package com.example.paymentapp.demopaymentapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentModel {
    private Long user_id;
    private Integer totalPrice;
    private Long invoice_id;
}
