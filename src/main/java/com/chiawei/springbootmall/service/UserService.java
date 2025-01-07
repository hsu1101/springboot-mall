package com.chiawei.springbootmall.service;

import com.chiawei.springbootmall.dto.UserRegisterRequest;
import com.chiawei.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
}
