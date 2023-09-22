package fr.cekogha.coursemanager.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class PositionKey implements Serializable {
    private Long idCourse;
    private Long idPartant;
}
