package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.IMAGE_POSTER_BASE_URL;

/**
 * Created by codedentwickler on 4/12/17.
 */

class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final OnItemClickListener mItemClickListener;
    private final Context mContext;
    private List<Movie> movies;

    FavouriteAdapter(List<Movie> movies, OnItemClickListener itemClickListener, Context context) {
        setMovies(movies);
        this.mItemClickListener = itemClickListener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            if (movies.size() != 0) {
                Movie movie = movies.get(position);

                Glide.with(mContext)
                        .load(IMAGE_POSTER_BASE_URL + movie.getPosterPath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .dontAnimate()
                        .into(holder.image);
                holder.bind(movie, mItemClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    private void setMovies(List<Movie> movies) {
        this.movies = checkNotNull(movies);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        void bind(final Movie movie, final OnItemClickListener listener) {

            itemView.setOnClickListener(v -> listener.onItemClick(movie));
        }

    }
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}