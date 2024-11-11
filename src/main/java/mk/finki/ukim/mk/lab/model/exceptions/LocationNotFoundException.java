package mk.finki.ukim.mk.lab.model.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long id) {
        super(String.format("Location with %d was not found", id));
    }
}
