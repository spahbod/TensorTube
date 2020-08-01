package pl.tube.tensortube.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "ProcessedFile")
@Table(name = "processed_file")
@Data
public class ProcessedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processedFileId", updatable = false, nullable = false)
    private Long processedFileId;

    @Column
    private Date created;
    @Column
    private String fileName;
    @Column
    private String fullPath;
    @Column
    private String userId;
    @Column
    private long size;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            mappedBy="processedFile"
    )
    private List<Tag> tags = new ArrayList<>();

    public ProcessedFile() {}

    public ProcessedFile(String fileName, String fullPath, String userId, long size) {
        this.created = new Date();
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.userId = userId;
        this.size = size;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
