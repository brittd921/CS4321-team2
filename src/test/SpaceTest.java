package test;

import model.Space;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTest {

    @Test
    void testSpaceStoresAndReturnsData() {
        Space space = new Space(
                "Conference Room A",
                "Main Building",
                10
        );

        assertEquals("Conference Room A", space.getName());
        assertEquals("Main Building", space.getBuilding());
        assertEquals(10, space.getCapacity());
    }
}
