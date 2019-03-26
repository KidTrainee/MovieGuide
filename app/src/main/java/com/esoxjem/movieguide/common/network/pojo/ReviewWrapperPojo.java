package com.esoxjem.movieguide.common.network.pojo;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by ivan on 8/20/2017.
 */

public class ReviewWrapperPojo {

    @Json(name = "results")
    private List<ReviewPojo> reviews;

    public List<ReviewPojo> getReviewPojos() {
        return reviews;
    }
}
