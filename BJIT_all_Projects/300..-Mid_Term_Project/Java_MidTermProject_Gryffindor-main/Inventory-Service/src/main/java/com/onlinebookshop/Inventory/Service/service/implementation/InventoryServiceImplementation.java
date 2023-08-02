package com.onlinebookshop.Inventory.Service.service.implementation;

import com.onlinebookshop.Inventory.Service.entity.InventoryEntity;
import com.onlinebookshop.Inventory.Service.model.InventoryModel;
import com.onlinebookshop.Inventory.Service.repository.InventoryRepository;
import com.onlinebookshop.Inventory.Service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<InventoryModel> createNewInventory(InventoryEntity inventory) {
        InventoryEntity savedInventoryEntity = inventoryRepo.save(inventory);
        InventoryModel inventoryModel = this.modelMapper.map(savedInventoryEntity , InventoryModel.class);
        return new ResponseEntity<>(inventoryModel , HttpStatus.CREATED );
    }

    @Override
    public ResponseEntity< List<InventoryModel>> showAllInventory(List<Long> bookIds ) {
        List<InventoryModel> inventoryModels = new ArrayList<>();
        for (Long bookId : bookIds ) {
            inventoryModels.add(this.modelMapper.
                    map(inventoryRepo.findBybookId(bookId), InventoryModel.class)
            );
        }
        return new ResponseEntity<>(inventoryModels , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InventoryModel> showInventoryByBookId(Long bookId) {
        InventoryEntity newInventory = inventoryRepo.findBybookId(bookId);

        InventoryModel inventoryModel = this.modelMapper.map( newInventory , InventoryModel.class);
        return new ResponseEntity<>( inventoryModel, HttpStatus.OK );
    }

    @Override
    public ResponseEntity<InventoryModel> updateInventoryByBookId(Long bookId, InventoryEntity inventory) {
        InventoryEntity newInventory = inventoryRepo.findBybookId(bookId);
        newInventory.setPrice(inventory.getPrice());
        newInventory.setQuantity(inventory.getQuantity());
        inventoryRepo.save(newInventory);

        InventoryModel inventoryModel = this.modelMapper.map( newInventory , InventoryModel.class);
        return new ResponseEntity<>( inventoryModel , HttpStatus.OK );
    }

    @Override
    public ResponseEntity<InventoryModel> deleteInventoryByBookId(Long bookId) {
        InventoryEntity newInventory = inventoryRepo.findBybookId(bookId);
        inventoryRepo.delete(newInventory);

        InventoryModel inventoryModel = this.modelMapper.map( newInventory , InventoryModel.class );
        return new ResponseEntity<>( inventoryModel , HttpStatus.OK );
    }
}