package movies.recommendation.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import movies.recommendation.model.Movie;
import movies.recommendation.model.User;
import movies.recommendation.repository.MovieRepository;
import movies.recommendation.service.RecommendationService;
import movies.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by tri.bui on 8/12/16.
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Inject
    UserService userService;

    @Inject
    MovieRepository movieRepository;

    @Value("${adult.age}")
    int adultAge;

    @Override
    @HystrixCommand(fallbackMethod = "recommendationFallback",
            ignoreExceptions = RuntimeException.class,
            commandProperties={
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public Set<Movie> getRecommendations(String userId) {
        User user = userService.findUser(userId);
        return user.getAge() < adultAge ? movieRepository.findMoviesForKids() : movieRepository.findMoviesForAdults();
    }

    Set<Movie> recommendationFallback(String user) {
        return movieRepository.findMoviesForFamily();
    }
}
