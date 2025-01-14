package com.chiawei.springbootmall.service;

import com.chiawei.springbootmall.dto.CreateOrderRequest;
import com.chiawei.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
