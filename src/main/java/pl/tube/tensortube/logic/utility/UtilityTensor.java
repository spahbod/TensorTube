package pl.tube.tensortube.logic.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class UtilityTensor {

    private static final Logger log = LoggerFactory.getLogger(UtilityTensor.class);

    @Value("${home.path}${catalog.tensor}${pb.file}")
    private String pbPath;

    @Value("${home.path}${catalog.tensor}${labels.file}")
    private String labelsPath;

    private byte[] graphDef;
    private List<String> labels;

    @PostConstruct
    public void init() {
        graphDef = readAllBytesOrExit(Paths.get(pbPath));
        labels = readAllLinesOrExit(Paths.get(labelsPath));
    }

    private List<String> readAllLinesOrExit(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.error("Failed to read [" + path + "]: " + e.getMessage(), e);
        }
        return null;
    }

    private byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Failed to read [" + path + "]: " + e.getMessage(), e);
        }
        return null;
    }

    public byte[] getGraphDef() {
        return graphDef;
    }

    public List<String> getLabels() {
        return labels;
    }
}
