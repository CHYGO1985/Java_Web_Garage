package com.lagou.rpc.api;

import com.lagou.rpc.pojo.User;

/**
 * 用户服务
 */
public interface IUserService {

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    User getById(int id);

    /**
     *
     * Search user by ID and return status as string
     *
     * @param id
     * @return
     */
    String getByIdReturnStr(int id);
}
