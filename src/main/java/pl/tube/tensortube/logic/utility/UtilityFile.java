package pl.tube.tensortube.logic.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tube.tensortube.model.FileToProcess;
import pl.tube.tensortube.model.ProcessedFile;
import pl.tube.tensortube.repository.FileToProcessRepository;
import pl.tube.tensortube.repository.ProcessedFileRepository;

@Component
public class UtilityFile {

    @Autowired
    private FileToProcessRepository fileToProcessRepository;

    @Autowired
    private ProcessedFileRepository processedFileRepository;

    public void addFileToProcessToRepository(String fileName, String fullPath, String userId) {
        FileToProcess fileToProcess = new FileToProcess(fileName, fullPath, userId);
        fileToProcessRepository.save(fileToProcess);
    }

    public void addProcessedFileToRepository(String fileName, String fullPath, String userId) {
        ProcessedFile file = new ProcessedFile(fileName, fullPath, userId);
        processedFileRepository.save(file);
    }

    public String getFileNameWithDatePrefix(String patch) {
        return System.currentTimeMillis() + "_" + patch;
    }
}
