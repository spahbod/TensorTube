package pl.tube.tensortube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tube.tensortube.model.FileToProcess;

public interface FileToProcessRepository extends JpaRepository<FileToProcess, Long> {
   FileToProcess findFirstByOrderByIdAsc();
}
