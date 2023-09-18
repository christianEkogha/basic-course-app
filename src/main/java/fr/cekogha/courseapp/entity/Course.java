package fr.cekogha.courseapp.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name="Course")
@EqualsAndHashCode(of = {"id"})
public class Course {

    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private LocalDate jour;

    @Column
    private String nom;
    
    @ManyToMany
    @JoinTable(
            name = "course_partant",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "partant_id")
    )
    private Set<Partant> partants = new HashSet<>();
    
    public void ajoutPartant(Partant partant) {
    	partants.add(partant);
    }
    
    
}
