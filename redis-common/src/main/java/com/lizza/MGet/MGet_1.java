package com.lizza.MGet;

import com.lizza.JedisHolder;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * mget: 一次获取多个 key 的结果
 */
public class MGet_1 {

    private Jedis jedis = JedisHolder.jedis();

    @Test
    public void test1() {
        jedis.mset("a", "1", "b", "2", "c", "3");
        jedis.mget("a", "b", "c")
                .forEach(System.out::println);
    }
}
