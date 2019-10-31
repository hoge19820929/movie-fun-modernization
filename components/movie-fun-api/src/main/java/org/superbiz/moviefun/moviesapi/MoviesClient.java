package org.superbiz.moviefun.moviesapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class MoviesClient {
    private final String moviesUrl;
    private final RestOperations restOperations;
    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public void addMovie(MovieInfo movie) {
        restOperations.postForObject(moviesUrl, movie, MovieInfo.class);
    }

    public void deleteMovieId(Long id) {
        restOperations.delete(moviesUrl + "/" + id);
    }

    public int countAll() {
        return restOperations.getForObject(moviesUrl + "/count", Integer.class);
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, HttpMethod.GET, null, movieListType).getBody();
    }

    public int count(String field, String key) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", key);

        return restOperations.getForObject(urlBuilder.toUriString(), Integer.class);
    }

    public List<MovieInfo> findAll(int start, int pageSize) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(moviesUrl)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(urlBuilder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(urlBuilder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }
}
