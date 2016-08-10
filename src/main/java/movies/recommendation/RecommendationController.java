package movies.recommendation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import movies.recommendation.exception.UserNotFoundException;
import movies.recommendation.model.Member;
import movies.recommendation.model.Movie;
import movies.recommendation.repository.MembershipRepository;
import movies.recommendation.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by tri.bui on 7/20/16.
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    @Inject
    MembershipRepository membershipRepository;

    @Inject
    MovieRepository movieRepository;

    @Value("${adult.age}")
    int adultAge;

    @RequestMapping("/{user}")
    @HystrixCommand(fallbackMethod = "recommendationFallback",
            ignoreExceptions = UserNotFoundException.class,
            commandProperties={
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public @ResponseBody Set<Movie> getRecommendations(@PathVariable(value="user")String user)
            throws UserNotFoundException {
        RequestContextHolder.currentRequestAttributes();
        Member member = membershipRepository.findUser(user);
        if (member == null) {
            throw new UserNotFoundException();
        }

        return member.getAge() < adultAge ? movieRepository.findMoviesForKids() : movieRepository.findMoviesForAdults();
    }

    Set<Movie> recommendationFallback(String user) {
        return movieRepository.findMoviesForFamily();
    }
}

