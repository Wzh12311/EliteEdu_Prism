package com.example.eliteedu_prism;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void test(){
        //连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //操作

//        jedis.set("name", "zhangsan");
//        jedis.lpush("list1","a","b","c");
//        jedis.rpush("list1","q","e","x");
        System.out.println(jedis.get("age"));

        System.out.println(jedis.get("name"));


        //关闭
        jedis.close();


    }


}
