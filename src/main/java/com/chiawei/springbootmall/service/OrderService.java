package com.chiawei.springbootmall.service;

import com.chiawei.springbootmall.dto.CreateOrderRequest;
import com.chiawei.springbootmall.dto.OrderQueryParams;
import com.chiawei.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
