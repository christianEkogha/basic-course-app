package fr.cekogha.courseapp.exception;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** The type Error response. */
@Getter
@Setter
public class ErrorResponse {

  private Integer httpStatus;
  private String exception;
  private String message;
  private List<FieldError> fieldErrors;
}
