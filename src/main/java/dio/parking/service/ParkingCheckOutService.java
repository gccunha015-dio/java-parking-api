package dio.parking.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import dio.parking.model.Parking;

@Service
public class ParkingCheckOutService {
  private static final int ONE_HOUR = 60;
  private static final int TWENTY_FOUR_HOURS = 24 * ONE_HOUR;
  private static final double ONE_HOUR_VALUE = 5.00;
  private static final double ADDITIONAL_PER_HOUR_VALUE = 2.00;
  private static final double DAY_VALUE = 20.00;

  public Double calculateBill(Parking parking) {
    return _calculateBill(parking.getEntryDate(), parking.getCheckOutDate());
  }

  private Double _calculateBill(LocalDateTime entryDate, LocalDateTime checkOutDate) {
    long minutes = entryDate.until(checkOutDate, ChronoUnit.MINUTES);
    Double bill = 0.0;
    if (minutes <= ONE_HOUR) {
      return ONE_HOUR_VALUE;
    }
    if (minutes < TWENTY_FOUR_HOURS) {
      bill = ONE_HOUR_VALUE;
      int hours = (int) (minutes / ONE_HOUR);
      for (int i = 0; i < hours; i++) {
        bill += ADDITIONAL_PER_HOUR_VALUE;
      }
      return bill;
    }
    int days = (int) (minutes / TWENTY_FOUR_HOURS);
    for (int i = 0; i < days; i++) {
      bill += DAY_VALUE;
    }
    return bill;
  }
}
