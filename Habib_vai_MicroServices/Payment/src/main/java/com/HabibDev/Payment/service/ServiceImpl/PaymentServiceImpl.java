package com.HabibDev.Payment.service.ServiceImpl;

import com.HabibDev.Payment.entity.PaymentEntity;
import com.HabibDev.Payment.model.PaymentRequest;
import com.HabibDev.Payment.model.PaymentResponse;
import com.HabibDev.Payment.repository.PaymentRepository;
import com.HabibDev.Payment.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        Integer customerId = paymentRequest.getCustomerId();
        PaymentEntity paymentEntity = paymentRepository.findByCustomerId(customerId);
        if (paymentEntity == null) {
            // Handle the case when the customer is not found
            return PaymentResponse.builder()
                    .message("Customer not found")
                    .build();
        }

        Integer accountBalance = paymentEntity.getAmount();

        // Check if the customer has enough funds to make the payment
        if (accountBalance >= paymentRequest.getAmount()) {
            // Process the payment and deduct the amount from the account balance
            Integer updatedBalance = accountBalance - paymentRequest.getAmount();
            paymentEntity.setAmount(updatedBalance);
            paymentRepository.save(paymentEntity);




            // Return a successful payment response
            return PaymentResponse.builder()
                    .message("Payment successful")
                    .build();
        } else {
            // Return an unsuccessful payment response
            return PaymentResponse.builder()
                    .message("Insufficient funds")
                    .build();
        }
    }
}
