package service;

import pojo.User;

public interface UserService {
    //用户登录
    User login(User user);
    //用户注册
    Boolean register(User user);

    //检查用户名是否被注册
    int checkName(String username);
}
