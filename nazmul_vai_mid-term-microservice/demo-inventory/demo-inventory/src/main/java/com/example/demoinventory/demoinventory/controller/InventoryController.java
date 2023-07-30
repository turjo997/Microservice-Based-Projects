package com.example.demoinventory.demoinventory.controller;

import com.example.demoinventory.demoinventory.model.InventoryModel;
import com.example.demoinventory.demoinventory.model.InventoryResponse;
import com.example.demoinventory.demoinventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping("/book-inventory/update/{bookId}")
    public void createInventoryRecord(@PathVariable Integer bookId,@RequestBody InventoryModel inventoryModel){
        inventoryService.createInventory(bookId, inventoryModel);
    }
    @GetMapping("/book-inventory")
    public List<InventoryModel> getInventoryRecords(@RequestParam String id){
//        return inventoryService.getInventoryRecord(ids);
//        int indexOfEquals = id.indexOf("=");
//        List<String> ids = new ArrayList<>();
//        if (indexOfEquals != -1 && indexOfEquals < id.length() - 1){
//            String requiredIds = id.substring(indexOfEquals+1);
            List<String> ids = Arrays.asList(id.split(","));
            System.out.println(ids);
//        }
        return inventoryService.getInventoryRecord(ids);


//        System.out.println(id);
//        return null;
    }
}
