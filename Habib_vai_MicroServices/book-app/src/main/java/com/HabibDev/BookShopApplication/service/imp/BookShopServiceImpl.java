package com.HabibDev.BookShopApplication.service.imp;


import com.HabibDev.BookShopApplication.entity.ShopEntity;
import com.HabibDev.BookShopApplication.model.PaymentRequest;
import com.HabibDev.BookShopApplication.model.ShopModel;
import com.HabibDev.BookShopApplication.repository.ShopRepository;
import com.HabibDev.BookShopApplication.service.BookShopService;;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookShopServiceImpl implements BookShopService {
    private final ShopRepository shopRepository;


    @Override
    public ResponseEntity<Object> addShop(ShopModel requestModel) {
        try {
            // Create a new ShopEntity instance using the request model data
            ShopEntity shopEntity = ShopEntity.builder()
                    .bookIds(requestModel.getBookIds())
                    .amount(requestModel.getAmount())
                    .build();

            // Save the shopEntity in the repository
            shopRepository.save(shopEntity);

            // If the save operation is successful, return a success message
            return new ResponseEntity<>("Shop added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            // If an error occurs, return an error response
            return new ResponseEntity<>("Failed to add shop", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addAmountToBooks(PaymentRequest requestModel) {
        Integer bookId = requestModel.getBookId();
        Integer amount = requestModel.getAmount();

        if (bookId >= 1 && bookId <= 3) {
            // Logic to add the amount to book 1-3
            Optional<ShopEntity> shopOptional = shopRepository.findById(3); // Assuming the 1st book shop has shopId = 1
            if (shopOptional.isPresent()) {
                ShopEntity shopEntity = shopOptional.get();
                Integer currentAmount = shopEntity.getAmount();
                Integer newAmount = currentAmount + (Integer.valueOf(amount));
                shopEntity.setAmount(newAmount);
                shopRepository.save(shopEntity);
            }
        } else if (bookId >= 4 && bookId <= 6) {
            // Logic to add the amount to book 4-6
            Optional<ShopEntity> shopOptional = shopRepository.findById(4); // Assuming the 1st book shop has shopId = 1
            if (shopOptional.isPresent()) {
                ShopEntity shopEntity = shopOptional.get();
                Integer currentAmount = shopEntity.getAmount();
                Integer newAmount = currentAmount + (Integer.valueOf(amount));
                shopEntity.setAmount(newAmount);
                shopRepository.save(shopEntity);
            }
        } else {
            // Handle the case when the bookId is not within the specified range
            Optional<ShopEntity> shopOptional = shopRepository.findById(5); // Assuming the 1st book shop has shopId = 1
            if (shopOptional.isPresent()) {
                ShopEntity shopEntity = shopOptional.get();
                Integer currentAmount = shopEntity.getAmount();
                Integer newAmount = currentAmount + (Integer.valueOf(amount));
                shopEntity.setAmount(newAmount);
                shopRepository.save(shopEntity);
            }
        }
    }


}
