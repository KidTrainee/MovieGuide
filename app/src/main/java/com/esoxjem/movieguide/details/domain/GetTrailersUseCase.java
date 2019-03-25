package com.esoxjem.movieguide.details.domain;

import com.esoxjem.movieguide.details.entities.Video;
import com.esoxjem.movieguide.network.TmdbWebService;

import java.util.List;

import io.reactivex.Observable;

public class GetTrailersUseCase {

    private final Pojo2DomainMapper mMapper;
    private TmdbWebService tmdbWebService;

    public GetTrailersUseCase(TmdbWebService tmdbWebService, Pojo2DomainMapper mapper) {
        this.tmdbWebService = tmdbWebService;
        mMapper = mapper;
    }

    public Observable<List<Video>> execute(final String id) {
        return tmdbWebService.trailers(id).map(mMapper::parsePOJO);
    }
}
