package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementid,Object... params) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementid,Object... params) throws Exception;

    //删除一条用户记录
    public void deleteOne(String statementid, Object... params) throws Exception;

    //根据条件更新一条用户记录
    public void updateOne(String statementid, Object... parems) throws Exception;

    //添加一条用户记录
    public void insertOne(String statementid, Object... params) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);


}
