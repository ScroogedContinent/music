package cn.org.continent.base.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description redis配置类 解决存入redis中乱码的情况
 * @date 2018/8/28 20:45
 */
@Configuration
public class RedisConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /*@Bean
    public RedisProperties redisProperties(){
        return new RedisProperties();
    }

    @Bean
    public RedisTemplate redisTemplate(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        stringRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        //若开启事务，则在注解了@Transactional的方法里，查询不到数据。
        stringRedisTemplate.setEnableTransactionSupport(false);

        return stringRedisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisProperties().getDatabase());
        redisStandaloneConfiguration.setHostName(redisProperties().getHost());
        redisStandaloneConfiguration.setPort(redisProperties().getPort());
        RedisPassword redisPassword = RedisPassword.of(redisProperties().getPassword().toCharArray());
        redisStandaloneConfiguration.setPassword(redisPassword);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.setPoolConfig(jedisPoolConfig());

        logger.info("-------------->>>>> Init Redis Config..........");
        return connectionFactory;
    }

    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties.Pool pool = redisProperties().getJedis().getPool();
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMinIdle(pool.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        return jedisPoolConfig;
    }*/

}
