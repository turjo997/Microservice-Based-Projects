package com.HabibDev.BookShopApplication.model;


import lombok.*;;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PaymentRequest {

    private Integer bookId;
    private Integer amount;

}
