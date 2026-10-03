package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SpaceControllerTest {

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
}