package com.practical.events.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.practical.events.entities.Event;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class EventResponse {
    public String id;
    public String title;
    public LocalDateTime dateTime;
    public int guests;
    public String location;

    public EventResponse(Event event) {
        id = event.getId();
        title = event.getTitle();
        dateTime = event.getDateTime();
        guests = event.getGuests();
        location = event.getLocation();
    }
}
