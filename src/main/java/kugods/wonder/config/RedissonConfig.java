package kugods.wonder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    private static final String REDISSON_HOST_FORMAT = "redis://%s:%d";

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisAddress = String.format(REDISSON_HOST_FORMAT, redisHost, redisPort);
        config.useSingleServer().setAddress(redisAddress);

        return Redisson.create(config);
    }
}
