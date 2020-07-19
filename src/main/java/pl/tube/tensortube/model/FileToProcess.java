package pl.tube.tensortube.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "FileToProcess")
@Data
public class FileToProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private Date created;
    @Column
    private String fileName;
    @Column
    private String fullPath;
    @Column
    private String userId;

    public FileToProcess() {}

    public FileToProcess(String fileName, String fullPath, String userId) {
        this.created = new Date();
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getUserId() { return userId; }
}
