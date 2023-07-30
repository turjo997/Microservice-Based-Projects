package com.sajal.order_service.service;

//import com.sajal.order_service.config.InventoryClientConfig;
import com.sajal.order_service.config.InventoryClientConfig;
import com.sajal.order_service.config.PaymentClientConfig;
import com.sajal.order_service.config.UserClientConfig;
import com.sajal.order_service.dto.ConfirmOrderDto;
import com.sajal.order_service.entity.OrderEntity;
import com.sajal.order_service.entity.OrderStatus;
import com.sajal.order_service.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final InventoryClientConfig inventoryClientConfig;
    private final  UserClientConfig userClientConfig;
    private final OrderEntityRepository orderEntityRepository;
    private final PaymentClientConfig paymentClientConfig;


    public Object placeOrder(OrderEntity order) {
        try {
            orderPlaceProcess(order.getUserId(), order.getBookId(), order.getQuantity());
            Double totalPrice = calculatePrice(order.getBookId(),order.getQuantity());
            order.setTotalAmount(totalPrice);
            order.setStatus(OrderStatus.AWAITING_PAYMENT);
            return orderEntityRepository.save(order);
        }catch (Exception e){
            return e.getMessage();
        }

    }
    public Object confirmOrder(ConfirmOrderDto confirmOrder){
        try {
            var order = confirmOrderProcess(confirmOrder.getOrderId(),confirmOrder.getCreditCard());
            return orderEntityRepository.save(order);
        }catch (Exception e){
            return e.getMessage();
        }
    }
    private OrderEntity confirmOrderProcess(Long orderId , Double cardBalance ){
        var orderInfo =orderEntityRepository.findById(orderId);

        //step 1
        if(orderInfo.isEmpty()){
            throw new RuntimeException("invalid order id");
        }
        //step 2 (payment status)
        if(!paymentClientConfig.payment(orderInfo.get().getTotalAmount(),cardBalance)){
            var itemInInventory =inventoryClientConfig.getBookCount(orderInfo.get().getBookId());
            //rollback steps
            inventoryClientConfig.updateCountByBookId(orderInfo.get().getBookId(), itemInInventory+orderInfo.get().getQuantity());

            orderInfo.get().setStatus(OrderStatus.FAILED);
            orderEntityRepository.save(orderInfo.get());
            throw new RuntimeException("order cancelled insufficient  balance");
        }
        // step 3 (adding books to user)
        if(!userClientConfig.addBookToUser(orderInfo.get().getUserId(), orderInfo.get().getBookId(), orderInfo.get().getQuantity())){
            var itemInInventory =inventoryClientConfig.getBookCount(orderInfo.get().getBookId());
            //rollback steps
            inventoryClientConfig.updateCountByBookId(orderInfo.get().getBookId(), itemInInventory+orderInfo.get().getQuantity());

            orderInfo.get().setStatus(OrderStatus.FAILED);
            orderEntityRepository.save(orderInfo.get());
            throw new RuntimeException("shipment failed");
        }
        //updating order status
        orderInfo.get().setStatus(OrderStatus.COMPLETED);
        return orderInfo.get();
    }
    private void orderPlaceProcess(Long userId, Long bookId, Integer bookCount){

        //step 1
        if(!userClientConfig.userExist(userId)){
            throw new RuntimeException("user doesn't exist");
        }
        //step 2
        Integer totalBookCount = inventoryClientConfig.getBookCount(bookId);
        if (totalBookCount<bookCount){
            throw new RuntimeException("Out of range/stock");
        };
        //step 3
        if(!inventoryClientConfig.updateCountByBookId(bookId,totalBookCount-bookCount)){
            throw new RuntimeException("Problem  in inventory");
        }

    }

    private Double calculatePrice(long bookId, int quantity){
       return (double) (inventoryClientConfig.getBookPrice(bookId) * quantity);
    }




}
