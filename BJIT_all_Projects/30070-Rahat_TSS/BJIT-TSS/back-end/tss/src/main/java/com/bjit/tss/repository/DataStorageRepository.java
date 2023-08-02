package com.bjit.tss.repository;

import com.bjit.tss.entity.DataStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataStorageRepository extends JpaRepository<DataStorage, Long> {

    Optional<DataStorage> findByDataKey(String dataKey);

}