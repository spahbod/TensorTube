package pl.tube.tensortube.logic.image;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.tube.tensortube.logic.video.VideoProcess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageProcess {

    private static final Logger log = LoggerFactory.getLogger(ImageProcess.class);

    @Value("${home.path}${catalog.video}")
    private String path;


    public List<String> processImages(List<Path> pathsToImages) throws IOException {
        log.info("ImageProcess -> START ");
        long startTime = System.currentTimeMillis();

        List<String> tags = pathsToImages.stream()
                .map(path -> processImage(path.toFile()))
                .collect(Collectors.toList());


        FileUtils.deleteDirectory(new File(path));

        long estimatedTime = System.currentTimeMillis() - startTime;
        log.info("ImageProcess -> END time " + estimatedTime + " ms " + " Number of tags : " + tags.size());

        return tags;
    }

    private String processImage(File image){
        log.info("ImageProcess -> processImage " + image.getName());
        return image.getName();
    }
}
