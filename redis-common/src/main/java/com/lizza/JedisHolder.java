package com.lizza;

import redis.clients.jedis.Jedis;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2022-01-24
 */
public class JedisHolder {

    public static Jedis jedis() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("root");
        return jedis;
    }
}
