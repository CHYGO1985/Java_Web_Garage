package com.lagou.rpc.provider.service;

import com.lagou.rpc.api.UserService;
import com.lagou.rpc.pojo.User;
import com.lagou.rpc.annotation.RpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * User service implementation.
 *
 * @author jingjiejiang
 * @history Aug 19, 2021
 *
 */
@RpcService
@Service
public class UserServiceImpl implements UserService {
    Map<Object, User> userMap = new HashMap();

    @Override
    public User getById(int id) {
        if (userMap.size() == 0) {
            initUserMap();
        }
        return userMap.get(id);
    }

    @Override
    public String getByIdReturnStr(int id) {
        if (userMap.size() == 0) {
            initUserMap();
        }

        return userMap.get(id) != null ? "success" : "fail";
    }

    /**
     *
     * This is for mocking data.
     * 
     */
    private void initUserMap() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("Zhang San");
        User user2 = new User();
        user2.setId(2);
        user2.setName("Li Si");
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }
}