package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.grocerystore.Fragments.MainFragment;
import com.example.grocerystore.Helpers.AllCategoriesDialog;
import com.example.grocerystore.Helpers.LicencesDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import static com.example.grocerystore.Helpers.AllCategoriesDialog.START_ACTIVITY;
import static com.example.grocerystore.WebsiteActivity.URL_KEY;

public class MainActivity extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private MaterialToolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		// Initialize shared preferences
		Utils.initSharedPreferences(this);

		// Set toolbar
		setSupportActionBar(toolbar);
		setToggleToolbar();

		// Navigation from menu
		onNavigationItemSelected();

		//Navigation header textView and image
		handleNavigationHeader();

		FragmentTransaction mainFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mainFragmentTransaction.replace(R.id.main_fragment_container, new MainFragment());
		mainFragmentTransaction.commit();
	}

	private void handleNavigationHeader() {
		View headerView = navigationView.getHeaderView(0);
		TextView webSiteTextView = (TextView) headerView.findViewById(R.id.navigation_menu_website);
		ImageView appImage = (ImageView) headerView.findViewById(R.id.navigation_menu_icon);

		Glide.with(this)
				.asBitmap()
				.load(R.mipmap.ic_logo)
				.into(appImage);
		webSiteTextView.setOnClickListener(v -> {
			Intent webIntent = new Intent(MainActivity.this, WebsiteActivity.class);
			webIntent.putExtra(URL_KEY, "https://github.com/toprek95");
			startActivity(webIntent);
		});
	}

	private void initViews() {
		drawerLayout = findViewById(R.id.drawer);
		navigationView = findViewById(R.id.navigation_view);
		toolbar = findViewById(R.id.toolbar);
	}

	private void setToggleToolbar() {
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				toolbar,
				R.string.drawer_open,
				R.string.drawer_close
		);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}

	private void onNavigationItemSelected() {
		navigationView.setNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.cart) {
				Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
				cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(cartIntent);
			}
			if (item.getItemId() == R.id.categories) {
				AllCategoriesDialog dialog = new AllCategoriesDialog();
				Bundle bundle = new Bundle();
				bundle.putString(START_ACTIVITY, getString(R.string.main_activity_name));
				dialog.setArguments(bundle);
				dialog.show(getSupportFragmentManager(), getString(R.string.all_categories_dialog_tag));
			}
			if (item.getItemId() == R.id.about) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.app_name)
						.setMessage(R.string.app_description)
						.setPositiveButton("Visit", (dialog, which) -> {
							Intent webIntent = new Intent(MainActivity.this, WebsiteActivity.class);
							webIntent.putExtra(URL_KEY, "https://github.com/toprek95/grocery-store");
							startActivity(webIntent);
						})
						.create().show();
			}
			if (item.getItemId() == R.id.terms) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.terms_and_conditions)
						.setMessage(R.string.terms_description)
						.setPositiveButton("Dismiss", (dialog, which) -> {
						})
						.create().show();
			}
			if (item.getItemId() == R.id.licences) {
				new LicencesDialog().show(getSupportFragmentManager(), "licences");
			}
			return false;
		});
	}


}