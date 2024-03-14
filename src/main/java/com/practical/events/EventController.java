package com.practical.events;

import com.practical.events.dtos.EventRequest;
import com.practical.events.dtos.EventResponse;
import com.practical.events.entities.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@CrossOrigin
@Validated
public class EventController {

    private EventService eventService;
    private final NotificationService notificationService;

    public EventController(EventService eventService, NotificationService notificationService) {
        this.eventService = eventService;
        this.notificationService = notificationService;
    }

    @GetMapping("/index")
    public String showEventList(Model model) {
        return "index";
    }

    @PostMapping("/events")
    public String createEvent(Model model, @Valid @RequestBody EventRequest eventRequest) {
        Event event = eventService.saveEvent(eventRequest.toEvent());
        notificationService.sendEvents(SseEmitter.event().name("event").data("created"));
        model.addAttribute("events", List.of(event));
        return "success";
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
        notificationService.sendEvents(SseEmitter.event().name("event").data("updated"));
        return "success";
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
        notificationService.sendEvents(SseEmitter.event().name("event").data("deleted"));
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
