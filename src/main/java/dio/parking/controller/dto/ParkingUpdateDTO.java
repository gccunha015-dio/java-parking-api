package dio.parking.controller.dto;

import lombok.Data;

@Data
public class ParkingUpdateDTO {
  private String license;
  private String state;
  private String model;
  private String color;
}
