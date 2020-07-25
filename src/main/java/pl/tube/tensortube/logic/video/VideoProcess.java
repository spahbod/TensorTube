package pl.tube.tensortube.logic.video;



import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoProcess {
    private static final Logger log = LoggerFactory.getLogger(VideoProcess.class);

    @Value("${home.path}${catalog.video}")
    private String path;

    @Value("${process.file.time.frame}")
    private Integer timeFrame;

    public List<Path>  getVideoFrames(File originalFile) throws IOException {
        log.info("VideoProcess -> START ");
        long startTime = System.currentTimeMillis();

        if (!Files.isDirectory(Paths.get(path))) {
            new File(path).mkdir();
        }

        Java2DFrameConverter frameConverter = new Java2DFrameConverter();
        FFmpegFrameGrabber ffmpegFrameGrabber = new FFmpegFrameGrabber(originalFile);
        ffmpegFrameGrabber.start();

        //getLengthInVideoFrames w mikrosekundach
        //int startFrame = ffmpegFrameGrabber.getLengthInVideoFrames() / 2;
        //ffmpegFrameGrabber.setVideoFrameNumber(startFrame);

        long timestamp = 0;
        for (int i = 0; timestamp < ffmpegFrameGrabber.getLengthInTime(); i++){
            timestamp = i * timeFrame * 1000000L;
            ffmpegFrameGrabber.setTimestamp(Math.round(timestamp));

            //Frame frame = ffmpegFrameGrabber.grab();
            //BufferedImage img = Java2DFrameUtils.toBufferedImage(frame);
            BufferedImage img = frameConverter.convert(ffmpegFrameGrabber.grabImage());
            if(img != null ) {
                log.info("VideoProcess -> " +  ffmpegFrameGrabber.getLengthInTime() + " " + timestamp);
                ImageIO.write(img, "jpeg", new File(path + "video-frame-" + System.currentTimeMillis() + ".jpeg"));
            }
        }
        ffmpegFrameGrabber.stop();
        List<Path> paths = getVideoFramesPaths();
        long estimatedTime = System.currentTimeMillis() - startTime;
        log.info("VideoProcess -> END time " + estimatedTime + " ms" + ", Number of frames : " + paths.size());
        return paths;
    }

    private List<Path> getVideoFramesPaths() throws IOException {
        return Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }
}
