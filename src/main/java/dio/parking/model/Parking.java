package dio.parking.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Parking {
  @Id
  private String id;

  private String license;
  private String state;
  private String model;
  private String color;
  private LocalDateTime entryDate;
  private LocalDateTime checkOutDate;
  private Double bill;
}
