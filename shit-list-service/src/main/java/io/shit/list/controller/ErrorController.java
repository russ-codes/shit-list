/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ErrorController {

  @ResponseBody
  @ExceptionHandler(Throwable.class)
  public ErrorResponse errorResponse(final Throwable throwable) {

    log.error("Error: {}", throwable.getMessage(), throwable);

    return ErrorResponse.builder().message(throwable.getMessage()).build();
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class ErrorResponse {

    private String message;
  }
}
