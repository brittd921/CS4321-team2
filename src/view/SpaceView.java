package view;

import controller.SpaceController;
import model.Space;

import java.util.List;

public class SpaceView {

    private final SpaceController controller;

    public SpaceView(SpaceController controller) {
        this.controller = controller;
    }

    public void displayAllSpaces() {
        List<Space> spaces = controller.getAllSpaces();

        if (spaces.isEmpty()) {
            System.out.println("No reservable spaces are currently available.");
            return;
        }

        System.out.println("Available Campus Spaces");
        System.out.println("-----------------------");

        for (Space space : spaces) {
            System.out.println(
                    "Name: " + space.getName()
                            + " | Building: " + space.getBuilding()
                            + " | Capacity: " + space.getCapacity()
            );
        }
    }
}