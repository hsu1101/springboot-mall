package com.chiawei.springbootmall.service;

import com.chiawei.springbootmall.constant.ProductCategory;
import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category,String search);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
