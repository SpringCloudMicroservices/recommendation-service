package movies.recommendation.controller;

import movies.recommendation.model.Movie;
import movies.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by tri.bui on 7/20/16.
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController {
    @Inject
    RecommendationService recommendationService;

    @RequestMapping("/{user}")
    public @ResponseBody Set<Movie> getRecommendations(@PathVariable(value="user")String userId) {
        return recommendationService.getRecommendations(userId);
    }
}

