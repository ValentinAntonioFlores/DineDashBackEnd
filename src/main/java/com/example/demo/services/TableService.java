package com.example.demo.services;


import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.model.table.DTO.TableDTO;
import com.example.demo.repository.RestaurantUserRepository;
import com.example.demo.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    public List<TableDTO> getTablesByRestaurant(UUID restaurantId) {
        List<RestaurantTable> tables = tableRepository.findByRestaurant_IdRestaurante(restaurantId);
        return tables.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public RestaurantTable getTableById(UUID tableId) {
        return tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found with ID: " + tableId));
    }

    public TableDTO createTable(UUID restaurantId, TableDTO tableDTO) {
        if (tableRepository.existsByRestaurant_IdRestauranteAndPositionXAndPositionY(restaurantId, tableDTO.getPositionX(), tableDTO.getPositionY())) {
            throw new IllegalArgumentException("A table already exists at the specified position.");
        }

        RestaurantUser restaurantUser = restaurantUserRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        RestaurantTable table = new RestaurantTable();
        table.setRestaurant(restaurantUser);
        table.setCapacity(tableDTO.getCapacity());
        table.setPositionX(tableDTO.getPositionX());
        table.setPositionY(tableDTO.getPositionY());
        table.setAvailable(true);

        table = tableRepository.save(table);
        return convertToDTO(table);
    }

    public TableDTO updateTable(UUID tableId, TableDTO tableDTO, UUID restaurantId) throws AccessDeniedException {
        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));

        if (!table.getRestaurant().getIdRestaurante().equals(restaurantId)) {
            throw new AccessDeniedException("You do not have permission to update this table.");
        }

        table.setCapacity(tableDTO.getCapacity());
        table.setPositionX(tableDTO.getPositionX());
        table.setPositionY(tableDTO.getPositionY());
        table.setAvailable(tableDTO.isAvailable());

        table = tableRepository.save(table);
        return convertToDTO(table);
    }

    public void deleteTable(UUID tableId, UUID restaurantId) throws AccessDeniedException {
        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));

        if (!table.getRestaurant().getIdRestaurante().equals(restaurantId)) {
            throw new AccessDeniedException("You do not have permission to delete this table.");
        }

        tableRepository.deleteById(tableId);
    }

    private TableDTO convertToDTO(RestaurantTable table) {
        TableDTO tableDto = new TableDTO();
        tableDto.setIdTable(table.getId());
        tableDto.setCapacity(table.getCapacity());
        tableDto.setPositionX(table.getPositionX());
        tableDto.setPositionY(table.getPositionY());
        tableDto.setAvailable(table.isAvailable());
        return tableDto;
    }
}