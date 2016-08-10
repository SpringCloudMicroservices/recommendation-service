package movies.recommendation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Created by tri.bui on 8/2/16.
 */
@Data
@NoArgsConstructor
@NodeEntity
public class Movie {
    @GraphId
    Long id;
    String title;
    String description;
    String tagline;
    String imageUrl;
    String genre;
    String trailer;
    String homepage;    

    @Relationship(type = "ACTS_IN", direction = INCOMING)
    Set<Person> actors;
}
