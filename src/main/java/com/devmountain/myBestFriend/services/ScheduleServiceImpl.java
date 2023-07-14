package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.ScheduleDto;
import com.devmountain.myBestFriend.entities.Pet;
import com.devmountain.myBestFriend.entities.Schedule;
import com.devmountain.myBestFriend.repositories.PetRepository;
import com.devmountain.myBestFriend.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    //add schedule
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;

    @Override
    @Transactional
    public void addSchedule(ScheduleDto scheduleDto, Long petId){
        Optional<Pet> petOptional = petRepository.findById(petId);
        Schedule schedule = new Schedule(scheduleDto);
        petOptional.ifPresent(schedule::setPet);
        scheduleRepository.saveAndFlush(schedule);
    }
    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        scheduleOptional.ifPresent(schedule -> scheduleRepository.delete(schedule));
    }
    @Override
    @Transactional
    public void updateSchedule(ScheduleDto scheduleDto){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleDto.getId());
        scheduleOptional.ifPresent(schedule -> {
            schedule.setEvent(scheduleDto.getEvent());
            schedule.setEventDate(scheduleDto.getEventDate());
            schedule.setEventTime(scheduleDto.getEventTime());
            scheduleRepository.saveAndFlush(schedule);
        });
    }
    @Override
    public List<ScheduleDto> getAllSchedules(Long petId){
        Optional<Pet> petOptional = petRepository.findById(petId);
        if(petOptional.isPresent()){
            List<Schedule> scheduleList = scheduleRepository.findAllByPetEquals(petOptional.get());
            return scheduleList.stream().map(schedule -> new ScheduleDto(schedule)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<ScheduleDto> getSchedule(Long scheduleId){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if(scheduleOptional.isPresent()){
            return Optional.of(new ScheduleDto(scheduleOptional.get()));
        }
        return Optional.empty();
    }

}
