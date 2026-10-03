package persistence;

import model.Space;
import java.util.ArrayList;
import java.util.List;


public class SpaceRepository {

    private final List<Space> spaces = new ArrayList<>();

    public void addSpace(Space space) {
        spaces.add(space);
    }


    public List<Space> getAllSpaces() {
        return new ArrayList<>(spaces);
    }
}
