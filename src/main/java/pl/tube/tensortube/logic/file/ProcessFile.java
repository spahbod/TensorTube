package pl.tube.tensortube.logic.file;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.tube.tensortube.logic.image.ImageProcess;
import pl.tube.tensortube.logic.utility.UtilityFile;
import pl.tube.tensortube.logic.video.VideoProcess;
import pl.tube.tensortube.model.FileToProcess;
import pl.tube.tensortube.repository.FileToProcessRepository;

@Component
public class ProcessFile {
    private static final Logger log = LoggerFactory.getLogger(ProcessFile.class);


    @Value("${home.path}${catalog.main}")
    private String path;

    @Autowired
    private FileToProcessRepository fileToProcessRepository;

    @Autowired
    private UtilityFile utilityFile;

    @Autowired
    private VideoProcess videoProcess;

    @Autowired
    private ImageProcess imageProcess;


    public void processFile() throws IOException {
        FileToProcess fileToProcess = fileToProcessRepository.findFirstByOrderByIdAsc();
        if(fileToProcess != null) {
            log.info("ProcessFile -> fileToProcess " + fileToProcess.getFileName());

            //tutaj będzie analiza AI
            File originalFile = FileUtils.getFile(fileToProcess.getFullPath());
            List<Path> paths = videoProcess.getVideoFrames(originalFile);
            imageProcess.processImages(paths);

            File file = addFileToRepository(fileToProcess, originalFile);

            FileUtils.deleteQuietly(file);
            fileToProcessRepository.delete(fileToProcess);
        }
    }

    private File addFileToRepository(FileToProcess fileToProcess,  File originalFile) throws IOException {
        log.info("ProcessFile -> " + originalFile.getName());

        String fileNameWithPrefix = utilityFile.getFileNameWithDatePrefix(fileToProcess.getFileName());

        File targetFile = new File(path + fileNameWithPrefix);

        FileUtils.copyInputStreamToFile(getInputStreamFromOriginalFile(originalFile), targetFile);
        utilityFile.addProcessedFileToRepository(targetFile.getName(), targetFile.getPath(), fileToProcess.getUserId(), originalFile.length());

        return originalFile;
    }

    private InputStream getInputStreamFromOriginalFile(File originalFile) throws FileNotFoundException {
        return new FileInputStream(originalFile);
    }
}
