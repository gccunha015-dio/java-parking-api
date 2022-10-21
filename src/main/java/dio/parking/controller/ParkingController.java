package dio.parking.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dio.parking.controller.dto.ParkingCreateDTO;
import dio.parking.controller.dto.ParkingDTO;
import dio.parking.controller.dto.ParkingUpdateDTO;
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
  private final ParkingService _parkingService;
  private final ParkingMapper _parkingMapper;

  public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
    _parkingService = parkingService;
    _parkingMapper = parkingMapper;
  }

  @PutMapping("/{id}/checkOut")
  @Operation(summary = "Check out parking")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Check out completed", content = @Content),
      @ApiResponse(responseCode = "400", description = "Parking already checked out", content = @Content),
      @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content)
  })
  public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) {
    try {
      Parking checkedOutParking = _parkingService.checkOut(id);
      ParkingDTO responseBody = _parkingMapper.toParkingDTO(checkedOutParking);
      return ResponseEntity.ok(responseBody);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (UnsupportedOperationException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping
  @Operation(summary = "Create a new parking")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "New parking created", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid parking format", content = @Content)
  })
  public ResponseEntity<ParkingDTO> create(@Valid @RequestBody ParkingCreateDTO dto, BindingResult result) {
    if (result.hasErrors())
      return ResponseEntity.badRequest().build();
    Parking parkingToCreate = _parkingMapper.toParking(dto);
    Parking createdParking = _parkingService.create(parkingToCreate);
    ParkingDTO responseBody = _parkingMapper.toParkingDTO(createdParking);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update parking")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Parking updated", content = @Content),
      @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content)
  })
  public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingUpdateDTO dto) {
    try {
      Parking parkingToUpdate = _parkingMapper.toParking(dto);
      Parking updatedParking = _parkingService.update(id, parkingToUpdate);
      ParkingDTO responseBody = _parkingMapper.toParkingDTO(updatedParking);
      return ResponseEntity.ok(responseBody);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Read parking by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Parking found", content = @Content),
      @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content)
  })
  public ResponseEntity<ParkingDTO> readById(@PathVariable String id) {
    try {
      Parking parking = _parkingService.readById(id);
      ParkingDTO responseBody = _parkingMapper.toParkingDTO(parking);
      return ResponseEntity.ok(responseBody);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  @Operation(summary = "Read all parkings")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of parkings", content = @Content)
  })
  public ResponseEntity<List<ParkingDTO>> readAll() {
    List<Parking> parkingList = _parkingService.readAll();
    List<ParkingDTO> responseBody = _parkingMapper.toParkingDTOList(parkingList);
    return ResponseEntity.ok(responseBody);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete parking by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Parking deleted", content = @Content),
      @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content)
  })
  public ResponseEntity<Void> deleteById(@PathVariable String id) {
    try {
      _parkingService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
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
