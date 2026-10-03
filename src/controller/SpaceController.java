package controller;

import model.Space;
import persistence.SpaceRepository;

import java.util.List;

public class SpaceController {

    private final SpaceRepository repository;

    public SpaceController(SpaceRepository repository) {
        this.repository = repository;
    }

    public List<Space> getAllSpaces() {
        return repository.getAllSpaces();
    }

    public Space getSpaceDetails(String name) {
        return repository.findSpaceByName(name);
    }

    public List<Space> getSpacesByCapacity(int minCapacity) {
        return repository.findSpacesByCapacity(minCapacity);
    }
}