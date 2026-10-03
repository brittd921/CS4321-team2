package test;

import model.Space;
import persistence.SpaceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceRepositoryTest {

    // US-1: Test retrieving all spaces
    @Test
    public void testGetAllSpaces() {
        SpaceRepository repository = new SpaceRepository();

        Space space1 = new Space(
                "Library",
                "Main Building",
                100
        );

        Space space2 = new Space(
                "Computer Lab",
                "Science Building",
                30
        );

        repository.addSpace(space1);
        repository.addSpace(space2);

        List<Space> spaces = repository.getAllSpaces();

        assertEquals(2, spaces.size());
        assertEquals("Library", spaces.get(0).getName());
        assertEquals("Computer Lab", spaces.get(1).getName());
    }

    // US-2: Test retrieving a specific space by name
    @Test
    public void testFindSpaceByNameReturnsSpace() {
        SpaceRepository repository = new SpaceRepository();

        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        repository.addSpace(space);

        Space result = repository.findSpaceByName("Library");

        assertNotNull(result);
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
    public void testFindSpaceByNameReturnsNullWhenNotFound() {
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

        Space result = repository.findSpaceByName("Gym");

        assertNull(result);
    }
}