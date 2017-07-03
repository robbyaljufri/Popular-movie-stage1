package com.example.android.popular_movies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    private TextView mMovieTitleTextView;
    private TextView mMovieRatingTextView;
    private TextView mMovieReleaseDateTextView;
    private TextView mMovieSynopsisTextView;
    private ImageView mMoviePosterImageView;
    private TextView mDetailErrorTextView;
    private ScrollView mDetailScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mMovieRatingTextView = (TextView) findViewById(R.id.tv_movie_rating);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_releaseDate);
        mMovieSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis);
        mMoviePosterImageView = (ImageView) findViewById(R.id.iv_movie_poster);
        mDetailErrorTextView = (TextView) findViewById(R.id.tv_detail_error);
        mDetailScrollView = (ScrollView) findViewById(R.id.sv_movie_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity.hasExtra(IntentItems.MOVIE_ENTITY)) {
            EntityMovie entityMovie = intentThatStartedThisActivity.getParcelableExtra(IntentItems.MOVIE_ENTITY);

            if(entityMovie != null) {
                showResults();
                mMovieTitleTextView.setText(entityMovie.getOriginalTitle());
                mMovieRatingTextView.setText(getString(R.string.average_rating) + entityMovie.getUserRating().toString());
                mMovieReleaseDateTextView.setText(getString(R.string.release_date) + entityMovie.getReleaseDate());
                mMovieSynopsisTextView.setText(entityMovie.getSynopsis());
                Picasso.with(this).load(entityMovie.getMoviePoster()).into(mMoviePosterImageView);
            } else {
                showErrors();
            }

        } else {
            showErrors();
        }
    }

    private void showErrors() {
        mDetailErrorTextView.setVisibility(View.VISIBLE);
        mDetailScrollView.setVisibility(View.INVISIBLE);
    }

    private void showResults() {
        mDetailErrorTextView.setVisibility(View.INVISIBLE);
        mDetailScrollView.setVisibility(View.VISIBLE);
    }

}
