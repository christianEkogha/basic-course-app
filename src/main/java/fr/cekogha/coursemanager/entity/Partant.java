package fr.cekogha.coursemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Partant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartant;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nomPartant;
    @OneToMany(mappedBy = "partant", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Position> positions = new ArrayList<>();
}
