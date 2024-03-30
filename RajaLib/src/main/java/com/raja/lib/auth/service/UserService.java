package com.raja.lib.auth.service;

import java.util.List;

import com.raja.lib.auth.dto.UserDto;
import com.raja.lib.auth.entity.User;

public interface UserService {

	void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}