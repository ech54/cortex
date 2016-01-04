package lu.wf.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.InProcessServer;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"lu.wf.admin"})
@EnableTransactionManagement
@EnableNeo4jRepositories("lu.wf.admin.repository")
public class AdminCoreConfiguration extends Neo4jConfiguration {

    @Bean
    public ObjectMapper jsonMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Override
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

    @Override
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("lu.wf.admin.model");
    }

    @Override
    @Bean
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://u:p@localhost:7474", "neo4j", "password");
    }

}