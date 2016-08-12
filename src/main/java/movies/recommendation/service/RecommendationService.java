package movies.recommendation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import movies.recommendation.model.Movie;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * Created by tri.bui on 8/12/16.
 */
public interface RecommendationService {
    @HystrixCommand(fallbackMethod = "recommendationFallback",
            ignoreExceptions = RuntimeException.class,
            commandProperties={
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    @ResponseBody
    Set<Movie> getRecommendations(String userId);
}
