package com.example.demo.model.table.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateTableDTO {

    @JsonProperty("restaurantId")
    private UUID restaurantId;

    private TableDTO tableDTO;

    // Getters and setters
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public TableDTO getTableDTO() {
        return tableDTO;
    }

    public void setTableDTO(TableDTO tableDTO) {
        this.tableDTO = tableDTO;
    }
}

