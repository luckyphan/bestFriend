package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.PetDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PetService {
    @Transactional
    void addPet(PetDto petDto, Long userId);

    @Transactional
    void deletePet(Long petId);

    @Transactional
    void updatePet(PetDto petDto);

    List<PetDto> getAllPets(Long userId);

    Optional<PetDto> getPet(Long petId);
}
