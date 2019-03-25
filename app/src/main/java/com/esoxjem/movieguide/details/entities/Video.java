package com.esoxjem.movieguide.details.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.esoxjem.movieguide.Api;
import com.esoxjem.movieguide.Constants;

/**
 * @author arun
 */
public class Video implements Parcelable {
    public static final String SITE_YOUTUBE = "YouTube";

    private String id;
    private String name;
    private String site;
    private String videoId;
    private int size;
    private String type;

    public Video(String id, String name, String site, String videoId, int size, String type) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.videoId = videoId;
        this.size = size;
        this.type = type;
    }

    public static String getUrl(Video video) {
        if (SITE_YOUTUBE.equalsIgnoreCase(video.getSite())) {
            return String.format(Api.YOUTUBE_VIDEO_URL, video.getVideoId());
        } else {
            return Constants.EMPTY;
        }
    }

    public static String getThumbnailUrl(Video video) {
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

    // region parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    protected Video(Parcel in) {
        id = in.readString();
        name = in.readString();
        site = in.readString();
        videoId = in.readString();
        size = in.readInt();
        type = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(videoId);
        parcel.writeInt(size);
        parcel.writeString(type);
    }
    // endregion
}
