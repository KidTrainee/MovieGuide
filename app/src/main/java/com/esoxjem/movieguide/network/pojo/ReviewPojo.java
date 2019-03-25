package com.esoxjem.movieguide.network.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author arun
 */
public class ReviewPojo
{
    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
