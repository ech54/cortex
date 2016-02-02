package lu.cortex;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import lu.cortex.async.DomainListenerDefault;
import lu.cortex.configuration.DomainDefinitionManagerDefault;

@Configuration
//@ComponentScan("lu.cortex")
public class DomainCommonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainCommonConfiguration.class);

    @Autowired
    private DomainDefinitionManagerDefault domainConfigurationExporter;

    @Bean
    //@Order(2)
    public JedisConnectionFactory jedisConnectionFactory() {
        LOGGER.info("start jedisConnectionFactory");
        final JedisConnectionFactory factory = new JedisConnectionFactory();
        //factory.setHostName("172.17.0.2");
        factory.setDatabase(0);
        factory.setHostName("localhost");
        return factory;
    }

    @Bean
    //@Order(2)
    MessageListenerAdapter listenerAdapter(DomainListenerDefault listener) {
        LOGGER.info("start listenerAdapter");
        return new MessageListenerAdapter(listener, "receiveMessage");
    }

    @Bean
    //@Order(2)
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        LOGGER.info("start container");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter,
                domainConfigurationExporter.getAllAlias()
                        .stream().map(a -> new PatternTopic(a))
                        .collect(Collectors.toList()));
        return container;
    }

    @Bean
    //@Order(2)
    public StringRedisTemplate redisTemplate() {
        LOGGER.info("start redisTemplate");
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
