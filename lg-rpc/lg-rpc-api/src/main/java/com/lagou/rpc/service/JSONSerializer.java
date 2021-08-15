package com.lagou.rpc.service;

import com.alibaba.fastjson.JSON;
import com.lagou.rpc.api.Serializer;

import java.io.IOException;

/**
 *
 * The JSON implementation of Inteface Serializer.
 *
 * @author jingjiejiang
 * @history Aug 15, 2021
 *
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> targetClass, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, targetClass);
    }
}
