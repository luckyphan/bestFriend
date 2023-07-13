package com.devmountain.myBestFriend.repositories;

import com.devmountain.myBestFriend.entities.Pet;
import com.devmountain.myBestFriend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserEquals(User user);
    Optional<Pet> findByName(String name);

}
