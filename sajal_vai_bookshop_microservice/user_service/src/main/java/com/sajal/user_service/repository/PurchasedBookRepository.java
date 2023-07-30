package com.sajal.user_service.repository;

import com.sajal.user_service.entity.PurchasedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasedBookRepository extends JpaRepository<PurchasedBook, Long> {

    Optional<PurchasedBook> findPurchasedBooksByUserId(Long userId);


    List<PurchasedBook> findAllByUserId(Long userId);

    Optional<PurchasedBook> findPurchasedBooksByUserIdAndBookId(Long userId, Long bookId);
}