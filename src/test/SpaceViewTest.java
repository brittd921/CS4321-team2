package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import view.SpaceView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private SpaceView viewWith(Space... spaces) {
        SpaceRepository repository = new SpaceRepository();
        for (Space space : spaces) {
            repository.addSpace(space);
        }
        return new SpaceView(new SpaceController(repository));
    }

    @Test
    void displaysMultipleSpaces() {
        SpaceView view = viewWith(
            new Space("Library", "Main Building", 100),
            new Space("Computer Lab", "Science Building", 30)
        );

        view.displayAllSpaces();

        String output = outContent.toString();
        assertTrue(output.contains("Library"));
        assertTrue(output.contains("Computer Lab"));
    }

    @Test
    void displaysRequiredSpaceInformation() {
        SpaceView view = viewWith(new Space("Library", "Main Building", 100));

        view.displayAllSpaces();

        String output = outContent.toString();
        assertTrue(output.contains("Library"), "space name should be displayed");
        assertTrue(output.contains("Main Building"), "building should be displayed");
        assertTrue(output.contains("100"), "capacity should be displayed");
    }

    @Test
    void displaysMessageWhenNoSpacesAvailable() {
        SpaceView view = viewWith();

        view.displayAllSpaces();

        String output = outContent.toString();
        assertTrue(output.contains("No reservable spaces are currently available."));
    }
}
