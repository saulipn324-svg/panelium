package com.saul.panelium.common;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  ResponseEntity<Map<String,String>> uploadTooLarge() {
    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
      .body(Map.of("message", "Cada imagen debe pesar menos de 15 MB y la carga completa menos de 200 MB"));
  }
}
