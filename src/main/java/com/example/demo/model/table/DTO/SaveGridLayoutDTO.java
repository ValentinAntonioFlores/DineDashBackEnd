package com.example.demo.model.table.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class SaveGridLayoutDTO {

    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @JsonProperty("gridLayout")
    private List<List<GridLayoutDTO>> layout;

    public SaveGridLayoutDTO() {}

    public SaveGridLayoutDTO(UUID restaurantId, List<List<GridLayoutDTO>> layout) {
        this.restaurantId = restaurantId;
        this.layout = layout;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<List<GridLayoutDTO>> getLayout() {
        return layout;
    }

    public void setLayout(List<List<GridLayoutDTO>> layout) {
        this.layout = layout;
    }
}
