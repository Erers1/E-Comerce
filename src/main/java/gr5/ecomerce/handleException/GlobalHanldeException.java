package gr5.ecomerce.handleException;

import gr5.ecomerce.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHanldeException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDTO.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("INTERNAL SERVER ERROR" + e.getMessage()).build());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("BAD REQUEST" + e.getMessage()).build());
    }
}
