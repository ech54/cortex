package lu.engine.core;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Configuration
@ComponentScan({"lu.engine.core"})
@EnableTransactionManagement
@EnableNeo4jRepositories("lu.engine.core.repository")
public class CoreConfiguration extends Neo4jConfiguration {

    @Bean
    public ObjectMapper jsonMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Override
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://u:p@localhost:7474", "neo4j", "password");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("lu.engine.core.model");
    }

    @Override
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
