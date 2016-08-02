package com.pycogroup.examples.recommendation;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pycogroup.examples.recommendation.exception.UserNotFoundException;
import com.pycogroup.examples.recommendation.model.Movie;
import com.pycogroup.examples.recommendation.model.User;
import com.pycogroup.examples.recommendation.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
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

    Set<Movie> kidRecommendations = Sets.newHashSet(new Movie("Lion King"), new Movie("Frozen"));
    Set<Movie> adultRecommendations = Sets.newHashSet(new Movie("Shawshank Redemption"), new Movie("Spring"));
    Set<Movie> familyRecommendations = Sets.newHashSet(new Movie("Hook"), new Movie("The Sandlot"));

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
        User member = membershipRepository.findUser(user);
        if (member == null) {
            throw new UserNotFoundException();
        }

        return member.getAge() < adultAge ? kidRecommendations : adultRecommendations;
    }

    Set<Movie> recommendationFallback(String user) {
        return familyRecommendations;
    }
}

