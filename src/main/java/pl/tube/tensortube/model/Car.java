package pl.tube.tensortube.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Car")
@Data
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column
  private String brand;
  @Column
  private String year;
  @Column
  private String color;

  public Car() {}

  public Car(Long id, String brand, String year, String color) {
    this.id = id;
    this.brand = brand;
    this.year = year;
    this.color = color;
  }

  public Car(String brand, String year, String color) {
    this.brand = brand;
    this.year = year;
    this.color = color;
  }
}
