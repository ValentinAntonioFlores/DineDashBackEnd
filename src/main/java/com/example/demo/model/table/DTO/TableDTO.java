package com.example.demo.model.table.DTO;


import java.util.UUID;

public class TableDTO {
    private UUID idTable; // PK
    private int capacity;
    private int positionX;
    private int positionY;
    private boolean isAvailable;

    public TableDTO() {}
    public TableDTO(UUID idTable, int capacity, int positionX, int positionY, boolean isAvailable) {
        this.idTable = idTable;
        this.capacity = capacity;
        this.positionX = positionX;
        this.positionY = positionY;
        this.isAvailable = isAvailable;
    }

    public UUID getIdTable() { return idTable; }
    public void setIdTable(UUID idTable) { this.idTable = idTable; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getPositionX() { return positionX; }
    public void setPositionX(int positionX) { this.positionX = positionX; }

    public int getPositionY() { return positionY; }

    public int setPositionY(int positionY) { this.positionY = positionY; return positionY; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

}
