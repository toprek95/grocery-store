package com.example.grocerystore;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.Helpers.AddReviewDialog;
import com.example.grocerystore.Helpers.AddToCartDialog;
import com.example.grocerystore.Helpers.ReviewsAdapter;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.example.grocerystore.Services.TrackUserTime;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

	public static final String GROCERY_ITEM_ID = "grocery_item_id";
	private static final String TAG = "GroceryItemDebug";
	private MaterialToolbar groceryItemToolbar;
	private TextView groceryItemName, groceryItemPrice, groceryItemDescription, addNewReview;
	private ImageView groceryItemImage;
	private MaterialRatingBar groceryItemAverageRating;
	private Button addToCartButton;
	private RecyclerView reviewsRecyclerView;
	private ReviewsAdapter reviewsAdapter;

	private GroceryItem groceryItem;

	//Binding to TrackUserTIme service
	private boolean isBound;
	private TrackUserTime mService;
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) service;
			mService = binder.getService();
			isBound = true;
			mService.setItem(groceryItem);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery_item);

		initViews();

		reviewsAdapter = new ReviewsAdapter();
		reviewsRecyclerView.setAdapter(reviewsAdapter);
		reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		// Set action bar
		setSupportActionBar(groceryItemToolbar);

		initGroceryItemLayout();
	}

	private void initGroceryItemLayout() {
		Intent incomingGroceryItemIntent = getIntent();

		if (null != incomingGroceryItemIntent) {
			int groceryItemId = incomingGroceryItemIntent.getIntExtra(GROCERY_ITEM_ID, -1);

			if (groceryItemId != -1) {
				groceryItem = Utils.getGroceryItemById(this, groceryItemId);

				if (null != groceryItem) {
					// Increase user points by 1
					Utils.updateUserPoints(this, groceryItem, 1);

					groceryItemName.setText(groceryItem.getName());
					groceryItemPrice.setText(groceryItem.getPrice() + " $");
					groceryItemDescription.setText(groceryItem.getDescription());
					Glide.with(this)
							.asBitmap()
							.load(Uri.parse(groceryItem.getImageUrl()))
							.into(groceryItemImage);
					groceryItemAverageRating.setRating(groceryItem.getAverageRating());

					ArrayList<Review> groceryItemReviews = Utils.getReviewsByGroceryItemId(this, groceryItemId);
					if (null != groceryItemReviews) {
						reviewsAdapter.setReviews(groceryItemReviews);
					}

					addNewReview.setOnClickListener(v -> {
						AddReviewDialog dialog = new AddReviewDialog();
						Bundle bundle = new Bundle();
						bundle.putInt(GROCERY_ITEM_ID, groceryItemId);
						dialog.setArguments(bundle);
						dialog.show(getSupportFragmentManager(), "add_new_item_dialog");
					});

					addToCartButton.setOnClickListener(v -> {
						AddToCartDialog dialog = new AddToCartDialog();
						Bundle bundle = new Bundle();
						bundle.putInt(GROCERY_ITEM_ID, groceryItemId);
						dialog.setArguments(bundle);
						dialog.show(getSupportFragmentManager(), "add_to_cart_dialog");
					});

				}
			}
		}
	}

	private void initViews() {
		groceryItemName = findViewById(R.id.grocery_item_name);
		groceryItemPrice = findViewById(R.id.grocery_item_price);
		groceryItemDescription = findViewById(R.id.description_text_view);
		addNewReview = findViewById(R.id.add_new_review_grocery_item);

		groceryItemImage = findViewById(R.id.grocery_item_image);

		groceryItemAverageRating = findViewById(R.id.grocery_item_rating);

		addToCartButton = findViewById(R.id.add_to_cart_grocery_item_button);

		reviewsRecyclerView = findViewById(R.id.reviews_recycler_view);

		groceryItemToolbar = findViewById(R.id.grocery_item_toolbar);
	}

	// Adding new Review for GroceryItem
	@Override
	public void onAddReviewResult(Review review) {
		Log.d(TAG, "onAddReviewResult: onAddReviewResult: " + review.toString());
		//Update user points based on new rating and average rating
		updateUserPoints(review.getRating(), review.getGroceryItemId());

		Utils.addReview(this, review);
		ArrayList<Review> reviews = Utils.getReviewsByGroceryItemId(this, review.getGroceryItemId());
		if (null != reviews) {
			reviewsAdapter.setReviews(reviews);
			updateAverageRating(review.getGroceryItemId());
		}
	}

	private void updateUserPoints(float rating, int groceryItemId) {
		GroceryItem groceryItem = Utils.getGroceryItemById(this, groceryItemId);
		if (groceryItem != null) {
			float avgRating = groceryItem.getAverageRating();
			if (0 <= rating && rating < 1) {
				int userPoints = (1 - Math.round(avgRating)) * 2;
				Utils.updateUserPoints(this, groceryItem, userPoints);
			} else if (1 <= rating && rating < 2) {
				int userPoints = (2 - Math.round(avgRating)) * 2;
				Utils.updateUserPoints(this, groceryItem, userPoints);
			} else if (2 <= rating && rating < 3) {
				int userPoints = (3 - Math.round(avgRating)) * 2;
				Utils.updateUserPoints(this, groceryItem, userPoints);
			} else if (3 <= rating && rating < 4) {
				int userPoints = (4 - Math.round(avgRating)) * 2;
				Utils.updateUserPoints(this, groceryItem, userPoints);
			} else {
				int userPoints = (5 - Math.round(avgRating)) * 2;
				Utils.updateUserPoints(this, groceryItem, userPoints);
			}
		}
	}

	private void updateAverageRating(int id) {
		GroceryItem groceryItem = Utils.getGroceryItemById(this, id);
		if (groceryItem != null) {
			groceryItemAverageRating.setRating(groceryItem.getAverageRating());
			//Add 3 user points, because item just got reviewed
			Utils.updateUserPoints(this, groceryItem, 3);
		}
	}

	//Binding and unbinding from service

	@Override
	protected void onStart() {
		super.onStart();

		Intent intent = new Intent(this, TrackUserTime.class);
		bindService(intent, connection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (isBound) {
			unbindService(connection);
		}
	}
}