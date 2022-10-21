package dio.parking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dio.parking.model.Parking;
import dio.parking.repository.ParkingRepository;

@Service
public class ParkingService {
  private final ParkingRepository _parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {
    _parkingRepository = parkingRepository;
  }

  public Parking create(Parking parking) {
    parking.setId(_generateUUID());
    parking.setEntryDate(LocalDateTime.now());
    return _parkingRepository.save(parking);
  }

  public List<Parking> readAll() {
    return _parkingRepository.findAll();
  }

  private String _generateUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public void deleteAll() {
    _parkingRepository.deleteAll();
  }
}
