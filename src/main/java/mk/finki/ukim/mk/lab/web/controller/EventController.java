package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepositroyJPA;
import mk.finki.ukim.mk.lab.service.CategoryService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;

    private final CategoryService categoryService;

    public EventController(EventService eventService, LocationService locationService, CategoryService categoryService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("events", eventService.listAll());
        model.addAttribute("locations", locationService.listAll());
        model.addAttribute("categories", categoryService.listAll());

        return "listEvents";

    }

    @GetMapping("/searchevent")
    public String searchEvents(@RequestParam(required = false) String searchText, @RequestParam(required = false) String minRating, @RequestParam(required = false) Long locationId, @RequestParam(required = false) Long categoryId, Model model) {
        if (searchText != null && searchText.isEmpty()) {
            searchText = null;
        }
        if (minRating != null && minRating.isEmpty()) {
            minRating = null;
        }

        if (locationId != null) {
            // Filter by location first
            model.addAttribute("events", eventService.findAllByLocation(locationId));
        } else if (searchText != null) {
            if (minRating != null) {
                model.addAttribute("events", eventService.searchEventsByTextAndScore(searchText, Double.parseDouble(minRating)));
            } else {
                model.addAttribute("events", eventService.searchEvents(searchText));
            }
        } else if (minRating != null) {
            model.addAttribute("events", eventService.searchEventsByScore(Double.parseDouble(minRating)));
        } else if (categoryId != null) {
            model.addAttribute("events", eventService.findAllByCategory(categoryId));
        } else {
            model.addAttribute("events", eventService.listAll());
        }

        model.addAttribute("locations", locationService.listAll());
        model.addAttribute("categories", categoryService.listAll());


        return "listEvents";
    }

    @GetMapping("/add-event")
    @PreAuthorize("hasRole('ADMIN')")
    public String addEventPage(Model model) {
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("categories", categoryService.listAll());

        return "add-event";
    }

    @GetMapping("/edit-form/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editEventForm(@PathVariable Long eventId, Model model) {
        if (eventService.findById(eventId).isPresent()) {
            Event event = eventService.findById(eventId).get();

            List<Location> locationList = locationService.findAll();

            model.addAttribute("locations", locationList);

            model.addAttribute("event", event);

            model.addAttribute("categories", categoryService.listAll());

            return "add-event";
        }
        return "redirect:/events?error=ProductNotFound";
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(@RequestParam(required = false) Long id, @RequestParam String name, @RequestParam String description,
                            @RequestParam double popularityScore, @RequestParam Long location, @RequestParam Long category) {

        eventService.saveEvent(id, name, description, popularityScore, location, category);

        return "redirect:/events";

    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteById(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";

    }

    @PostMapping("/increment/{id}")
    public String incrementRating(@PathVariable Long id, HttpSession session) {

        eventService.hasIncremented(id);

        return "redirect:/events";
    }

}
