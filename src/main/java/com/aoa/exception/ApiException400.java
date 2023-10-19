package com.aoa.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiException400 extends RuntimeException{

  private final ErrorCause errorCause;
  public ApiException400(String message, ErrorCause errorCause) {
    super(message);
    this.errorCause = errorCause;
  }
}
