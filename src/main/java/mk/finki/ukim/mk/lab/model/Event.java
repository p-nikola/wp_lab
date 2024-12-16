package mk.finki.ukim.mk.lab.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double popularityScore;
    @ManyToOne
    private Location location;
    private boolean hasUpvote;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventBooking> eventBookings;


    public Event(String name, String description, double popularityScore, Location location,Category category) {
        //this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
        this.hasUpvote=false;
        this.category=category;
    }
}
