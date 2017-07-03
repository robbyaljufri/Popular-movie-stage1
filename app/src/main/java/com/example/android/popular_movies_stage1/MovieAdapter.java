package com.example.android.popular_movies_stage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 6/28/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{
    public List<EntityMovie> mMovieData;
    public Context mContext;

    final private MovieAdapterOnClickerHandler mClickHandler;

    interface MovieAdapterOnClickerHandler {
        void ItemClicked(EntityMovie id);
    }

    public MovieAdapter(MovieAdapterOnClickerHandler onClickerHandler, Context context) {
        mMovieData = new ArrayList<EntityMovie>();
        mClickHandler = onClickerHandler;
        mContext = context;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mListMoviePosterImageView;
        public final View mView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mView = view;
            mListMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_list_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            EntityMovie entityMovie = mMovieData.get(position);
            mClickHandler.ItemClicked(entityMovie);
        }
}

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        Integer layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        EntityMovie entityMovie = mMovieData.get(position);
        Picasso.with(holder.mView.getContext())
                .load(entityMovie.getMoviePoster())
                .into(holder.mListMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }


    public void setMovieData(List<EntityMovie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
