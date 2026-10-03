package model;

public class Space {

    private String id;
    private String name;
    private String building;
    private int capacity;
    private String description;

    public Space(String name, String building, int capacity) {
        this.name = name;
        this.building = building;
        this.capacity = capacity;
    }

    public Space(String id, String name, String building, int capacity, String description) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.capacity = capacity;
        this.description = description;
    }

    public String getId() {
        return id;
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

    public String getDescription() {
        return description;
    }
}