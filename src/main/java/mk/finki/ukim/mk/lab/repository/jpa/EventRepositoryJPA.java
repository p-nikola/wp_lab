package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepositoryJPA extends JpaRepository<Event,Long> {
    Optional<Event> findByName(String name);
    List<Event> findAllByLocation_Id(Long locationId);

    List<Event> searchByName(String name);
    List<Event> searchByNameAndPopularityScore(String name,double popularityScore);
    List<Event> searchByPopularityScore(double popularityScore);

}
