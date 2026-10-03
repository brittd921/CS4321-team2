package test;

import controller.SpaceController;
import model.Space;
import persistence.SpaceRepository;
import view.SpaceView;

public class SpaceViewTest {

    public static void main(String[] args) {
        SpaceRepository repository = new SpaceRepository();

        repository.addSpace(
                new Space("Library", "Main Building", 100)
        );

        repository.addSpace(
                new Space("Computer Lab", "Science Building", 30)
        );

        SpaceController controller = new SpaceController(repository);
        SpaceView view = new SpaceView(controller);

        view.displayAllSpaces();
    }
}