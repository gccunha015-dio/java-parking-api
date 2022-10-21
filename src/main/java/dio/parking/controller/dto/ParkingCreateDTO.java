package dio.parking.controller.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ParkingCreateDTO {
  @NotBlank
  private String license;

  @NotBlank
  private String state;

  @NotBlank
  private String model;

  @NotBlank
  private String color;
}
