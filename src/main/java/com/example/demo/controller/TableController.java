package com.example.demo.controller;

import com.example.demo.model.table.DTO.*;
import com.example.demo.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "http://localhost:3000")
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping("by-restaurant")
    public ResponseEntity<List<TableDTO>> getTablesByRestaurant(@RequestBody @Valid GetTablesByRestaurantDTO getTablesDTO) {
        List<TableDTO> tables = tableService.getTablesByRestaurant(getTablesDTO.getRestaurantId());
        return ResponseEntity.ok(tables);
    }

    @PostMapping
    public ResponseEntity<TableDTO> createTable(@RequestBody @Valid CreateTableDTO dto) {
        TableDTO createdTable = tableService.createTable(dto.getRestaurantId(), dto.getTableDTO());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @PutMapping
    public ResponseEntity<TableDTO> updateTable(@RequestBody @Valid UpdateTableDTO dto) throws AccessDeniedException {
        TableDTO updatedTable = tableService.updateTable(dto.getTableId(), dto.getTableDTO(), dto.getRestaurantId());
        return ResponseEntity.ok(updatedTable);
    }

    @PostMapping("delete")
    public ResponseEntity<Void> deleteTable(@RequestBody @Valid DeleteTableDTO dto) throws AccessDeniedException {
        tableService.deleteTable(dto.getTableId(), dto.getRestaurantId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/grid") // Renamed endpoint for consistency
    public ResponseEntity<List<List<GridLayoutDTO>>> getGridLayout(@RequestParam UUID restaurantId) {
        List<List<GridLayoutDTO>> gridLayout = tableService.getGridLayout(restaurantId);
        return ResponseEntity.ok(gridLayout);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveLayout(@RequestBody @Valid SaveGridLayoutDTO dto) {
        tableService.saveGridLayout(dto.getRestaurantId(), dto.getLayout());
        return ResponseEntity.ok("Layout saved successfully."); // Improved success message
    }
}
