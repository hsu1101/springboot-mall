package com.chiawei.springbootmall.dao;

import com.chiawei.springbootmall.dto.ProductQueryParams;
import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;
import java.util.List;

public interface ProductDao {

    List<Product>getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);
}
