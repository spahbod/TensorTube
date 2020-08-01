package pl.tube.tensortube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tube.tensortube.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
