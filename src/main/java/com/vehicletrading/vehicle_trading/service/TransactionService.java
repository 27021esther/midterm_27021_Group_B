package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.dto.TransactionDTO;
import com.vehicletrading.vehicle_trading.entity.Customer;
import com.vehicletrading.vehicle_trading.entity.Transaction;
import com.vehicletrading.vehicle_trading.entity.Vehicle;
import com.vehicletrading.vehicle_trading.repository.CustomerRepository;
import com.vehicletrading.vehicle_trading.repository.TransactionRepository;
import com.vehicletrading.vehicle_trading.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * Create a transaction (buy/sell).
     * One-to-Many: the saved transaction holds FK customer_id and vehicle_id.
     */
    @Transactional
    public Transaction createTransaction(TransactionDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found: " + request.getCustomerId()));
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
            .orElseThrow(() -> new RuntimeException("Vehicle not found: " + request.getVehicleId()));

        Transaction transaction = Transaction.builder()
            .customer(customer)
            .vehicle(vehicle)
            .transactionDate(request.getTransactionDate() != null 
                ? java.time.LocalDate.parse(request.getTransactionDate()).atStartOfDay() 
                : LocalDateTime.now())
            .salePrice(request.getAmount())
            .transactionType(Transaction.TransactionType.valueOf(request.getTransactionType()))
            .build();

        // Mark vehicle as sold if SALE transaction
        if (transaction.getTransactionType() == Transaction.TransactionType.SALE) {
            vehicle.setStatus(Vehicle.VehicleStatus.SOLD);
            vehicleRepository.save(vehicle);
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getByCustomer(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    /** Paginated transaction list sorted by transactionDate descending */
    public Page<Transaction> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").descending());
        return transactionRepository.findAll(pageable);
    }
}
