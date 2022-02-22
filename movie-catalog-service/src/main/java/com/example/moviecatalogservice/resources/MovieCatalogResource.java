package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private RestTemplate restTemplate;

    public MovieCatalogResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Calling the ratings service to get the user ratings.
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
        // Using the ratings service to get the ratings for a user.
        // Then it is using the movie info service to get the movie details for each movie id.
        // Finally, it is putting all the movie details together into a CatalogItem.
        return ratings.getUserRating().stream().map(
                rating -> {
                    //For each movie id call movie info service & get movie details
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

                    //Put 'em all together
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                }
        ).collect(Collectors.toList());

    }
}
