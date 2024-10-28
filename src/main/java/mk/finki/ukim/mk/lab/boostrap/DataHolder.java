package mk.finki.ukim.mk.lab.boostrap;


import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Event> events= new ArrayList<>();


    @PostConstruct
    public void init(){
        events.add(new Event("Music Festival", "Annual outdoor music festival", 85.0));
        events.add(new Event("Art Expo", "Modern art exhibition", 70.5));
        events.add(new Event("Science Fair", "Science projects and innovation", 65.0));
        events.add(new Event("Tech Conference", "Latest in tech and gadgets", 90.0));
        events.add(new Event("Food Fest", "Enjoy cuisines from around the world", 75.0));
        events.add(new Event("Film Screening", "Indie films showcase", 68.5));
        events.add(new Event("Book Fair", "Meet authors and publishers", 72.0));
        events.add(new Event("Marathon", "Annual charity run", 80.0));
        events.add(new Event("Dance Workshop", "Learn from professional dancers", 60.0));
        events.add(new Event("Comedy Night", "Stand-up comedy from top comedians", 88.5));


    }




}
