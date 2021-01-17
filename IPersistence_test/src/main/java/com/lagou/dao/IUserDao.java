package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;

    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;

    //删除一条用户记录
    public void deleteByCondition(User user) throws Exception;

    //根据条件更新一条用户记录
    public void updateByCondition(User user) throws Exception;
}
