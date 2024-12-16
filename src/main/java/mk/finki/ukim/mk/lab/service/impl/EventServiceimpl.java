package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.exceptions.LocationNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepositroyJPA;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepositoryJPA;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepositoryJPA;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceimpl implements EventService {


    private final EventRepositoryJPA eventRepository;
    private final LocationRepositoryJPA locationRepositoryJPA;

    private final CategoryRepositroyJPA categoryRepositroyJPA;

    public EventServiceimpl(EventRepositoryJPA eventRepository, LocationRepositoryJPA locationRepositoryJPA, CategoryRepositroyJPA categoryRepositroyJPA) {
        this.eventRepository = eventRepository;
        this.locationRepositoryJPA = locationRepositoryJPA;
        this.categoryRepositroyJPA = categoryRepositroyJPA;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.searchByName(text);
    }

    @Override
    public List<Event> searchEventsByTextAndScore(String text, double rating) {
        return eventRepository.searchByNameAndPopularityScore(text, rating);
    }

    @Override
    public List<Event> searchEventsByScore(double rating) {
        return eventRepository.searchByPopularityScore(rating);
    }

    @Override
    public Optional<Event> saveEvent(Long id, String name, String description, double popularityScore, Long locationId,Long  categoryId) {

        Location location = locationRepositoryJPA.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));

        Category category = categoryRepositroyJPA.findById(categoryId)
                .orElseThrow(() -> new LocationNotFoundException(categoryId));

        Event event;

        if (id != null) {
            // Editing an existing event
            event = eventRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Event with ID " + id + " not found"));
            // Update the fields
            event.setName(name);
            event.setDescription(description);
            event.setPopularityScore(popularityScore);
            event.setLocation(location);
            event.setCategory(category);
        } else {
            // Adding a new event
            event = new Event(name, description, popularityScore, location,category);
        }

        // Save the event (updates if it has an ID, inserts otherwise)
        return Optional.of(eventRepository.save(event));

    }


    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void hasIncremented(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + id + " not found"));
        event.setPopularityScore(event.getPopularityScore() + 1.0);
        event.setHasUpvote(true);

        eventRepository.save(event);
    }

    @Override
    public List<Event> findAllByLocation(Long locationId) {
        return eventRepository.findAllByLocation_Id(locationId);
    }

    @Override
    public List<Event> findAllByCategory(Long categoryId) {
        return eventRepository.findAllByCategory_Id(categoryId);
    }
}
