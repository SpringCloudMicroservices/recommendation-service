package movies.recommendation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by tri.bui on 8/2/16.
 */
@Data
@NoArgsConstructor
@NodeEntity
public class Actor extends Person {
}
