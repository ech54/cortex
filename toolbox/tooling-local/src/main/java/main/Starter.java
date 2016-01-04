package main;

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Starter implements CommandLineRunner  {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    public static void main(String ...args) {
        SpringApplication.run(Starter.class);
    }

    private static final String NEO4J_HOME = "C:\\temp\\neo4j\\";
    private static final String NEO4J_PROPERTIES = "classpath:/neo4j.properties";

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("start tooling box");

        LOGGER.info("start neo4j in local:");

        final GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(NEO4J_HOME))

                .newGraphDatabase();

        registerShutdownHook(graphDb);
        Thread t = new Thread(new RunNeo4J(graphDb));
        try {
            t.start();
            while(true) {/*nothing*/}
        } finally {
            graphDb.shutdown();
        }
    }

    private class RunNeo4J implements Runnable {
        private GraphDatabaseService graphDb;

        public RunNeo4J(final GraphDatabaseService service) {
            graphDb = service;
        }

        @Override
        public void run() {
            //graphDb.createNode();
        }

    }


    private static void registerShutdownHook( final GraphDatabaseService graphDb ) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

}
