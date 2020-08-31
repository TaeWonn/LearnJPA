package com.tae.common.redis.service;

import com.tae.common.redis.MyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void test(){
        //hashMap 같은 key value 구조
        ValueOperations<String,Object> vop = redisTemplate.opsForValue();
        MyData data = new MyData();
        data.setId("123");
        data.setName("tae");
        vop.set("key",data);

        MyData data2 = (MyData)vop.get("key");
        System.out.println(data2);

    }
}
