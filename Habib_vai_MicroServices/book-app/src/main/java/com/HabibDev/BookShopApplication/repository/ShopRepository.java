package com.HabibDev.BookShopApplication.repository;

import com.HabibDev.BookShopApplication.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
    // Additional methods if needed
    //ShopEntity
}

