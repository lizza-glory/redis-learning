package com.lizza.Pipeline;

import com.lizza.JedisHolder;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2022-02-15
 */
public class PipelineTests {

    @Test
    public void test1() {
        Jedis jedis = JedisHolder.jedis();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 10; i++) {
            if (i == 7) {
                pipeline.lpush("user-1", "7");
                continue;
            }
            pipeline.set("user-" + i, "name-" + i);
        }
        pipeline.sync();
    }
}
