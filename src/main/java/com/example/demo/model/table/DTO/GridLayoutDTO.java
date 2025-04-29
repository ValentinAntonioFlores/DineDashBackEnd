package com.example.demo.model.table.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GridLayoutDTO {

    @JsonProperty("isTable")
    private boolean isTable;

    @JsonProperty("capacity")
    private int capacity;

    @JsonProperty("positionX")
    private int positionX;

    @JsonProperty("positionY")
    private int positionY;

    @JsonProperty("isAvailable")
    private boolean isAvailable;  // Rename reserved to isAvailable

    // Getters and setters
    public boolean isTable() {
        return isTable;
    }

    public void setTable(boolean table) {
        isTable = table;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
