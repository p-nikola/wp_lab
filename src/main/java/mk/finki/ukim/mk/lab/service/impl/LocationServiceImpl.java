package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepositoryJPA;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepositoryJPA locationRepositoryJPA;

    public LocationServiceImpl(LocationRepositoryJPA locationRepositoryJPA) {
        this.locationRepositoryJPA = locationRepositoryJPA;
    }

    @Override
    public List<Location> findAll() {
        return locationRepositoryJPA.findAll();
    }

    @Override
    public List<Location> listAll() {
        return locationRepositoryJPA.findAll();
    }


}
