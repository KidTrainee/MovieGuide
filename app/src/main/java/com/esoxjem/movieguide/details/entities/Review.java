package com.esoxjem.movieguide.details.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author arun
 */
public class Review implements Parcelable
{
    private String id;
    private String author;
    private String content;
    private String url;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getContent()
    {
        return content;
    }

    // region parcelable
    protected Review(Parcel in)
    {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>()
    {
        @Override
        public Review createFromParcel(Parcel in)
        {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size)
        {
            return new Review[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
    // endregion
}
