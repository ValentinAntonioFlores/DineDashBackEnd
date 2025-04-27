package com.example.demo.controller;

import com.example.demo.model.table.DTO.*;
import com.example.demo.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping("/by-restaurant")
    public ResponseEntity<List<TableDTO>> getTablesByRestaurant(@RequestBody GetTablesByRestaurantDTO getTablesDTO) {
        List<TableDTO> tables = tableService.getTablesByRestaurant(getTablesDTO.getRestaurantId());
        return ResponseEntity.ok(tables);
    }

    @PostMapping
    public ResponseEntity<TableDTO> createTable(@RequestBody CreateTableDTO dto) {
        TableDTO createdTable = tableService.createTable(dto.getRestaurantId(), dto.getTableDTO());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }

    @PutMapping
    public ResponseEntity<TableDTO> updateTable(@RequestBody UpdateTableDTO dto) throws AccessDeniedException {
        TableDTO updatedTable = tableService.updateTable(dto.getTableId(), dto.getTableDTO(), dto.getRestaurantId());
        return ResponseEntity.ok(updatedTable);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteTable(@RequestBody DeleteTableDTO dto) throws AccessDeniedException {
        tableService.deleteTable(dto.getTableId(), dto.getRestaurantId());
        return ResponseEntity.noContent().build();
    }
}