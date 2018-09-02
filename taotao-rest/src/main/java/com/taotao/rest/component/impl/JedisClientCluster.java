package com.taotao.rest.component.impl;

import com.taotao.rest.component.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {
    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public String set(String key, String value) {
        String ans=jedisCluster.set(key,value);
        return ans;
    }

    @Override
    public String get(String key) {
        String ans=jedisCluster.get(key);
        return ans;
    }

    @Override
    public long hset(String key, String item, String value) {
        long ans=jedisCluster.hset(key,item,value );
        return ans;
    }

    @Override
    public String hget(String key, String item) {

        String ans=jedisCluster.hget(key,item);
        return ans;
    }

    @Override
    public long hdel(String key, String item) {
        long ans=jedisCluster.hdel(key,item);
        return ans;
    }

    @Override
    public long incr(String key) {
        long ans=jedisCluster.incr(key);
        return ans;
    }

    @Override
    public long decr(String key) {
        long ans=jedisCluster.decr(key);
        return ans;
    }

    @Override
    public long expire(String key, int second) {
        long ans=jedisCluster.expire(key,second);
        return ans;
    }

    @Override
    public long ttl(String key) {
        long ans=jedisCluster.ttl(key);
        return ans;
    }


}
