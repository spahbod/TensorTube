package pl.tube.tensortube.logic.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tube.tensortube.model.FileToProcess;
import pl.tube.tensortube.model.ProcessedFile;
import pl.tube.tensortube.model.Tag;
import pl.tube.tensortube.repository.FileToProcessRepository;
import pl.tube.tensortube.repository.ProcessedFileRepository;
import pl.tube.tensortube.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtilityFile {

    @Autowired
    private FileToProcessRepository fileToProcessRepository;

    @Autowired
    private ProcessedFileRepository processedFileRepository;

    @Autowired
    private TagRepository tagRepository;

    public void addFileToProcessToRepository(String fileName, String fullPath, String userId) {
        FileToProcess fileToProcess = new FileToProcess(fileName, fullPath, userId);
        fileToProcessRepository.save(fileToProcess);
    }

    public void addProcessedFileToRepository(String fileName, String fullPath, String userId, long size, List<Tag> tags) {
        ProcessedFile file = new ProcessedFile(fileName, fullPath, userId, size);

        processedFileRepository.saveAndFlush(file);

        for (Tag tag : tags) {
            tag.setProcessedFile(file);
            tagRepository.saveAndFlush(tag);
        }
    }

    public String getFileNameWithDatePrefix(String patch) {
        return System.currentTimeMillis() + "_" + patch;
    }
}
