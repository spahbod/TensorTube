package pl.tube.tensortube.view;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.tube.tensortube.controller.UserManager;
import pl.tube.tensortube.logic.file.FileToProcessRunnable;
import pl.tube.tensortube.logic.utility.UtilityFile;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Named
@SessionScoped
@Data
public class FileUploadView {
    private static final Logger log = LoggerFactory.getLogger(FileUploadView.class);

    private ExecutorService executor;

    private UploadedFile file;

    @Value("${home.path}${catalog.temp}")
    private String path;

    @Value("${thread.pool.size}")
    private Integer threadPoolSize;

    @Autowired
    private UtilityFile utilityFile;

    @Autowired
    private UserManager userManager;

    @PostConstruct
    public void init(){
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void upload() throws IOException {
        if (file != null && StringUtils.isNotBlank(file.getFileName())) {
            String fileNameWithPrefix = utilityFile.getFileNameWithDatePrefix(file.getFileName());

            Runnable worker = new FileToProcessRunnable(file, utilityFile, path + fileNameWithPrefix, userManager.getUserId());
            executor.execute(worker);

            addMessage("Successful", file.getFileName() + " is uploaded.");
        } else {
            addMessage("Failed", "No file");
        }
    }

    private void addMessage(String summary, String detail) {
        FacesMessage facesMessage = new FacesMessage(summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        log.debug("FileUploadView -> addMessage" + summary +" "+ detail);
    }
}
