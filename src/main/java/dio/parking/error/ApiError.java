package dio.parking.error;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ApiError {
  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;
  private String debugMessage;
  private List<ApiSubError> subErrors;

  ApiError(HttpStatus status) {
    timestamp = LocalDateTime.now();
    this.status = status;
  }

  ApiError(HttpStatus status, Throwable ex) {
    this(status);
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  ApiError(HttpStatus status, String message, Throwable ex) {
    this(status, ex);
    this.message = message;
  }
}