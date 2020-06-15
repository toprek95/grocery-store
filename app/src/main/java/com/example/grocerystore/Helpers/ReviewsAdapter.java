package com.example.grocerystore.Helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.Models.Review;
import com.example.grocerystore.R;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

	private ArrayList<Review> reviews = new ArrayList<>();

	public ReviewsAdapter() {
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Review review = reviews.get(position);

		holder.reviewAuthorName.setText(review.getUserName());
		holder.reviewText.setText(review.getReview());
		holder.reviewDate.setText(review.getDate());
		holder.reviewRating.setRating(review.getRating());
	}

	@Override
	public int getItemCount() {
		return reviews.size();
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
		notifyDataSetChanged();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView reviewAuthorName, reviewText, reviewDate;
		private MaterialRatingBar reviewRating;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			reviewAuthorName = itemView.findViewById(R.id.review_author_name);
			reviewDate = itemView.findViewById(R.id.review_date);
			reviewText = itemView.findViewById(R.id.review_text);

			reviewRating = itemView.findViewById(R.id.review_rating);
		}
	}
}
