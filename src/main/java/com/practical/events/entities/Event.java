package com.practical.events.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    private String id;
    private String title;
    private LocalDateTime dateTime;
    private int guests;
    private String location;
}
