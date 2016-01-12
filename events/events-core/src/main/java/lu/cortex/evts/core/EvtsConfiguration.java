package lu.cortex.evts.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//TODO configure a redis solution.
public class EvtsConfiguration {}
/*
@Configuration
@ComponentScan({"lu.cortex.evts.core"})
public class EvtsConfiguration {

    @Value("${redis.hostname}")
    String hostName;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        final JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostName);
        return factory;
    }

    @Bean
    RedisTemplate< String, Object > redisTemplate() {
        final RedisTemplate< String, Object > templates =  new RedisTemplate< String, Object >();
        templates.setConnectionFactory(redisConnectionFactory());
        templates.setKeySerializer(new StringRedisSerializer());
        templates.setHashValueSerializer( new GenericToStringSerializer<Object>( Object.class ) );
        templates.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        return templates;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue"); // TODO to change.
    }
} */
