package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SpaceControllerTest {

    // US-1: Test retrieving all spaces
    @Test
    void getAllSpacesReturnsSpacesFromRepository() {
        SpaceRepository repository = new SpaceRepository();

        repository.addSpace(
                new Space("Library", "Main Building", 100)
        );

        repository.addSpace(
                new Space("Computer Lab", "Science Building", 30)
        );

        SpaceController controller = new SpaceController(repository);

        List<Space> spaces = controller.getAllSpaces();

        assertFalse(spaces.isEmpty());
        assertEquals(2, spaces.size());
        assertEquals("Library", spaces.get(0).getName());
        assertEquals("Computer Lab", spaces.get(1).getName());
    }

    // US-2: Test retrieving details for one specific space
    @Test
    void getSpaceDetailsReturnsRequestedSpace() {
        SpaceRepository repository = new SpaceRepository();

        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        repository.addSpace(space);

        SpaceController controller = new SpaceController(repository);

        Space result = controller.getSpaceDetails("Library");

        assertEquals("S001", result.getId());
        assertEquals("Library", result.getName());
        assertEquals("Main Building", result.getBuilding());
        assertEquals(100, result.getCapacity());
        assertEquals(
                "Large study and meeting space",
                result.getDescription()
        );
    }
}