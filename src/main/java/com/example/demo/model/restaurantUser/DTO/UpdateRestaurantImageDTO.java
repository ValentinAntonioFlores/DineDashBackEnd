package com.example.demo.model.restaurantUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public  class UpdateRestaurantImageDTO {



    @JsonProperty("idRestaurante")
    private String idRestaurante;

    @JsonProperty("image")
    private String imagenBase64;

    public UpdateRestaurantImageDTO(String idRestaurante, String imagenBase64) {
        this.idRestaurante = idRestaurante;
        this.imagenBase64 = imagenBase64;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }
    public void setIdRestaurante(String restaurantId) {
        this.idRestaurante = restaurantId;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }
    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }
}