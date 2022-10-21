package dio.parking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dio.parking.controller.dto.ParkingCreateDTO;
import dio.parking.controller.dto.ParkingDTO;
import dio.parking.controller.mapper.ParkingMapper;
import dio.parking.model.Parking;
import dio.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings")
public class ParkingController {
  private final ParkingMapper _parkingMapper = new ParkingMapper();
  private final ParkingService _parkingService;

  public ParkingController(ParkingService parkingService) {
    _parkingService = parkingService;
  }

  @PostMapping
  @Operation(summary = "Park a new car")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "New parking created"),
      @ApiResponse(responseCode = "400", description = "Invalid parking format", content = @Content)
  })
  public ResponseEntity<ParkingDTO> create(@Valid @RequestBody ParkingCreateDTO dto) {
    // try {
    Parking parkingToCreate = _parkingMapper.toParking(dto);
    Parking createdParking = _parkingService.create(parkingToCreate);
    ParkingDTO responseBody = _parkingMapper.toParkingDTO(createdParking);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    // } catch (Exception __) {
    // return ResponseEntity.badRequest().build();
    // }
  }

  @GetMapping
  @Operation(summary = "Read all parkings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of parkings")
  })
  public ResponseEntity<List<ParkingDTO>> readAll() {
    List<Parking> parkingList = _parkingService.readAll();
    List<ParkingDTO> responseBody = _parkingMapper.toParkingDTOList(parkingList);
    return ResponseEntity.ok(responseBody);
  }

  @DeleteMapping
  @Operation(summary = "Delete all parkings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Deleted all parkings")
  })
  public ResponseEntity<Void> deleteAll() {
    _parkingService.deleteAll();
    return ResponseEntity.noContent().build();
  }
}
