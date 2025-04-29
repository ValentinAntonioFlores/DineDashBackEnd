package com.example.demo.services;


import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.DTO.GridLayoutDTO;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.model.table.DTO.TableDTO;
import com.example.demo.repository.RestaurantUserRepository;
import com.example.demo.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void saveGridLayout(UUID restaurantId, List<List<GridLayoutDTO>> gridLayout) {

        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID must not be null.");
        }
        // Delete all current tables for this restaurant
        List<RestaurantTable> existingTables = tableRepository.findByRestaurant_IdRestaurante(restaurantId);
        tableRepository.deleteAll(existingTables);

        // Rebuild tables from grid
        RestaurantUser restaurantUser = restaurantUserRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        for (int x = 0; x < gridLayout.size(); x++) {
            for (int y = 0; y < gridLayout.get(x).size(); y++) {
                GridLayoutDTO cell = gridLayout.get(x).get(y);
                if (cell.isTable()) {
                    RestaurantTable table = new RestaurantTable();
                    table.setRestaurant(restaurantUser);
                    table.setPositionX(x); // Use loop index
                    table.setPositionY(y); // Use loop index
                    table.setCapacity(cell.getCapacity());
                    table.setAvailable(cell.isAvailable());
                    tableRepository.save(table);
                }
            }
        }
    }

    @Transactional
    public List<List<GridLayoutDTO>> getGridLayout(UUID restaurantId) {
        // Retrieve all tables associated with the restaurant
        List<RestaurantTable> tables = tableRepository.findByRestaurant_IdRestaurante(restaurantId);
        if (tables.isEmpty()) {
            return List.of();  // Return empty grid if no tables are found
        }

        // Find the max X and Y positions for grid dimensions
        int maxX = tables.stream().mapToInt(RestaurantTable::getPositionX).max().orElse(0);
        int maxY = tables.stream().mapToInt(RestaurantTable::getPositionY).max().orElse(0);

        // Initialize the grid based on max X and Y
        List<List<GridLayoutDTO>> grid = new java.util.ArrayList<>();
        for (int x = 0; x <= maxX; x++) {
            List<GridLayoutDTO> row = new java.util.ArrayList<>();
            for (int y = 0; y <= maxY; y++) {
                row.add(new GridLayoutDTO());  // Initialize each cell as empty
            }
            grid.add(row);
        }

        // Fill grid with table data from the database
        for (RestaurantTable table : tables) {
            GridLayoutDTO dto = new GridLayoutDTO();
            dto.setTable(true);  // Mark this cell as a table
            dto.setCapacity(table.getCapacity());  // Set table capacity
            dto.setAvailable(table.isAvailable());  // Set availability (true if available)

            // Ensure we don't go out of bounds in the grid
            if (table.getPositionX() <= maxX && table.getPositionY() <= maxY) {
                grid.get(table.getPositionX()).set(table.getPositionY(), dto);
            }
        }

        return grid;
    };
}

