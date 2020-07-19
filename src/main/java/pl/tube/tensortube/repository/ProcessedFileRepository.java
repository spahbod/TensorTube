package pl.tube.tensortube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tube.tensortube.model.ProcessedFile;

public interface ProcessedFileRepository extends JpaRepository<ProcessedFile, Long> {
}
