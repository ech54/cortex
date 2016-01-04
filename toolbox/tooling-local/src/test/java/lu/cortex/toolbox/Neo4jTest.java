package lu.cortex.toolbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Neo4jTest.testingConf.class)
@Transactional
public class Neo4jTest {

        @Autowired
        Session session;

        @Configuration
        @ComponentScan({"lu.engine.core"})
        @EnableTransactionManagement
        @EnableNeo4jRepositories
        static class testingConf extends Neo4jConfiguration {


        @Override
        public Neo4jServer neo4jServer() {

                return new RemoteServer("http://u:p@localhost:7474");
//                return new RemoteServer("http://u:p@localhost:7474", "neo4j", "node1");
        }

        @Override
        public SessionFactory getSessionFactory() {
            return new SessionFactory("lu.engine.core.model");
        }

        @Override
        public Session getSession() throws Exception {
            return super.getSession();
        }}


        @NodeEntity
        public class Personne {

                @GraphId
                private Long id;
                @Property
                private String name;
                @Property
                private String login;

                public Long getId() {
                        return id;
                }

                public void setId(Long id) {
                        this.id = id;
                }

                public String getName() {
                        return name;
                }

                public void setName(String name) {
                        this.name = name;
                }

                public String getLogin() {
                        return login;
                }

                public void setLogin(String login) {
                        this.login = login;
                }
        }

        @Test
        public void checkConnection() {
                final Personne p = new Personne();
                p.setLogin("StartLord");
                p.setName("Peter Parker");
                session.save(p);
        }
        @Test
        public void retrievePersonne() {
             session.loadAll(Personne.class).forEach(
                     p -> System.out.println("id=" + p.getId() + ", login=" + p.getLogin() + ", name=" + p.getName()));
        }
}
