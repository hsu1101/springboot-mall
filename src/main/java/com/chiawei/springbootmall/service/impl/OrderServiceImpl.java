package com.chiawei.springbootmall.service.impl;

import com.chiawei.springbootmall.dao.OrderDao;
import com.chiawei.springbootmall.dao.ProductDao;
import com.chiawei.springbootmall.dto.BuyItem;
import com.chiawei.springbootmall.dto.CreateOrderRequest;
import com.chiawei.springbootmall.model.OrderItem;
import com.chiawei.springbootmall.model.Product;
import com.chiawei.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmout = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmout = totalAmout + amount;

            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmout);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
