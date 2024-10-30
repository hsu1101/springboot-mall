package com.chiawei.springbootmall.dao;

import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
