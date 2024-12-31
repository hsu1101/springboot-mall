package com.chiawei.springbootmall.controller;

import com.chiawei.springbootmall.constant.ProductCategory;
import com.chiawei.springbootmall.dto.ProductQueryParams;
import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;
import com.chiawei.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //@RequestParam，表示從url中取得到的請求參數
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, //表示根據什麼欄位排序
            @RequestParam(defaultValue = "desc") String sort,  //表示使用升序(小到大)或是降序(大到小)來排序

            //分頁 Pagination 控制分頁功能的參數
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, //此參數為取得幾筆商品數據
            @RequestParam(defaultValue = "0") @Min(0) Integer offset //表示跳過多少筆數據

    ){
        ProductQueryParams productQueryParams = new ProductQueryParams();//方便添加新的查詢條件
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(productList);//列表類型不論有沒有查到數據，都要固定回傳200給前端
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        //查詢商品product是否存在
        Product product = productService.getProductById(productId);
        if(product ==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //如果存在就去更新商品，並將更新過後的商品數據給前端
        productService.updateProduct(productId, productRequest);
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//前面不用先去查詢prodcu是否存在，此功能只要確定商品消失不見就可以

    }
}
