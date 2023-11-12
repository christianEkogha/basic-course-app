package fr.cekogha.courseapp.exception;

import lombok.Getter;
import lombok.Setter;

/** The type Field error. */
@Getter
@Setter
public class FieldError {

  private String field;
  private String errorCode;
}
