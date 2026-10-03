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

        SpaceController controller = new SpaceController(repository);
        SpaceView view = new SpaceView(controller);

        System.out.println("US-1 TEST");
        view.displayAllSpaces();

        System.out.println();
        System.out.println("US-2 TEST");
        view.displaySpaceDetails("Library");
    }
}