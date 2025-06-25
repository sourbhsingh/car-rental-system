package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.enitity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
