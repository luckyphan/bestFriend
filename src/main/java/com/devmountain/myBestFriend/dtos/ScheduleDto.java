package com.devmountain.myBestFriend.dtos;

import com.devmountain.myBestFriend.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto implements Serializable {
    private Long id;
    private String event;
    Date eventDate;
    Date eventTime;

    public ScheduleDto(Schedule schedule){
        if(schedule.getId() != null){
            this.id = schedule.getId();
        }
        if(schedule.getEvent() != null){
            this.event = schedule.getEvent();
        }
        if(schedule.getEventDate() != null){
            this.eventDate = getEventDate();
        }
        if(schedule.getEventTime() != null){
            this.eventTime = getEventTime();
        }
    }
}
