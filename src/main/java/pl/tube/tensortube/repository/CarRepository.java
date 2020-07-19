package pl.tube.tensortube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.tube.tensortube.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
