package com.example.demo.model.restaurantUser.DTO;

import com.example.demo.model.table.DTO.TableDTO;

public class PublicRestaurantDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private TableDTO layout;


    public PublicRestaurantDTO(Long id, String name, String imageUrl, TableDTO layout) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.layout = layout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

        public TableDTO getLayout() {
            return layout;
        }

        public void setLayout(TableDTO layout) {
            this.layout = layout;
        }
}
