package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.PetDto;
import com.devmountain.myBestFriend.dtos.UserDto;
import com.devmountain.myBestFriend.entities.Pet;
import com.devmountain.myBestFriend.entities.User;
import com.devmountain.myBestFriend.repositories.PetRepository;
import com.devmountain.myBestFriend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    //add pet
    //delete pet
    //update pet
    //get pet by id

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public void addPet(PetDto petDto, Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        Pet pet = new Pet(petDto);
        userOptional.ifPresent(pet::setUser);
        petRepository.saveAndFlush(pet);
    }
    @Override
    @Transactional
    public void deletePet(Long petId){
        Optional<Pet> petOptional = petRepository.findById(petId);
        petOptional.ifPresent(pet -> petRepository.delete(pet));
    }
    @Override
    @Transactional
    public void updatePet(PetDto petDto){
        Optional<Pet> petOptional = petRepository.findById(petDto.getId());
        petOptional.ifPresent(pet -> {
            pet.setName(petDto.getName());
            pet.setBreed(petDto.getBreed());
            pet.setMedical_history(petDto.getMedical_history());
            petRepository.saveAndFlush(pet);
        });
    }

    @Override
    public List<PetDto> getAllPets(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            List<Pet> petList = petRepository.findAllByUserEquals(userOptional.get());
            return petList.stream().map(pet -> new PetDto(pet)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<PetDto> getPet(Long petId){
        Optional<Pet> petOptional = petRepository.findById(petId);
        if(petOptional.isPresent()){
            return Optional.of(new PetDto(petOptional.get()));
        }
        return Optional.empty();
    }

//    @Override
    public List<String> petSchedule(PetDto petDto){
        List<String> response = new ArrayList<>();
        Optional<Pet> petOptional = petRepository.findByName(petDto.getName());
        if(petOptional.isPresent()){
                response.add("http://localhost:8080/planner.html");
                response.add(String.valueOf(petOptional.get().getId()));
            }
        else{
            response.add("Invalid pet");
        }
        return response;
    }

}
