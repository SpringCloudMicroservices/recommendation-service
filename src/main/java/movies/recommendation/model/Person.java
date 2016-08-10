package movies.recommendation.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by tri.bui on 8/2/16.
 */
@Data
@NodeEntity
public class Person {
    @GraphId
    Long id;

    String name;
}
