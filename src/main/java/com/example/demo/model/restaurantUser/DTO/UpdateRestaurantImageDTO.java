package com.example.demo.model.restaurantUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public  class UpdateRestaurantImageDTO {

    @JsonProperty("imageBase64")
    private String imagenBase64;

    public UpdateRestaurantImageDTO() {}

    public UpdateRestaurantImageDTO(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }
    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }
}