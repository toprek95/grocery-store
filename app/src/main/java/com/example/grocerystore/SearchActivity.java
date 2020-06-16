package com.example.grocerystore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

	private EditText searchInput;
	private ImageView searchButton;
	private TextView firstCategory, secondCategory, thirdCategory, seeAllCategories;
	private MaterialToolbar toolbar;
	private BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initViews();
		initBottomNavigation();
		setSupportActionBar(toolbar);
	}

	private void initBottomNavigation() {
		bottomNavigationView.setSelectedItemId(R.id.search);

		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.search) {
				// Already here
			}
			if (item.getItemId() == R.id.home) {
				Intent mainIntent = new Intent(this, MainActivity.class);
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(mainIntent, ActivityOptions.makeCustomAnimation(
							this,
							R.anim.animation_right_to_left_enter,
							R.anim.animation_right_to_left_exit).toBundle()
					);
				} else {
					startActivity(mainIntent);
				}
			}
			if (item.getItemId() == R.id.cart) {
				Toast.makeText(this, "Cart selected", Toast.LENGTH_SHORT).show();
			}

			return false;
		});
	}

	private void initViews() {
		searchInput = findViewById(R.id.search_input);
		searchButton = findViewById(R.id.search_button);
		firstCategory = findViewById(R.id.first_category_text);
		secondCategory = findViewById(R.id.second_category_text);
		thirdCategory = findViewById(R.id.third_category_text);
		seeAllCategories = findViewById(R.id.see_all_categories_text);
		toolbar = findViewById(R.id.search_toolbar);
		bottomNavigationView = findViewById(R.id.bottom_navigation_view);
	}
}