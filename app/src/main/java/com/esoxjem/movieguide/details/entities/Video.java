package com.esoxjem.movieguide.details.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.esoxjem.movieguide.Api;
import com.esoxjem.movieguide.Constants;

/**
 * @author arun
 */
public class Video {
    public static final String SITE_YOUTUBE = "YouTube";

    private String site;
    private String videoId;

    public Video(String site, String videoId) {
        this.site = site;
        this.videoId = videoId;
    }

    public String getUrl(Video video) {
        if (SITE_YOUTUBE.equalsIgnoreCase(video.getSite())) {
            return String.format(Api.YOUTUBE_VIDEO_URL, video.getVideoId());
        } else {
            return Constants.EMPTY;
        }
    }

    public String getThumbnailUrl(Video video) {
        if (SITE_YOUTUBE.equalsIgnoreCase(video.getSite())) {
            return String.format(Api.YOUTUBE_THUMBNAIL_URL, video.getVideoId());
        } else {
            return Constants.EMPTY;
        }
    }

    public String getSite() {
        return site;
    }

    public String getVideoId() {
        return videoId;
    }
}
