package org.maven.Spring.console.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 配置单机模式，如果你的 Redis 在本地且无密码，这样即可
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 如果有密码：.setPassword("你的密码");
        return Redisson.create(config);
    }
}
