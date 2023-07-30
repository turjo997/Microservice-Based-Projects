package com.sajal.payment_service.repository;

import com.sajal.payment_service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, Long> {
}