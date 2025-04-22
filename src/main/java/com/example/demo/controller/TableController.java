package com.example.demo.controller;

import com.example.demo.model.table.DTO.TableDTO;
import com.example.demo.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping("{restaurantId}")
    public ResponseEntity<List<TableDTO>> getTablesByRestaurant(@PathVariable UUID restaurantId) {
        List<TableDTO> tables = tableService.getTablesByRestaurant(restaurantId);
        return ResponseEntity.ok(tables);
    }

    @PostMapping
    public ResponseEntity<TableDTO> createTable(@RequestParam UUID restaurantId, @RequestBody TableDTO tableDTO) {
        TableDTO createdTable = tableService.createTable(restaurantId, tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @PutMapping("{tableId}")
    public ResponseEntity<TableDTO> updateTable(
            @PathVariable UUID tableId,
            @RequestBody TableDTO tableDTO,
            @RequestParam UUID restaurantId) throws AccessDeniedException {
        TableDTO updatedTable = tableService.updateTable(tableId, tableDTO, restaurantId);
        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable UUID tableId, @RequestParam UUID restaurantId) throws AccessDeniedException {
        tableService.deleteTable(tableId, restaurantId);
        return ResponseEntity.noContent().build();
    }
}