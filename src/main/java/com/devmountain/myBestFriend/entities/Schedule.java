package com.devmountain.myBestFriend.entities;

import com.devmountain.myBestFriend.dtos.ScheduleDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String event;

    @Temporal(TemporalType.DATE)
    Date eventDate;

    @Temporal(TemporalType.TIME)
    Date eventTime;

    @ManyToOne
    @JsonBackReference
    private Pet pet;

    public Schedule(ScheduleDto scheduleDto){
        if(scheduleDto.getEvent() != null){
            this.event = scheduleDto.getEvent();
        }
        if(scheduleDto.getEventDate() != null){
            this.eventDate = scheduleDto.getEventDate();
        }
        if(scheduleDto.getEventTime() != null){
            this.eventTime = scheduleDto.getEventTime();
        }
    }


}
