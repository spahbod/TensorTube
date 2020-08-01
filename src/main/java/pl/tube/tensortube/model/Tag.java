package pl.tube.tensortube.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Tag")
@Table(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId", updatable = false, nullable = false)
    private Long tagId;

    @Column
    private String name;

    @Column
    private long probability;

    @ManyToOne
    @JoinColumn(name="processedFileId")
    private ProcessedFile processedFile;

    public Tag() {}

    public Tag(String name, long probability) {
        this.name = name;
        this.probability = probability;
    }

    public Tag(String name, long probability, ProcessedFile processedFile) {
        this.name = name;
        this.probability = probability;
        this.processedFile = processedFile;
    }

    public void setProcessedFile(ProcessedFile processedFile) {
        this.processedFile = processedFile;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Tag tag = (Tag) obj;
        if (this == tag) {
            return true;
        } else {
            return (this.name.equals(tag.name));
        }
    }
    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
        return hashno;
    }
}
