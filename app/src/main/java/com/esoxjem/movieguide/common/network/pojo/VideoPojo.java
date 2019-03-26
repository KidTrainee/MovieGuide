package com.esoxjem.movieguide.common.network.pojo;

import com.squareup.moshi.Json;

/**
 * @author arun
 */
public class VideoPojo {


    private String id;
    private String name;
    private String site;
    @Json(name = "key")
    private String videoId;
    private int size;
    private String type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getVideoId() {
        return videoId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
