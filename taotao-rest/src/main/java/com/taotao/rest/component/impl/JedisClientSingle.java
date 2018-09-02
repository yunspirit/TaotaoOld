package com.taotao.rest.component.impl;

import com.taotao.rest.component.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {
    @Autowired
    private JedisPool jedisPool;
    @Override
    public String set(String key, String value) {
        Jedis jedis=jedisPool.getResource();
        jedis.auth("ck@xWGItEB4$bTv&");
        String ans=jedis.set(key,value);
        jedis.close();
        return ans;
    }

    @Override
    public String get(String key) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        String ans=jedis.get(key);
        jedis.close();
        return ans;
    }

    @Override
    public long hset(String key, String item, String value) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.hset(key,item,value);
        jedis.close();
        return ans;
    }

    @Override
    public String  hget(String key, String item) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        String ans=jedis.hget(key,item);
        jedis.close();
        return ans;
    }

    @Override
    public long hdel(String key, String item) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.hdel(key, item);
        return ans;
    }

    @Override
    public long incr(String key) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.incr(key);
        jedis.close();
        return ans;
    }

    @Override
    public long decr(String key) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.decr(key);
        jedis.close();
        return ans;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.expire(key,second);
        jedis.close();
        return ans;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis=jedisPool.getResource();jedis.auth("ck@xWGItEB4$bTv&");
        long ans=jedis.ttl(key);
        jedis.close();
        return ans;
    }


}
