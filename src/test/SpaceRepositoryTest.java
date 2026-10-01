package test;

import model.Space;
import persistence.SpaceRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceRepositoryTest {
    @Test
    public void testGetAllSpaces() {
        SpaceRepository repository = new SpaceRepository();

        Space space1 = new Space("Library", "Main Building", 100);
        Space space2 = new Space("Computer Lab", "Science Building", 30);

        repository.addSpace(space1);
        repository.addSpace(space2);

        List<Space> spaces = repository.getAllSpaces();

        assertEquals(2, spaces.size());
        assertEquals("Library", spaces.get(0).getName());
        assertEquals("Computer Lab", spaces.get(1).getName());
    }
}
