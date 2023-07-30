package com.example.paymentapp.demopaymentapp.controller;

import com.example.paymentapp.demopaymentapp.model.InvoiceModel;
import com.example.paymentapp.demopaymentapp.model.OrderResponse;
import com.example.paymentapp.demopaymentapp.model.PaymentModel;
import com.example.paymentapp.demopaymentapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    //private OrderResponse orderResponse = new OrderResponse();
    private InvoiceModel invoiceModel;

    @KafkaListener(topics = "${spring.kafka.topic.name}"
            , groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(@Payload OrderResponse orderResponse) throws Exception {
        logger.info("Order response: " + orderResponse);

        paymentService.validateOrder(orderResponse);
    }


    @GetMapping("/get-invoice/{id}")
    public List<InvoiceModel> getInvoice(@PathVariable Long id){
        return paymentService.getInvoice(id);
    }

    @PostMapping("/payment")
    public String finalPayment(@RequestBody PaymentModel paymentModel){
        return paymentService.finalResult(paymentModel);
    }

}
