package fr.cekogha.coursemanager.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FieldError {

    private String field;
    private String errorMessage;

}
