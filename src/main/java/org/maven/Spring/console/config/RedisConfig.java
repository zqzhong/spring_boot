package org.maven.Spring.console.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置value的序列化方式（使用JSON序列化）
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 如果需要自定义连接工厂
     */
//     @Bean
//     public RedisConnectionFactory redisConnectionFactory() {
//         RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
//         clusterConfig.setClusterNodes(Arrays.asList(
//             new RedisNode("192.168.1.101", 6379),
//             new RedisNode("192.168.1.102", 6379),
//             new RedisNode("192.168.1.103", 6379),
//             new RedisNode("192.168.1.104", 6379),
//             new RedisNode("192.168.1.105", 6379),
//             new RedisNode("192.168.1.106", 6379)
//         ));
//         clusterConfig.setMaxRedirects(3);
//
//         LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//             .commandTimeout(Duration.ofSeconds(2))
//             .build();
//
//         return new LettuceConnectionFactory(clusterConfig, clientConfig);
//     }
}

