package com.HabibDev.BookShopApplication.service;

import com.HabibDev.BookShopApplication.model.BookRequestModel;
import com.HabibDev.BookShopApplication.model.PaymentRequest;
import com.HabibDev.BookShopApplication.model.ShopModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookShopService {
    ResponseEntity<Object> addShop(ShopModel requestModel);
    void addAmountToBooks( PaymentRequest requestModel);
}
