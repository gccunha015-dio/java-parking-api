package dio.parking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dio.parking.model.Parking;
import dio.parking.repository.ParkingRepository;

@Service
public class ParkingService {
  private final ParkingRepository _parkingRepository;
  private final ParkingCheckOutService _parkingCheckOutService;

  public ParkingService(ParkingRepository parkingRepository, ParkingCheckOutService parkingCheckOutService) {
    _parkingRepository = parkingRepository;
    _parkingCheckOutService = parkingCheckOutService;
  }

  public Parking checkOut(String id) throws UnsupportedOperationException {
    Parking parking = readById(id);
    if (parking.getBill() != null)
      throw new UnsupportedOperationException();
    parking.setCheckOutDate(LocalDateTime.now());
    parking.setBill(_parkingCheckOutService.calculateBill(parking));
    return _saveParking(parking);
  }

  public Parking update(String id, Parking parkingWithUpdatedValues) {
    Parking parking = readById(id);
    String newLicense = parkingWithUpdatedValues.getLicense();
    String newState = parkingWithUpdatedValues.getState();
    String newModel = parkingWithUpdatedValues.getModel();
    String newColor = parkingWithUpdatedValues.getColor();
    if (newLicense != null)
      parking.setLicense(newLicense);
    if (newState != null)
      parking.setState(newState);
    if (newModel != null)
      parking.setModel(newModel);
    if (newColor != null)
      parking.setColor(newColor);
    return _saveParking(parking);
  }

  public Parking create(Parking parking) {
    parking.setId(_generateUUID());
    parking.setEntryDate(LocalDateTime.now());
    return _saveParking(parking);
  }

  public Parking readById(String id) throws NoSuchElementException {
    return _parkingRepository.findById(id).get();
  }

  public List<Parking> readAll() {
    return _parkingRepository.findAll();
  }

  public void deleteById(String id) {
    readById(id);
    _parkingRepository.deleteById(id);
  }

  public void deleteAll() {
    _parkingRepository.deleteAll();
  }

  private String _generateUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  private Parking _saveParking(Parking parking) {
    return _parkingRepository.save(parking);
  }
}
