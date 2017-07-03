package com.example.android.popular_movies_stage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickerHandler {
    final static private String SORT = "SORT";

    private ProgressBar mLoadingProgressBar;
    private TextView mErrorTextView;
    private RecyclerView mMovieListRecyclerView;
    private MovieAdapter mMovieAdapter;
    private String mSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mErrorTextView = (TextView) findViewById(R.id.tv_list_error);
        mMovieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);

        if (savedInstanceState != null) {
            mSort = savedInstanceState.getString(SORT);
        }

        if (mSort == null) {
            mSort = DatabaseMovie.POPULAR;
        }

        mMovieListRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this, this);

        mMovieListRecyclerView.setAdapter(mMovieAdapter);

        loadMovies();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT, mSort);
    }

    @Override
    public void ItemClicked(EntityMovie entityMovie) {
        Context context = this;
        Class destinationActivity = MovieDetailActivity.class;

        Intent startMovieDetailIntent = new Intent(context, destinationActivity);

        startMovieDetailIntent.putExtra(IntentItems.MOVIE_ENTITY, entityMovie);

        startActivity(startMovieDetailIntent);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<EntityMovie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<EntityMovie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String type = params[0];

            String json = DatabaseMovie.getMovies(type);
            List<EntityMovie> movieEntities = JsonParser.parseJson(json);

            return movieEntities;
        }

        @Override
        protected void onPostExecute(List<EntityMovie> movieEntities) {
            mMovieAdapter.setMovieData(movieEntities);
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();

        switch (id) {
            case R.id.action_top_rated:
                mSort = DatabaseMovie.TOP_RATED;
                loadMovies();
                return true;
            case R.id.action_popular:
                mSort = DatabaseMovie.POPULAR;
                loadMovies();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovies() {
        if(isOnline()) {
            showResults();
            new FetchMovieTask().execute(mSort);
        } else {
            showErrors();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showErrors() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mMovieListRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showResults() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMovieListRecyclerView.setVisibility(View.VISIBLE);
    }
}
