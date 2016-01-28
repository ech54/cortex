package lu.cortex.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.evt.model.Event;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class DomainSenderDefault implements DomainSender {

    // The default applicative logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainSender.class);

    @Autowired
    StringRedisTemplate template;

    @Override
    public void send(final Event event) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonInString = mapper.writeValueAsString(event);
            LOGGER.info("send event= {}", jsonInString);
            template.convertAndSend(event.getDestination().getSystemAlias(), jsonInString);
        } catch(final Exception e) {
          throw new RuntimeException(e);
        }
    }

    @Override
    public void broadcast(final Event event) {
        throw new NotImplementedException();
    }
}
