package com.HabibDev.BookShopApplication.controller;


import com.HabibDev.BookShopApplication.model.PaymentRequest;
import com.HabibDev.BookShopApplication.model.ShopModel;
import com.HabibDev.BookShopApplication.service.BookShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class BookShopContoller {

    private final BookShopService bookShopService;

    @PostMapping("/create")
    public ResponseEntity<Object> createShop(@RequestBody ShopModel requestModel) {
        return bookShopService.addShop(requestModel);
    }

    @PostMapping("/add-amount")
    public void addAmountToBooks(@RequestBody PaymentRequest requestModel) {
        bookShopService.addAmountToBooks(requestModel);
    }



}
