package pl.tube.tensortube.logic.image;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import pl.tube.tensortube.logic.utility.UtilityTensor;
import pl.tube.tensortube.model.Tag;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Component
public class ImageProcess {

    private static final Logger log = LoggerFactory.getLogger(ImageProcess.class);

    @Value("${home.path}${catalog.video}")
    private String path;

    @Value("${image.thread.pool.size}")
    private Integer processImagesPoolSize;

    @Autowired
    private UtilityTensor utilityTensor;

    private ForkJoinPool customThreadPool;

    @PostConstruct
    public void init(){
        customThreadPool = new ForkJoinPool(processImagesPoolSize);
    }

    public List<Tag> processImages(List<Path> pathsToImages) throws IOException, ExecutionException, InterruptedException {
        log.info("ImageProcess -> START ");
        long startTime = System.currentTimeMillis();

/*
        List<Tag> tags = customThreadPool.submit(() -> pathsToImages.parallelStream()
                .map(path -> {
                    try {
                        return processImage(path);
                    } catch (IOException e) {
                        log.error("processImage", e);
                        return null;
                    }
                })
                .distinct()
                .collect(Collectors.toList()))
                .get();
*/

        List<Tag> tags = pathsToImages.parallelStream()
                .map(path -> {
                    try {
                        return processImage(path);
                    } catch (IOException e) {
                        log.error("processImage", e);
                        return null;
                    }
                })
                .distinct()
                .collect(Collectors.toList());


        FileUtils.deleteDirectory(new File(path));

        long estimatedTime = System.currentTimeMillis() - startTime;
        log.info("ImageProcess -> END time " + estimatedTime + " ms " + " Number of tags : " + tags.size());

        return tags;
    }

    private Tag processImage(Path path) throws IOException {
        byte[] imageBytes = Files.readAllBytes(path);
        Tensor image = Tensor.create(imageBytes);

        float[] labelProbabilities = executeInceptionGraph(utilityTensor.getGraphDef(), image);
        int bestLabelIdx = maxIndex(labelProbabilities);

        log.info(String.format(
                "BEST MATCH: %s (%.2f%% likely)",
                utilityTensor.getLabels().get(bestLabelIdx), labelProbabilities[bestLabelIdx] * 100f));

        return new Tag(utilityTensor.getLabels().get(bestLabelIdx), (long) labelProbabilities[bestLabelIdx]);
    }

    private float[] executeInceptionGraph(byte[] graphDef, Tensor image) {
        try (Graph g = new Graph()) {
            g.importGraphDef(graphDef);
            try (Session s = new Session(g);
                 Tensor result = s.runner().feed("DecodeJpeg/contents", image).fetch("softmax").run().get(0)) {
                final long[] rshape = result.shape();
                if (result.numDimensions() != 2 || rshape[0] != 1) {
                    log.info(String.format(
                            "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                            Arrays.toString(rshape)));
                    return null;
                }
                int nlabels = (int) rshape[1];
                return result.copyTo(new float[1][nlabels])[0];
            }
        }
    }

    private int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }
}
