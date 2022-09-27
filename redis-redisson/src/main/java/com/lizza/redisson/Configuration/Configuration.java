package com.lizza.redisson.Configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class Configuration {

    public void s() {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
              // use "rediss://" for SSL connection
              .addNodeAddress("perredis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);
    }
}
