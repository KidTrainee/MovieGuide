package com.esoxjem.movieguide.moviedetails.domain;

import com.esoxjem.movieguide.common.network.pojo.ReviewPojo;
import com.esoxjem.movieguide.common.network.pojo.ReviewWrapperPojo;
import com.esoxjem.movieguide.common.network.pojo.VideoPojo;
import com.esoxjem.movieguide.common.network.pojo.VideoWrapperPojo;
import com.esoxjem.movieguide.moviedetails.entities.Review;
import com.esoxjem.movieguide.moviedetails.entities.Video;

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
