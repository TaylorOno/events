package com.practical.events;

import com.practical.events.entities.Event;
import com.practical.events.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEvent(String id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }
}
