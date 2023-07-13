package com.devmountain.myBestFriend.repositories;

import com.devmountain.myBestFriend.entities.Pet;
import com.devmountain.myBestFriend.entities.Schedule;
import com.devmountain.myBestFriend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPetEquals(Pet pet);

}
