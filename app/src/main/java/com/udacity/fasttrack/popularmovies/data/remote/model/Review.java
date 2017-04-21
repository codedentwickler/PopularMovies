package com.udacity.fasttrack.popularmovies.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class Review implements Parcelable{

    @SerializedName("id")
    private String reviewId;

    @SerializedName("author")
    private String author;

    @SerializedName("url")
    private String reviewUrl;

    @SerializedName("content")
    private String content;

    public Review(String reviewId) {
        this.reviewId = reviewId;
    }

    protected Review(Parcel in) {
        reviewId = in.readString();
        author = in.readString();
        reviewUrl = in.readString();
        content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getReviewId() {
        return reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewId);
        dest.writeString(author);
        dest.writeString(reviewUrl);
        dest.writeString(content);
    }
}
