package com.taotao.jedis;

import com.taotao.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import sun.security.krb5.internal.APOptions;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {
    //单机版测试
//    @Test
//    public void testJedisSingle()throws Exception{
//         Jedis jedis=new Jedis("203.195.230.38",6379);
//         jedis.set("test","hrllo world------------!");
//         String a=jedis.get("test");
//         System.out.println(a);
//         jedis.close();
//    }
    //单机版测试   使用连接池
    @Test
    public void testJedisPool()throws Exception{
        JedisPool jedisPool=new JedisPool("203.195.230.38",10010);
        Jedis jedis=jedisPool.getResource();
        jedis.auth("ck@xWGItEB4$bTv&");
        System.out.println(jedis.get("a"));
        jedis.close();
        jedisPool.close();
    }


    //jedis集群
    @Test
    public void testJedisCluster() throws Exception{
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("203.195.230.38",7001));
        nodes.add(new HostAndPort("203.195.230.38",7002));
        nodes.add(new HostAndPort("203.195.230.38",7003));
        nodes.add(new HostAndPort("203.195.230.38",7004));
        nodes.add(new HostAndPort("203.195.230.38",7005));
        nodes.add(new HostAndPort("203.195.230.38",7006));
        //自带连接池
        JedisCluster cluster=new JedisCluster(nodes);
        cluster.set("name","zhangsan");
        String name=cluster.get("name");
        System.out.println(name);

        cluster.close();
    }

    @Test
    public void testJedisClientSpring() throws Exception{
        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisClient jedisClient=ac.getBean(JedisClient.class);
        jedisClient.set("cluster","1000");
        System.out.println(jedisClient.get("cluster"));

    }
}
