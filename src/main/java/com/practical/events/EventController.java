package com.practical.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practical.events.dtos.EventRequest;
import com.practical.events.dtos.EventResponse;
import com.practical.events.entities.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@CrossOrigin
@Validated
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/index")
    public String showEventList(Model model) {
        return "index";
    }

    @PostMapping("/events")
    public String createEvent(Model model, @Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.saveEvent(eventRequest.toEvent());
        model.addAttribute("events", List.of(event));
        return "table-body";
    }

    @GetMapping("/events/{id}")
    public String getEvent(Model model, @PathVariable String id) {
        eventService.getEvent(id)
                .ifPresentOrElse(
                        event -> model.addAttribute("event", event),
                        ()-> model.addAttribute("event", new Event())
                );
        return "event-form";
    }

    @PutMapping("/events/{id}")
    public String updateEvent(Model model, @PathVariable String id, @Valid @RequestBody EventRequest updateRequest) {
        Event event = eventService.getEvent(id)
                .map(existingEvent -> update(existingEvent, updateRequest))
                .map(eventService::saveEvent)
                .orElseThrow();
        model.addAttribute("events", List.of(event));
        return "table-body";
    }

    private Event update(Event event, EventRequest updateRequest) {
        event.setTitle(updateRequest.getTitle());
        event.setDateTime(updateRequest.getDateTime());
        event.setGuests(updateRequest.getGuests());
        event.setLocation(updateRequest.getLocation());
        return event;
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(Model model, @PathVariable String id) {
        eventService.getEvent(id).ifPresent(eventService::deleteEvent);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        List<EventResponse> events = eventService.getAllEvents()
                .stream()
                .map(EventResponse::new)
                .collect(toList());

        model.addAttribute("events", events);
        return "table-body";
    }
}
