package com.example.grocerystore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.Fragments.FirstCartFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

	private MaterialToolbar toolbar;
	private FrameLayout cartFragmentsContainer;
	private BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		initViews();
		initBottomNavigation();
		setSupportActionBar(toolbar);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(cartFragmentsContainer.getId(), new FirstCartFragment());
		transaction.commit();
	}

	private void initBottomNavigation() {
		bottomNavigationView.setSelectedItemId(R.id.cart);

		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.search) {
				Intent searchIntent = new Intent(this, SearchActivity.class);
				searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(searchIntent, ActivityOptions.makeCustomAnimation(
							this,
							R.anim.animation_left_to_right_enter,
							R.anim.animation_left_to_right_exit).toBundle()
					);
				} else {
					startActivity(searchIntent);
				}
			}
			if (item.getItemId() == R.id.home) {
				Intent mainIntent = new Intent(this, MainActivity.class);
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(mainIntent, ActivityOptions.makeCustomAnimation(
							this,
							R.anim.animation_left_to_right_enter,
							R.anim.animation_left_to_right_exit).toBundle()
					);
				} else {
					startActivity(mainIntent);
				}
			}
			if (item.getItemId() == R.id.cart) {
				// Already here
			}

			return false;
		});
	}

	private void initViews() {
		toolbar = findViewById(R.id.cart_toolbar);
		cartFragmentsContainer = findViewById(R.id.cart_fragments_container);
		bottomNavigationView = findViewById(R.id.bottom_navigation_view);
	}
}