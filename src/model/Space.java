package model;

public class Space {
    private String name;
    private String building;
    private int capacity;

    public Space(String name, String building, int capacity) {
        this.name = name;
        this.building = building;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public int getCapacity() {
        return capacity;
    }
}
