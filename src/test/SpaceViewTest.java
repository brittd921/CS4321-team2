package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import view.SpaceView;

public class SpaceViewTest {

    public static void main(String[] args) {

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

        repository.addSpace(
                new Space(
                        "S002",
                        "Computer Lab",
                        "Science Building",
                        30,
                        "Computer workspace for students"
                )
        );

        repository.addSpace(
                new Space(
                        "S003",
                        "Conference Room",
                        "Student Center",
                        50,
                        "Meeting and presentation space"
                )
        );

        SpaceController controller = new SpaceController(repository);
        SpaceView view = new SpaceView(controller);

        // US-1
        System.out.println("US-1 TEST");
        view.displayAllSpaces();

        // US-2
        System.out.println();
        System.out.println("US-2 TEST");
        view.displaySpaceDetails("Library");

        // US-3 - Matching spaces
        System.out.println();
        System.out.println("US-3 TEST - MATCHING SPACES");
        view.displaySpacesByCapacity(50);

        // US-3 - No matching spaces
        System.out.println();
        System.out.println("US-3 TEST - NO MATCHING SPACES");
        view.displaySpacesByCapacity(200);
    }
}