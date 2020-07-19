package pl.tube.tensortube.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tube.tensortube.model.Car;
import pl.tube.tensortube.repository.CarRepository;

@Named
@SessionScoped
@Data
//https://stackoverflow.com/questions/21517327/update-primefaces-datatable-with-new-rows-adding-dynamically
public class CarsView implements Serializable {

  @Autowired
  private CarRepository carRepository;

  private List<Car> cars;

  @PostConstruct
  public void init() { cars = carRepository.findAll(); }
}
