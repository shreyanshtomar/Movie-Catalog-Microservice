package com.example.ratingsinfoservice.resources;

import com.example.ratingsinfoservice.models.Rating;
import com.example.ratingsinfoservice.models.UserRating;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    private UserRating userRating;

    public RatingsResource(UserRating userRating){
        this.userRating = userRating;
    }


    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping(path = "users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("2345", 3)
        );
        userRating.setUserRating(ratings);
        return userRating;
    }
}
