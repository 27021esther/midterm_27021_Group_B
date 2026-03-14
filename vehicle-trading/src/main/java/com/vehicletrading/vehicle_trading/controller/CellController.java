package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.entity.Cell;
import com.vehicletrading.vehicle_trading.service.CellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEnti

import java.util.List;

@RestController
@RequestMapping("/api/cells")
@RequiredArgsConstructor
public class CellController {

    private final CellService cellService;

    // POST /api/cells?sectorId=1
    @PostMapping
    public ResponseEntity<Cell> create(
            @RequestBody Cell cell,
            @RequestParam Long sectorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(cellService.save(cell, sectorId));
    }

    // GET /api/cells
    @GetMapping
    public ResponseEntity<List<Cell>> getAll() {
        return ResponseEntity.ok(cellService.getAll());
    }

    // GET /api/cells/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cell> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cellService.getById(id));
    }

    // GET /api/cells/by-sector/{sectorId}
    @GetMapping("/by-sector/{sectorId}")
    public ResponseEntity<List<Cell>> getBySectorId(@PathVariable Long sectorId) {
        return ResponseEntity.ok(cellService.getBySectorId(sectorId));
    }
}
