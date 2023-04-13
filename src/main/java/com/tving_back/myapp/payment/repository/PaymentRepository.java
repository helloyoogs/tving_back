package com.tving_back.myapp.payment.repository;

import com.tving_back.myapp.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
