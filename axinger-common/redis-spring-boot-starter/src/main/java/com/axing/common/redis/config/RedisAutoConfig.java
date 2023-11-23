package com.axing.common.redis.config;

import com.axing.common.json.bean.JsonProperties;
import com.axing.common.json.config.ObjectMapperConfig;
import com.axing.common.json.model.CommonObjectMapper;
import com.axing.common.redis.bean.RedisProperties;
import com.axing.common.redis.service.RedisService;
import com.axing.common.redis.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

/**
 * @author xing
 * @version 1.0.0
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(ObjectMapperConfig.class)
public class RedisAutoConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisProperties redisProperties;
    private final JsonProperties jsonProperties;

    @Bean
    public RedisService redisService(RedisConnectionFactory factory) {
        return new RedisServiceImpl(this.redisTemplate(factory));
    }

    /**
     * 自定义key规则
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 设置RedisTemplate规则
     *
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 序列号key value
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(this.valueSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(this.valueSerializer2());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 设置CacheManager缓存规则
     *
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        // 配置序列化（解决乱码的问题）,
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(this.valueSerializer())
                )
                .disableCachingNullValues()
                // 创建默认缓存配置对象、 将@Cacheable缓存key值时默认会给value或cacheNames后加上双冒号 改为 单冒号
                .computePrefixWith(name -> name + ":");

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();


    }


    /**
     * new Jackson2JsonRedisSerializer
     * new GenericJackson2JsonRedisSerializer(objectMapper);
     *
     * @return RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> valueSerializer() {
//         ObjectMapper objectMapper = objectMapperConfig.getObjectMapper();
        ObjectMapper objectMapper = new CommonObjectMapper(jsonProperties);
//        // 将当前对象的数据类型也存入序列化的结果字符串中，以便反序列化
        if (redisProperties.isSavePackageName()) {
            objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        }
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        serializer.setObjectMapper(objectMapper);
//        return serializer;

//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        //一种方便的方法，允许更改底层 VisibilityCheckers 的配置，以更改自动检测的属性类型的详细信息。
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        return new GenericJackson2JsonRedisSerializer(objectMapper);


        //创建objectMapper，见下面的jsonson配置
//        ObjectMapper objectMapper = JsonSerializerConfig.createObjectMapper();
        //为什么这么写，是因为默认的new GenericJackson2JsonRedisSerializer()中存在此逻辑，为了保持一致，这里这么写，避免不必要的错误，不同版本的spring-data-redis可能会有所区别，自己查看下new GenericJackson2JsonRedisSerializer()内的逻辑处理一下即可。
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }


    @Bean
    public RedisSerializer<Object> valueSerializer2() {
        ObjectMapper objectMapper = new CommonObjectMapper(jsonProperties);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

}