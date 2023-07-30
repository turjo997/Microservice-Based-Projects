package com.example.paymentapp.demopaymentapp.repository;

import com.example.paymentapp.demopaymentapp.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {
}
