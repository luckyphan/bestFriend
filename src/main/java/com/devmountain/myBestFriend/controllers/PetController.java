package com.devmountain.myBestFriend.controllers;

import com.devmountain.myBestFriend.dtos.PetDto;
import com.devmountain.myBestFriend.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/user/{userId}")
    public List<PetDto> getAllPets(@PathVariable Long userId){
        return petService.getAllPets(userId);
    }
    @PostMapping("/user/{userId}")
    public void addPet(@RequestBody PetDto petDto, @PathVariable Long userId){
        petService.addPet(petDto,userId);
    }
    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId){
        petService.deletePet(petId);
    }
    @PutMapping
    public void updatePet(@RequestBody PetDto petDto){
        petService.updatePet(petDto);
    }
    @GetMapping("/{petId}")
    public Optional<PetDto> getPet(@PathVariable Long petId){
        return petService.getPet(petId);
    }

}
