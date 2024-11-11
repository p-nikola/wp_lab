package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/eventbooking")
public class EventBookingController {

    private final EventService eventService;
    private final EventBookingService eventBookingService;

    public EventBookingController(EventService eventService, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.eventBookingService = eventBookingService;
    }


    @PostMapping("/bookevent")
    public String createBooking(@RequestParam Long eventId, @RequestParam int numTickets, Model model, HttpServletRequest request) {
        Optional<Event> optionalEvent = eventService.findById(eventId);


        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            String attendeeAddress = request.getRemoteAddr();
            EventBooking eventBooking = eventBookingService.placeBooking(event.getName(), "Petko Petkovski", attendeeAddress, numTickets);
            model.addAttribute("booking", eventBooking);
            model.addAttribute("clientIp", attendeeAddress);
            eventBookingService.addBooking(eventBooking.getEventName(), eventBooking.getAttendeeName(), eventBooking.getAttendeeAddress(), Math.toIntExact(eventBooking.getNumberOfTickets()));

            return "bookingConfirmation";
        }

        return "redirect:/events?error=Booking was not successful";

    }

    @GetMapping("/searchbookings")
    public String searhBookings(@RequestParam(required = false) String searchText, @RequestParam(required = false) String numTickets, @RequestParam(required = false) String atendeeName, Model model) {
        if (searchText != null && searchText.isEmpty()) {
            searchText = null;
        }
        if (numTickets != null && numTickets.isEmpty()) {
            numTickets = null;
        }

        if (atendeeName != null && atendeeName.isEmpty()) {
            atendeeName = null;
        }


        if (searchText != null) {
            if (numTickets != null) {
                if (atendeeName != null) {
                    model.addAttribute("eventBookings", eventBookingService.searchEvent(searchText, atendeeName, Integer.parseInt(numTickets)));
                }
            } else {
                model.addAttribute("eventBookings", eventBookingService.searchEventsByEventName(searchText));
            }
        } else {
            model.addAttribute("eventBookings", eventBookingService.listAll());
        }

        return "listBookingConfirmation";
    }


    @GetMapping("/listbookings")
    public String listBookings(Model model) {

        model.addAttribute("eventBookings", eventBookingService.listAll());
        return "listBookingConfirmation";

    }


}
