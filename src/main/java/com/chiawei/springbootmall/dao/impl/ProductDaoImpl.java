package com.chiawei.springbootmall.dao.impl;

import com.chiawei.springbootmall.constant.ProductCategory;
import com.chiawei.springbootmall.dao.ProductDao;
import com.chiawei.springbootmall.dto.ProductRequest;
import com.chiawei.springbootmall.model.Product;
import com.chiawei.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductCategory category,String search) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description," +
                "created_date, last_modified_date " +
                "FROM product WHERE 1=1"; //WHERE 1=1，對查詢沒有影響，主要是想讓下面的查詢條件可以自由地接在這個sql語法的後面
        Map<String, Object> map = new HashMap<>();

        if(category != null){
            sql = sql + " AND category = :category"; //AND前面要留空白建S
            map.put("category", category.name());//取出ENUM類型的name
        }

        if(search != null){
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%"+ search + "%"); //模糊查詢要寫在map這裡才會生效
        }

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql ="SELECT product_id, product_name, category, image_url, price, stock, description," +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(productList.size() >0){
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT INTO product(product_name, category, image_url, price," +
                " stock, description, created_date, last_modified_date)" +
                "VALUES(:productName, :category, :imageUrl, :price, :stock, :description," +
                ":createDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        //存儲資料庫自動生成的productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql ="UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("lastModifiedDate", now);

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }
}
