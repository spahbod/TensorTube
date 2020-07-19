package pl.tube.tensortube.view;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.tube.tensortube.controller.UserManager;
import pl.tube.tensortube.logic.utility.UtilityFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

@Named
@SessionScoped
@Data
public class FileUploadView {
    private static final Logger log = LoggerFactory.getLogger(FileUploadView.class);

    private UploadedFile file;

    @Value("${home.path}${catalog.temp}")
    private String path;

    @Autowired
    private UtilityFile utilityFile;
    @Autowired
    private UserManager userManager;

    public void upload() throws IOException {
        if (file != null && StringUtils.isNotBlank(file.getFileName())) {
            String fileNameWithPrefix = utilityFile.getFileNameWithDatePrefix(file.getFileName());

            File targetFile = new File(path + fileNameWithPrefix);
            FileUtils.copyInputStreamToFile(file.getInputstream(), targetFile);

            utilityFile.addFileToProcessToRepository(file.getFileName(), targetFile.getPath(), userManager.getUserId());

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
