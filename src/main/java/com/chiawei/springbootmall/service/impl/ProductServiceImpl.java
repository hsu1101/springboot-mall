package com.chiawei.springbootmall.service.impl;

import com.chiawei.springbootmall.dao.ProductDao;
import com.chiawei.springbootmall.model.Product;
import com.chiawei.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
