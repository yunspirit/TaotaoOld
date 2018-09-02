package com.taotao.sso.component;

public interface JedisClient {
    public String set(String key, String value);
    public String get(String key);

    public long hset(String key, String item, String value);
    public String hget(String key, String item);
    public long hdel(String key, String item);

    public long incr(String key);
    public long decr(String key);
    public long expire(String key, int second);
    public long ttl(String key);
}
