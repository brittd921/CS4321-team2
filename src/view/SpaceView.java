package view;

import controller.SpaceController;
import model.Space;

import java.util.List;

public class SpaceView {

    private final SpaceController controller;

    public SpaceView(SpaceController controller) {
        this.controller = controller;
    }

    // US-1: Display all spaces
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

    // US-2: Display details for a selected space
    public void displaySpaceDetails(String name) {
        Space space = controller.getSpaceDetails(name);

        if (space == null) {
            System.out.println("Space not found.");
            return;
        }

        System.out.println("Space Details");
        System.out.println("-------------");
        System.out.println("Name: " + space.getName());
        System.out.println("Building: " + space.getBuilding());
        System.out.println("Capacity: " + space.getCapacity());
        System.out.println("Description: " + space.getDescription());
    }

    // US-3: Display spaces that meet a minimum capacity
    public void displaySpacesByCapacity(int minCapacity) {
        List<Space> spaces = controller.getSpacesByCapacity(minCapacity);

        if (spaces.isEmpty()) {
            System.out.println(
                    "No spaces found with a capacity of "
                            + minCapacity
                            + " or more."
            );
            return;
        }

        System.out.println(
                "Spaces with capacity of "
                        + minCapacity
                        + " or more"
        );
        System.out.println("--------------------------------");

        for (Space space : spaces) {
            System.out.println(
                    "Name: " + space.getName()
                            + " | Building: " + space.getBuilding()
                            + " | Capacity: " + space.getCapacity()
            );
        }
    }
}