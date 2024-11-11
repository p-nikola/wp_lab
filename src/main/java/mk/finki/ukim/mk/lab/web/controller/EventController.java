package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;

    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("events", eventService.listAll());

        return "listEvents";

    }

    @GetMapping("/searchevent")
    public String searchEvents(@RequestParam(required = false) String searchText, @RequestParam(required = false) String minRating, Model model) {
        if (searchText != null && searchText.isEmpty()) {
            searchText = null;
        }
        if (minRating != null && minRating.isEmpty()) {
            minRating = null;
        }


        if (searchText != null) {
            if (minRating != null) {
                model.addAttribute("events", eventService.searchEventsByTextAndScore(searchText, Double.parseDouble(minRating)));
            } else {
                model.addAttribute("events", eventService.searchEvents(searchText));
            }
        } else if (minRating != null) {
            model.addAttribute("events", eventService.searchEventsByScore(Double.parseDouble(minRating)));

        } else {
            model.addAttribute("events", eventService.listAll());
        }

        return "listEvents";
    }

    @GetMapping("/add-event")
    public String addEventPage(Model model) {
        model.addAttribute("locations", locationService.findAll());

        return "add-event";
    }

    @GetMapping("/edit-form/{eventId}")
    public String editEventForm(@PathVariable Long eventId, Model model) {
        if (eventService.findById(eventId).isPresent()) {
            Event event = eventService.findById(eventId).get();

            List<Location> locationList = locationService.findAll();

            model.addAttribute("locations", locationList);

            model.addAttribute("event", event);

            return "add-event";
        }
        return "redirect:/events?error=ProductNotFound";
    }


    @PostMapping("/add")
    public String saveEvent(@RequestParam(required = false) Long id, @RequestParam String name, @RequestParam String description,
                            @RequestParam double popularityScore, @RequestParam Long location) {

        eventService.saveEvent(id, name, description, popularityScore, location);

        return "redirect:/events";

    }


    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";

    }
}
