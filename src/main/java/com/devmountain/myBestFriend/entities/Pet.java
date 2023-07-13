package com.devmountain.myBestFriend.entities;

import com.devmountain.myBestFriend.dtos.PetDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String breed;

    @Column(columnDefinition = "text")
    private String medical_history;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonBackReference
    private Set<Schedule> petSet = new HashSet<>();

    public Pet(PetDto petDto){
        if(petDto.getName() != null){
            this.name = petDto.getName();
        }
        if(petDto.getBreed() != null){
            this.breed = petDto.getBreed();
        }
        if(petDto.getMedical_history() != null){
            this.medical_history = petDto.getMedical_history();
        }
    }
}
