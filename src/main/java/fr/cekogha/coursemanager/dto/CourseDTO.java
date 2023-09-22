package fr.cekogha.coursemanager.dto;

import fr.cekogha.coursemanager.util.SizeAndDuplicate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CourseDTO {
    @NotBlank
    private String nomCourse;
    @FutureOrPresent
    private LocalDate jourCourse;
    @SizeAndDuplicate(minSize = 3, duplicate = false)
    private List<Long> partantIds;
}
