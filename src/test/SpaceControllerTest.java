package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpaceControllerTest {

    // US-1: Test retrieving all spaces
    @Test
    void getAllSpacesReturnsSpacesFromRepository() {
        SpaceRepository repository = new SpaceRepository();

        repository.addSpace(
                new Space(
                        "Library",
                        "Main Building",
                        100
                )
        );

        repository.addSpace(
                new Space(
                        "Computer Lab",
                        "Science Building",
                        30
                )
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

    // US-2: Test when the requested space does not exist
    @Test
    void getSpaceDetailsReturnsNullWhenSpaceIsNotFound() {
        SpaceRepository repository = new SpaceRepository();

        repository.addSpace(
                new Space(
                        "S001",
                        "Library",
                        "Main Building",
                        100,
                        "Large study and meeting space"
                )
        );

        SpaceController controller = new SpaceController(repository);

        Space result = controller.getSpaceDetails("Gym");

        assertNull(result);
    }

    // US-3: Test filtering spaces by minimum capacity
    @Test
    void getSpacesByCapacityReturnsMatchingSpaces() {
        SpaceRepository repository = new SpaceRepository();

        repository.addSpace(
                new Space(
                        "Library",
                        "Main Building",
                        100
                )
        );

        repository.addSpace(
                new Space(
                        "Computer Lab",
                        "Science Building",
                        30
                )
        );

        repository.addSpace(
                new Space(
                        "Conference Room",
                        "Student Center",
                        50
                )
        );

        SpaceController controller = new SpaceController(repository);

        List<Space> spaces = controller.getSpacesByCapacity(50);

        assertEquals(2, spaces.size());
        assertEquals("Library", spaces.get(0).getName());
        assertEquals("Conference Room", spaces.get(1).getName());
    }
}