package movies.recommendation.repository;

import movies.recommendation.model.Member;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tri.bui on 8/2/16.
 */
@FeignClient("user-service")
public interface MembershipRepository {
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{user}")
    Member findUser(@PathVariable("user") String user);
}
