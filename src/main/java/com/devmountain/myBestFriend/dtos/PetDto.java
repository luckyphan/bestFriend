package com.devmountain.myBestFriend.dtos;

import com.devmountain.myBestFriend.entities.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto implements Serializable {
    private Long id;
    private String name;
    private String breed;
    private String medical_history;
    private Set<ScheduleDto> scheduleDtoSet = new HashSet<>();

    public PetDto(Pet pet){
        if(pet.getId() != null){
            this.id = pet.getId();
        }
        if(pet.getName() != null){
            this.name = pet.getName();
        }
        if(pet.getBreed() != null){
            this.breed = pet.getBreed();
        }
        if(pet.getMedical_history() != null){
            this.medical_history = pet.getMedical_history();
        }

    }
}
