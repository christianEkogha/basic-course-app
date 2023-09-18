package fr.cekogha.courseapp.dto;

import java.util.Set;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PartantDTO {
	@Digits(integer = 100, fraction = 0)
	private long id;
	@NotBlank
	private String nom; 
	@Size(max=0, message="La liste des courses est modifiée pour l'ajout d'un partant via PUT /api/courses?numero=:partant_num")
	private Set<Long> courseIds;
}
