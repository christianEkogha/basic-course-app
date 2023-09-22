package fr.cekogha.coursemanager.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;
    
    @NotNull
    private String nomCourse;
    
    @FutureOrPresent
    private LocalDate jourCourse;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @Size(min=3, message="Au moins 3 partants obligatoires")
    private Collection<Position> positions = new ArrayList<>();

}
