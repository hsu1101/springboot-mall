package com.chiawei.springbootmall.service;

import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
}
