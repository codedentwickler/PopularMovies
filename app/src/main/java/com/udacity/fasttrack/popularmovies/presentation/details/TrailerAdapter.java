package com.udacity.fasttrack.popularmovies.presentation.details;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {


    private static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private final TrailerAdapter.OnItemClickListener mItemClickListener;

    @Nullable
    private List<Trailer> trailers;

    TrailerAdapter(ArrayList<Trailer> trailers, OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        setTrailers(trailers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie_trailers, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (trailers == null) {
            return;
        }
        holder.bind(trailers.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (trailers == null) {
            return 0;
        }
        return trailers.size();
    }
     public List<Trailer> getTrailers(){
        if (trailers == null){
            return new ArrayList<>(0);
        }
        return trailers;

     }

    void replaceData(List<Trailer> trailers) {
        setTrailers(trailers);
        notifyDataSetChanged();
    }

    private void setTrailers(List<Trailer> trailers) {
        this.trailers = checkNotNull(trailers);
    }

    interface OnItemClickListener {
        void onItemClick(Trailer trailer);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_video_thumbnail)
        ImageView movieVideoThumbnail;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Trailer trailer, final OnItemClickListener listener) {

            if (trailer.isYoutubeVideo()) {
                Glide.with(itemView.getContext())
                        .load(String.format(YOUTUBE_THUMBNAIL, trailer.getKey()))
                        .placeholder(new ColorDrawable(itemView.getContext()
                                .getResources().getColor(R.color.accent_material_light)))
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .crossFade()
                        .into(movieVideoThumbnail);
            }
            itemView.setOnClickListener(v -> listener.onItemClick(trailer));

        }

    }
}
