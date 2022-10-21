package dio.parking.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import dio.parking.controller.dto.ParkingCreateDTO;
import dio.parking.controller.dto.ParkingDTO;
import dio.parking.model.Parking;

public class ParkingMapper extends ModelMapper {
  public Parking toParking(ParkingCreateDTO dto) {
    return map(dto, Parking.class);
  }

  public ParkingDTO toParkingDTO(Parking parking) {
    return map(parking, ParkingDTO.class);
  }

  public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList) {
    return parkingList.stream().map(this::toParkingDTO).collect(Collectors.toList());
  }
}
