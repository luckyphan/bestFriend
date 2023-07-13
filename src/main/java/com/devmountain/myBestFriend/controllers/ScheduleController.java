package com.devmountain.myBestFriend.controllers;

import com.devmountain.myBestFriend.dtos.ScheduleDto;
import com.devmountain.myBestFriend.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/pet/{petId}")
    public List<ScheduleDto> getAllSchedules(@PathVariable Long petId){
        return scheduleService.getAllSchedules(petId);
    }
    @PostMapping("/pet/{petId}")
    public void addSchedule(@RequestBody ScheduleDto scheduleDto, @PathVariable Long petId){
        scheduleService.addSchedule(scheduleDto,petId);
    }
    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
    }
    @PutMapping
    public void updateSchedule(@RequestBody ScheduleDto scheduleDto){
        scheduleService.updateSchedule(scheduleDto);
    }
    @GetMapping("/{scheduleId}")
    public Optional<ScheduleDto> getSchedule(@PathVariable Long scheduleId){
        return scheduleService.getSchedule(scheduleId);
    }
}
