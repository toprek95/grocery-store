package com.example.grocerystore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.Helpers.AddReviewDialog;
import com.example.grocerystore.Helpers.ReviewsAdapter;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

	public static final String GROCERY_ITEM_ID = "grocery_item_id";
	private static final String TAG = "GroceryItemDebug";
	MaterialToolbar groceryItemToolbar;
	private TextView groceryItemName, groceryItemPrice, addNewReview;
	private ImageView groceryItemImage;
	private MaterialRatingBar groceryItemAverageRating;
	private Button addToCartButton;
	private RecyclerView reviewsRecyclerView;
	private ReviewsAdapter reviewsAdapter;

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
				GroceryItem groceryItem = Utils.getGroceryItemById(this, groceryItemId);

				if (null != groceryItem) {
					groceryItemName.setText(groceryItem.getName());
					groceryItemPrice.setText(groceryItem.getPrice() + " $");
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
						// TODO: 15-Jun-2020 Add to cart selected item
						Toast.makeText(GroceryItemActivity.this, "Add to cart clicked", Toast.LENGTH_SHORT).show();
					});

				}
			}
		}
	}

	private void initViews() {
		groceryItemName = findViewById(R.id.grocery_item_name);
		groceryItemPrice = findViewById(R.id.grocery_item_price);
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
		Utils.addReview(this, review);
		ArrayList<Review> reviews = Utils.getReviewsByGroceryItemId(this, review.getGroceryItemId());
		if (null != reviews) {
			reviewsAdapter.setReviews(reviews);
			updateAverageRating(review.getGroceryItemId());
		}
	}

	private void updateAverageRating(int id) {
		GroceryItem groceryItem = Utils.getGroceryItemById(this, id);
		if (groceryItem != null) {
			groceryItemAverageRating.setRating(groceryItem.getAverageRating());
		}
	}
}