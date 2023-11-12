package fr.cekogha.courseapp.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** The type Course dto. */
@Getter
@Setter
@ToString
public class CourseDTO {
  @Digits(integer = 100, fraction = 0)
  private long numero;

  @NotBlank private String nom;
  @FutureOrPresent private LocalDate jour;

  @Size(min = 3, message = "Au moins de 3 partants requis")
  private Set<Long> partantIds;
}
