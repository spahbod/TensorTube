package pl.tube.tensortube.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tube.tensortube.model.ProcessedFile;
import pl.tube.tensortube.repository.ProcessedFileRepository;

@Named
@SessionScoped
@Data
//https://stackoverflow.com/questions/21517327/update-primefaces-datatable-with-new-rows-adding-dynamically
public class ProcessedFileView implements Serializable {

  @Autowired
  private ProcessedFileRepository processedFileRepository;

  public List<ProcessedFile> getProcessedFiles() {
    return processedFileRepository.findAll();
  }
}
