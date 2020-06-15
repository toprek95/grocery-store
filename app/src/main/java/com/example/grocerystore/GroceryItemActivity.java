package com.example.grocerystore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.Helpers.ReviewsAdapter;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class GroceryItemActivity extends AppCompatActivity {

	public static final String GROCERY_ITEM_ID = "grocery_item_id";

	private TextView groceryItemName, groceryItemPrice, addNewReview;
	private ImageView groceryItemImage;
	private MaterialRatingBar groceryItemAverageRating;
	private Button addToCartButton;
	private RecyclerView reviewsRecyclerView;
	MaterialToolbar groceryItemToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery_item);

		initViews();

		setSupportActionBar(groceryItemToolbar);

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

					ArrayList<Review> groceryItemReviews = groceryItem.getReviews();
					if (null != groceryItemReviews) {
						ReviewsAdapter adapter = new ReviewsAdapter();
						reviewsRecyclerView.setAdapter(adapter);
						reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
						adapter.setReviews(groceryItemReviews);
					}

					addNewReview.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO: 15-Jun-2020 Create new review dialog
							Toast.makeText(GroceryItemActivity.this, "Create new review clicked", Toast.LENGTH_SHORT).show();
						}
					});

					addToCartButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO: 15-Jun-2020 Add to cart selected item
							Toast.makeText(GroceryItemActivity.this, "Add to cart clicked", Toast.LENGTH_SHORT).show();
						}
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
}