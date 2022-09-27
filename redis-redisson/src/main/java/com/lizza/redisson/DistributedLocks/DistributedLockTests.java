package com.lizza.redisson.DistributedLocks;

import cn.hutool.core.util.StrUtil;
import com.lizza.redisson.util.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DistributedLockTests {

    private RedissonClient redisson;

    private ExecutorService executors = Executors.newFixedThreadPool(10);

    {
        Config config = new Config();
        // 单机模式
        config.useSingleServer()
              .setAddress("redis://127.0.0.1:6379")
              .setPassword("root");
        redisson = Redisson.create(config);
    }

    /**
     * 1. 锁超时时间: 1s, 业务时间: 500ms, 结果: 正常加锁/解锁
     * 2. 并发场景, 加锁/解锁流程正常
     */
    @Test
    public void test1() throws Exception {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                RLock lock = redisson.getLock("lock-1");
                lock.lock(1, TimeUnit.SECONDS);
                Log.log("{} lock success", Thread.currentThread().getName());
                try { Thread.sleep(500); } catch (Exception e) { e.printStackTrace(); }
                lock.unlock();
                Log.log("{} unlock success", Thread.currentThread().getName());
            });
        }
        Thread.currentThread().join();
    }

    /**
     * 1. 锁超时时间: 1s, 业务时间: 1500ms, 结果: 正常加锁; 锁在 1500ms 后自动释放;
     * 2. 解锁异常: IllegalMonitorStateException
     * 3. 并发场景下, 锁超时后, 自动解锁, 其他线程/进程可以继续获取锁
     */
    @Test
    public void test2() throws Exception {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                RLock lock = redisson.getLock("lock-1");
                lock.lock(1, TimeUnit.SECONDS);
                Log.log("{} lock success", Thread.currentThread().getName());
                try { Thread.sleep(1500); } catch (Exception e) { e.printStackTrace(); }
                lock.unlock();
                Log.log("{} unlock success", Thread.currentThread().getName());
            });
        }
        Thread.currentThread().join();
    }

    @Test
    public void test3() throws Exception {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                RLock lock = redisson.getLock("lock-1");
                lock.lock();
                Log.log("{} lock success", Thread.currentThread().getName());
                try { Thread.sleep(10000); } catch (Exception e) { e.printStackTrace(); }
                lock.unlock();
                Log.log("{} unlock success", Thread.currentThread().getName());
            });
        }
        Thread.currentThread().join();
    }


}
