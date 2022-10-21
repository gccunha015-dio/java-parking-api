package dio.parking.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingDTO {
  private String id;
  private String license;
  private String state;
  private String model;
  private String color;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime entryDate;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime checkOutDate;

  private Double bill;
}
