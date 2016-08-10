package movies.recommendation.repository;

import movies.recommendation.model.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Set;

/**
 * Created by tri.bui on 8/2/16.
 */
public interface MovieRepository extends GraphRepository<Movie> {
    @Query("MATCH (m:Movie) WHERE m.genre = 'Animation' WITH m LIMIT 25 MATCH p=(m)<-[r:ACTS_IN]-(a:Person) RETURN m, rels(p)")
    Set<Movie> findMoviesForKids();

    @Query("MATCH (m:Movie) WITH m LIMIT 25 MATCH p=(m)<-[r:ACTS_IN]-(a:Person) RETURN m, rels(p)")
    Set<Movie> findMoviesForAdults();

    @Query("MATCH (m:Movie) WHERE m.genre = 'Family' WITH m MATCH p=(m)<-[r:ACTS_IN]-(a:Person) RETURN m, rels(p) LIMIT 25")
    Set<Movie> findMoviesForFamily();
}
