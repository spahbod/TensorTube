package pl.tube.tensortube.logic.file;

import org.apache.commons.io.FileUtils;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.tube.tensortube.logic.utility.UtilityFile;
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


    public void processFile() throws IOException {
        FileToProcess fileToProcess = fileToProcessRepository.findFirstByOrderByIdAsc();
        if(fileToProcess != null) {
            log.info("ProcessFile -> fileToProcess " + fileToProcess.getFileName());
            //tutaj będzie analiza AI
            File originalFile = addFileToRepository(fileToProcess);

            FileUtils.deleteQuietly(originalFile);
            fileToProcessRepository.delete(fileToProcess);
        }
    }

    private File addFileToRepository(FileToProcess fileToProcess) throws IOException {

        String fileNameWithPrefix = utilityFile.getFileNameWithDatePrefix(fileToProcess.getFileName());

        File targetFile = new File(path + fileNameWithPrefix);

        File originalFile = FileUtils.getFile(fileToProcess.getFullPath());

        FileUtils.copyInputStreamToFile(getInputStreamFromOriginalFile(originalFile), targetFile);
        utilityFile.addProcessedFileToRepository(targetFile.getName(), targetFile.getPath(), fileToProcess.getUserId(), originalFile.length());

        return originalFile;
    }

    private InputStream getInputStreamFromOriginalFile(File originalFile) throws FileNotFoundException {
        return new FileInputStream(originalFile);
    }
}
