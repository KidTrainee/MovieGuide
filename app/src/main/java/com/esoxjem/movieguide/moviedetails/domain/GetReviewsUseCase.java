package com.esoxjem.movieguide.moviedetails.domain;

import com.esoxjem.movieguide.moviedetails.entities.Review;
import com.esoxjem.movieguide.common.network.TmdbWebService;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author arun
 */
public class GetReviewsUseCase {

    private final Pojo2DomainMapper mMapper;
    private TmdbWebService tmdbWebService;

    public GetReviewsUseCase(TmdbWebService tmdbWebService, Pojo2DomainMapper mapper) {
        this.tmdbWebService = tmdbWebService;
        mMapper = mapper;
    }

    public Observable<List<Review>> execute(final String id) {
        return tmdbWebService.reviews(id).map(mMapper::parsePOJO);
    }
}
