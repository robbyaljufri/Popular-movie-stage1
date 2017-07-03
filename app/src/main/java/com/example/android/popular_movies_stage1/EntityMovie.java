package com.example.android.popular_movies_stage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 6/28/2017.
 */

public class EntityMovie implements Parcelable {

    private Integer id;
    private String originalTitle;
    private String moviePoster;
    private String synopsis;
    private Double userRating;
    private String releaseDate;

    public EntityMovie(
            Integer id,
            String originalTitle,
            String moviePoster,
            String synopsis,
            Double userRating,
            String releaseDate
    ) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    private EntityMovie(Parcel in) {
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.moviePoster = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readDouble();
        this.releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(moviePoster);
        dest.writeString(synopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    public static final Creator<EntityMovie> CREATOR = new Creator<EntityMovie>() {
        @Override
        public EntityMovie createFromParcel(Parcel source) {
            return new EntityMovie(source);
        }

        @Override
        public EntityMovie[] newArray(int size) {
            return new EntityMovie[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
