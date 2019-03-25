package com.esoxjem.movieguide.details.domain;

import com.esoxjem.movieguide.network.pojo.ReviewPojo;
import com.esoxjem.movieguide.network.pojo.ReviewWrapperPojo;
import com.esoxjem.movieguide.network.pojo.VideoPojo;
import com.esoxjem.movieguide.network.pojo.VideoWrapperPojo;
import com.esoxjem.movieguide.details.entities.Review;
import com.esoxjem.movieguide.details.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class Pojo2DomainMapper {

    public List<Video> parsePOJO(VideoWrapperPojo pojoWrapper) {
        List<Video> domains = new ArrayList<>();
        for (VideoPojo pojo : pojoWrapper.getVideoPojos()) {
            domains.add(new Video(pojo.getSite(), pojo.getVideoId()));
        }
        return domains;
    }

    public List<Review> parsePOJO(ReviewWrapperPojo pojoWrapper) {
        List<Review> domains = new ArrayList<>();
        for (ReviewPojo pojo : pojoWrapper.getReviewPojos()) {
            domains.add(new Review(pojo.getAuthor(), pojo.getContent()));
        }
        return domains;

    }
}
