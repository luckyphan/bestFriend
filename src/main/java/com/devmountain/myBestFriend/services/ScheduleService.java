package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.ScheduleDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    @Transactional
    void addSchedule(ScheduleDto scheduleDto, Long petId);

    @Transactional
    void deleteSchedule(Long scheduleId);

    @Transactional
    void updateSchedule(ScheduleDto scheduleDto);

    List<ScheduleDto> getAllSchedules(Long petId);

    Optional<ScheduleDto> getSchedule(Long scheduleId);
}
