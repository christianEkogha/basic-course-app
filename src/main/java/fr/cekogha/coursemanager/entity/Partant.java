package fr.cekogha.coursemanager.entity;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Partant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartant;
    @NotNull
    @Column(nullable = false, unique = true)
    private String nomPartant;
    @OneToMany(mappedBy = "partant", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Position> positions = new ArrayList<>();
}
