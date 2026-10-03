package test;

import model.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTest {

    // Existing US-1 test
    @Test
    public void testSpaceStoresAndReturnsData() {
        Space space = new Space(
                "Conference Room A",
                "Main Building",
                10
        );

        assertEquals("Conference Room A", space.getName());
        assertEquals("Main Building", space.getBuilding());
        assertEquals(10, space.getCapacity());
    }

    // New US-2 test
    @Test
    public void testSpaceStoresDetailedInformation() {
        Space space = new Space(
                "S001",
                "Library",
                "Main Building",
                100,
                "Large study and meeting space"
        );

        assertEquals("S001", space.getId());
        assertEquals("Library", space.getName());
        assertEquals("Main Building", space.getBuilding());
        assertEquals(100, space.getCapacity());
        assertEquals(
                "Large study and meeting space",
                space.getDescription()
        );
    }
}