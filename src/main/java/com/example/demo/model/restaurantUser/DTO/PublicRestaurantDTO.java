package com.example.demo.model.restaurantUser.DTO;

import com.example.demo.model.table.DTO.TableDTO;

import java.util.List;

public class PublicRestaurantDTO {

        private String id;
        private String name;
        private String imageUrl;
        private List<TableDTO> layout;
        private double averageRating;
        private Double latitude;
        private Double longitude;


        public PublicRestaurantDTO(String id, String name, String imageUrl, List<TableDTO> layout, double averageRating, Double latitude, Double longitude) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.layout = layout;
            this.averageRating = averageRating;
            this.latitude = latitude;
            this.longitude = longitude;
    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<TableDTO> getLayout() {
            return layout;
        }

        public void setLayout(List<TableDTO> layout) { this.layout = layout; }

        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
}

