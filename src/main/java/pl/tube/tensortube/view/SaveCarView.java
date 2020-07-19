package pl.tube.tensortube.view;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tube.tensortube.model.Car;
import pl.tube.tensortube.repository.CarRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
@Data
public class SaveCarView {

    private String brand;
    private String year;
    private String color;

    @Autowired
    private CarRepository carRepository;

    public String saveCar() {
        Car car = new Car(brand, year, color);
        carRepository.save(car);

        FacesContext context = FacesContext.getCurrentInstance();
        return context.getViewRoot().getViewId() + "?faces-redirect=true";
    }
}
