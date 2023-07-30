package com.example.demoinventory.demoinventory.service;

import com.example.demoinventory.demoinventory.model.InventoryModel;
import com.example.demoinventory.demoinventory.model.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    void createInventory(Integer bookId, InventoryModel inventoryModel);
    List<InventoryModel> getInventoryRecord(List<String> ids);
}
