package movies.recommendation.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by tri.bui on 8/2/16.
 */
@EnableTransactionManagement
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = {"movies.recommendation.service"})
@Configuration
@EnableNeo4jRepositories(basePackages = "movies.recommendation.repository")
public class MyNeo4jConfiguration extends Neo4jConfiguration {

//    @Value("${neo4j.url}")
    public static final String URL = System.getenv("NEO4J_URL") != null ?
            System.getenv("NEO4J_URL") : "http://neo4j:admin@localhost:7474";

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(URL);
        return config;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(),
                "movies.recommendation.model");
    }
}
