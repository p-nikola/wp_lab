package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventBookingRepositoryJPA extends JpaRepository<EventBooking,Long> {



    List<EventBooking> findByEvent_NameContainingIgnoreCase(String text);
    List<EventBooking> findByAttendeeNameContainingIgnoreCase(String name);
    List<EventBooking> findByNumberOfTickets(long tickets);
    List<EventBooking> findByEventNameAndAttendeeNameAndNumberOfTickets(String eventName, String atendeeName, long tickets);

}
