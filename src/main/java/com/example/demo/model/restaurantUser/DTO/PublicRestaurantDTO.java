package com.example.demo.model.restaurantUser.DTO;

import com.example.demo.model.table.DTO.TableDTO;

import java.util.List;

public class PublicRestaurantDTO {

        private String id;
        private String name;
        private String imageUrl;
        private List<TableDTO> layout;


        public PublicRestaurantDTO(String id, String name, String imageUrl, List<TableDTO> layout) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.layout = layout;
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
}
