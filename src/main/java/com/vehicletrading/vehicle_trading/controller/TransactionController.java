package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.dto.TransactionDTO;
import com.vehicletrading.vehicle_trading.entity.Transaction;
import com.vehicletrading.vehicle_trading.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * POST /api/transactions
     * Creates a transaction (buy/sell) linking a Customer and a Vehicle.
     * Demonstrates One-to-Many: one customer can have many transactions.
     */
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(transactionService.createTransaction(request));
    }

    /**
     * GET /api/transactions?page=0&size=10
     * Paginated + sorted (by transactionDate desc) list of all transactions.
     */
    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionService.getAllPaged(page, size));
    }

    // GET /api/transactions/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Transaction>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(transactionService.getByCustomer(customerId));
    }
}
