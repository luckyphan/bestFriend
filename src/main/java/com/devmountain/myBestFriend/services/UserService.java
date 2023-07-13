package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.UserDto;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    //Register User
    @Transactional
    List<String> addUser(UserDto userDto);

    //Login User
    List<String> userLogin(UserDto userDto);
}
