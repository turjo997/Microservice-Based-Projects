package com.example.demoinventory.demoinventory.service.impl;

import com.example.demoinventory.demoinventory.entity.Inventory;
import com.example.demoinventory.demoinventory.model.InventoryModel;
import com.example.demoinventory.demoinventory.model.InventoryResponse;
import com.example.demoinventory.demoinventory.repository.InventoryRepository;
import com.example.demoinventory.demoinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    @Override
    public void createInventory(Integer bookId, InventoryModel inventoryModel) {
        Optional<Inventory> inventory = inventoryRepository.findById(bookId);
        if(!inventory.isPresent()){
            Inventory requiredInventory = Inventory.builder()
                                .bookId(bookId)
                                .price(inventoryModel.getPrice())
                                .quantity(inventoryModel.getQuantity()).build();
            inventoryRepository.save(requiredInventory);
        }else {
            Inventory required = inventory.get();
            required.setPrice(inventoryModel.getPrice());
            required.setQuantity(inventoryModel.getQuantity());
            inventoryRepository.save(required);
        }
    }

    @Override
    public List<InventoryModel> getInventoryRecord(List<String> ids) {
//        List<Inventory> listOfInvs = inventoryRepository.findAll();
        List<Integer> requiredIds = new ArrayList<>();
        for(String idb:ids){
            Integer requiredId = Integer.valueOf(idb);
            requiredIds.add(requiredId);
        }
        List<InventoryModel> listOfInvModels = new ArrayList<>();

        for(Integer id: requiredIds){
            Optional<Inventory> invs = inventoryRepository.findById(id);
            if(invs.isPresent()){
                Inventory in = invs.get();
                InventoryModel inventory = InventoryModel.builder().
                        price(in.getPrice())
                        .quantity(in.getQuantity()).build();
                listOfInvModels.add(inventory);
            }

        }

//        for(Inventory inv: listOfInvs){
//            InventoryResponse inventory = InventoryResponse.builder()
//                                                .id(inv.getBookId())
//                                                .price(inv.getPrice())
//                                                .quantity(inv.getQuantity()).build();
//            listOfInvModels.add(inventory);
//        }
        return listOfInvModels;
    }
}
