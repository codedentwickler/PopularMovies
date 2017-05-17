package com.udacity.fasttrack.popularmovies.presentation.details;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final ReviewAdapter.OnItemClickListener mItemClickListener;

    @Nullable
    private List<Review> reviews;

    ReviewAdapter(@Nullable List<Review> reviews, OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        this.reviews = reviews;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie_review, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (reviews == null) {
            return;
        }

        holder.bind(reviews.get(position), mItemClickListener);
    }

    void replaceData(List<Review> reviews) {
        setReviews(reviews);
        notifyDataSetChanged();
    }

    private void setReviews(@Nullable List<Review> reviews) {
        this.reviews = checkNotNull(reviews);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    @Nullable
    List<Review> getReviews() {
        if (reviews == null){
            return new ArrayList<>(0);
        }

        return reviews;
    }


    interface OnItemClickListener {
        void onItemClick(Review review);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_movie_review_content)
        TextView contentTextView;
        @BindView(R.id.text_movie_review_author)
        TextView authorTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Review review, final ReviewAdapter.OnItemClickListener listener) {

            contentTextView.setText(review.getContent());
            authorTextView.setText(review.getAuthor());

            itemView.setOnClickListener(v -> listener.onItemClick(review));
        }
    }
}
