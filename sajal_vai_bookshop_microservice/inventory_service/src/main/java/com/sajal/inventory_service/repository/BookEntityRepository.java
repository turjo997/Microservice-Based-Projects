package com.sajal.inventory_service.repository;


import com.sajal.inventory_service.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

}