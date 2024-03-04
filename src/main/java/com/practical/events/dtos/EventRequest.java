package com.practical.events.dtos;

import com.practical.events.entities.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
public class EventRequest {

    @NotEmpty
    private String title;

    @NotNull
    private LocalDateTime dateTime;

    private String location;
    @PositiveOrZero
    @Max(30)
    private int guests;


    public Event toEvent() {
        Event event = new Event();
        event.setId(UUID.randomUUID().toString());
        event.setTitle(title);
        event.setDateTime(dateTime);
        event.setGuests(guests);
        event.setLocation(location);
        return event;
    }
}
