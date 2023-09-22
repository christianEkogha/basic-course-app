package fr.cekogha.coursemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Position {
    @EmbeddedId
    @JsonIgnore
    private PositionKey pk = new PositionKey();
    
    @ManyToOne
    @MapsId("idCourse")
    @JoinColumn(name = "id_course")
    @JsonIgnore
    private Course course;
    
    @ManyToOne
    @MapsId("idPartant")
    @JoinColumn(name = "id_partant")
    private Partant partant;
    
    private int numero;

}
