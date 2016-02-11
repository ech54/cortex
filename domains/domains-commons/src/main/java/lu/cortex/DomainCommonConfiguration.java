package lu.cortex;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import lu.cortex.async.AsynchronousDomainListener;
import lu.cortex.configuration.DomainDefinitionManagerDefault;
import lu.cortex.sync.SynchronousDomainListener;

@Configuration
public class DomainCommonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainCommonConfiguration.class);

    @Autowired
    private DomainDefinitionManagerDefault domainConfigurationExporter;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        LOGGER.info("start jedisConnectionFactory");
        final JedisConnectionFactory factory = new JedisConnectionFactory();
        //factory.setHostName("172.17.0.2");
        factory.setDatabase(0);
        factory.setHostName("localhost");
        return factory;
    }

    @Bean
    MessageListenerAdapter listenerForAsynchronousProcess(AsynchronousDomainListener listener) {
        return new MessageListenerAdapter(listener, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter listenerForSynchronousProcess(SynchronousDomainListener listener) {
        return new MessageListenerAdapter(listener, "receiveMessage");
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            @Qualifier("listenerForAsynchronousProcess") MessageListenerAdapter asynchronous,
            @Qualifier("listenerForSynchronousProcess") MessageListenerAdapter synchronous) {
        LOGGER.info("start container");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        final StringBuilder buffer = new StringBuilder();
        domainConfigurationExporter.getQueueNames().stream().forEach(a -> buffer.append("alias:" + a + " "));
        LOGGER.info("alias from exporter: " + buffer.toString());
        container.addMessageListener(asynchronous,
                domainConfigurationExporter.getAsyncQueue()
                        .stream().map(a -> new PatternTopic(a))
                        .collect(Collectors.toList()));
        container.addMessageListener(synchronous,
                domainConfigurationExporter.getSyncQueue()
                        .stream().map(a -> new PatternTopic(a))
                        .collect(Collectors.toList()));
        return container;
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        LOGGER.info("start redisTemplate");
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
