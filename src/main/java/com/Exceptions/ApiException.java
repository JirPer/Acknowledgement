package com.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

  private final ErrorCause errorCause;
  public ApiException(String message, ErrorCause errorCause) {
    super(message);
    this.errorCause = errorCause;
  }
}
