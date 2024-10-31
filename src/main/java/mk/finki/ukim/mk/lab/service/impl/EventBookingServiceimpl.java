package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.repository.EventBookingRepository;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingServiceimpl implements EventBookingService {


    private final EventBookingRepository eventBookingRepository;

    public EventBookingServiceimpl(EventBookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        return new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets);
    }

    @Override
    public void addBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        eventBookingRepository.addEvent(new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets));
    }

    @Override
    public List<EventBooking> listAll() {
        return eventBookingRepository.listAll();
    }

    @Override
    public List<EventBooking> searchEventsByEventName(String text) {
        return eventBookingRepository.searchEventsByEventName(text);
    }

    @Override
    public List<EventBooking> searchEventsByAtendeeName(String name) {
        return eventBookingRepository.searchEventsByAtendeeName(name);
    }

    @Override
    public List<EventBooking> searchEventsByNumTickets(int tickets) {
        return eventBookingRepository.searchEventsByNumTickets(tickets);
    }

    @Override
    public List<EventBooking> searchEvent(String eventName, String atendeeName, int tickets) {
        return eventBookingRepository.searchEvent(eventName,atendeeName,tickets);
    }
}
