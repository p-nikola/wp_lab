package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.boostrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> searchEvents(String text) {
        return DataHolder.events.stream().filter(i -> i.getName().toLowerCase().contains(text.toLowerCase()) || i.getDescription().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
    }

    public List<Event> searchEventsByTextAndScore(String text, double rating) {
        return DataHolder.events.stream().filter(i -> i.getName().toLowerCase().contains(text.toLowerCase()) && i.getPopularityScore() <= rating).collect(Collectors.toList());
    }


    public List<Event> searchEventsByScore(double rating) {
        return DataHolder.events.stream().filter( i->i.getPopularityScore() <= rating).collect(Collectors.toList());
    }

}
