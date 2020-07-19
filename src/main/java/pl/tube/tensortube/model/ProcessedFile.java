package pl.tube.tensortube.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ProcessedFile")
@Data
public class ProcessedFile {
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

    public ProcessedFile() {}

    public ProcessedFile(String fileName, String fullPath, String userId) {
        this.created = new Date();
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.userId = userId;
    }
}
