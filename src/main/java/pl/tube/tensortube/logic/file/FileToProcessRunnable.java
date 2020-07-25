package pl.tube.tensortube.logic.file;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.tube.tensortube.logic.utility.UtilityFile;

import java.io.File;
import java.io.IOException;

public class FileToProcessRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(FileToProcessRunnable.class);

    private UploadedFile file;
    private UtilityFile utilityFile;
    private String path;
    private String userId;

    public FileToProcessRunnable(UploadedFile file, UtilityFile utilityFile, String path, String userId) {
        this.file = file;
        this.utilityFile = utilityFile;
        this.path = path;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            log.info("FileToProcessRunnable -> file " + file.getFileName() + " with thread " + Thread.currentThread().getId());
            File targetFile = new File(path);
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
            utilityFile.addFileToProcessToRepository(file.getFileName(), targetFile.getPath(), userId);
        } catch (IOException e) {
            log.error("ERROR processing " + file.getFileName(), e);
        }
    }
}
