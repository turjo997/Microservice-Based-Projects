package com.HabibDev.Payment.repository;



import com.HabibDev.Payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

   PaymentEntity findByCustomerId(Integer customerId);
   // @Query("SELECT p.accountBalance FROM PaymentEntity p WHERE p.customerId = ?1")
//
   // @Query("UPDATE PaymentEntity p SET p.accountBalance = ?2 WHERE p.customerId = ?1")
   // void updateAccountBalance(String customerId, Double updatedBalance);
}