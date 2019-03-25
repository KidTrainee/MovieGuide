package com.esoxjem.movieguide.network.pojo;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by ivan on 8/20/2017.
 */

public class VideoWrapperPojo {

    @Json(name = "results")
    private List<VideoPojo> videos;

    public List<VideoPojo> getVideoPojos() {
        return videos;
    }

}
