package com.chiawei.springbootmall.dao;

import com.chiawei.springbootmall.dto.UserRegisterRequest;
import com.chiawei.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    User getUserByEmail(String email);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
