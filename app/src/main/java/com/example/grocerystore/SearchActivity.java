package com.example.grocerystore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.Helpers.AllCategoriesDialog;
import com.example.grocerystore.Helpers.GroceryItemAdapter;
import com.example.grocerystore.Models.GroceryItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.grocerystore.Helpers.AllCategoriesDialog.SELECTED_CATEGORIES;
import static com.example.grocerystore.Helpers.AllCategoriesDialog.START_ACTIVITY;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.SelectedCategories {

	private static final String TAG = "SearchActivityDebug";
	ColorStateList originalTextViewColor;
	private EditText searchInput;
	private ImageView searchButton;
	private TextView firstCategory, secondCategory, thirdCategory, seeAllCategories;
	private MaterialToolbar toolbar;
	private BottomNavigationView bottomNavigationView;
	private RecyclerView searchResultRecyclerView;
	private GroceryItemAdapter groceryItemAdapter;
	private ArrayList<GroceryItem> allGroceryItems;
	private ArrayList<String> allCategories;
	private ArrayList<Boolean> isCategorySelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		initViews();
		initBottomNavigation();
		setSupportActionBar(toolbar);
		originalTextViewColor = firstCategory.getTextColors();

		// Init grocery item adapter
		groceryItemAdapter = new GroceryItemAdapter(this);
		searchResultRecyclerView.setAdapter(groceryItemAdapter);
		searchResultRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

		// Get all grocery items from database
		allGroceryItems = Utils.getAllItems(this);
		if (null != allGroceryItems) {
			// Set all grocery item to adapter
			groceryItemAdapter.setGroceryItems(allGroceryItems);
		}

		allCategories = Utils.getAllCategories(this);
		isCategorySelected = new ArrayList<>();
		if (null != allCategories) {
			for (String category : allCategories) {
				isCategorySelected.add(false);
			}
			// Set categories text views
			if (allCategories.size() > 0) {
				setCategoriesTextViews(allCategories);
			}
		}

		// Handle result from dialog box opened from main menu
		Intent incomingSearchIntent = getIntent();
		if (null != incomingSearchIntent) {
			// Get incoming selected categories
			ArrayList<String> selectedCategories = incomingSearchIntent.getStringArrayListExtra(SELECTED_CATEGORIES);
			if (null != selectedCategories) {
				for (int i = 0; i < allCategories.size(); i++) {
					if (selectedCategories.contains(allCategories.get(i).toLowerCase())) {
						isCategorySelected.set(i, true);
					} else {
						isCategorySelected.set(i, false);
					}
				}
				setCategoriesColor();
				filterCategorySearch();
			}
		}

		// Filter all grocery items as you type in search bar
		searchInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterSearchResults(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		searchButton.setOnClickListener(v -> {
			String searchText = searchInput.getText().toString().toLowerCase();
			filterSearchResults(searchText);
		});

		handleCategorySearch();

		// Open dialog for all categories
		seeAllCategories.setOnClickListener(v -> {
			AllCategoriesDialog dialog = new AllCategoriesDialog();
			Bundle searchBundle = new Bundle();
			searchBundle.putString(START_ACTIVITY, getString(R.string.search_activity_name));

			boolean[] selectedCategories = new boolean[allCategories.size()];
			for (int i = 0; i < allCategories.size(); i++) {
				selectedCategories[i] = isCategorySelected.get(i);
			}
			searchBundle.putBooleanArray(SELECTED_CATEGORIES, selectedCategories);
			dialog.setArguments(searchBundle);
			dialog.show(getSupportFragmentManager(), getString(R.string.all_categories_dialog_tag));
		});
	}

	private void handleCategorySearch() {
		if (firstCategory.getVisibility() == View.VISIBLE) {
			firstCategory.setOnClickListener(v -> {
				if (!isCategorySelected.get(0)) {
					firstCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
					isCategorySelected.set(0, true);
				} else {
					firstCategory.setTextColor(originalTextViewColor);
					isCategorySelected.set(0, false);
				}
				filterCategorySearch();
			});
		}
		if (secondCategory.getVisibility() == View.VISIBLE) {
			secondCategory.setOnClickListener(v -> {
				if (!isCategorySelected.get(1)) {
					secondCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
					isCategorySelected.set(1, true);
				} else {
					secondCategory.setTextColor(originalTextViewColor);
					isCategorySelected.set(1, false);
				}
				filterCategorySearch();
			});
		}
		if (thirdCategory.getVisibility() == View.VISIBLE) {
			thirdCategory.setOnClickListener(v -> {
				if (!isCategorySelected.get(2)) {
					thirdCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
					isCategorySelected.set(2, true);
				} else {
					thirdCategory.setTextColor(originalTextViewColor);
					isCategorySelected.set(2, false);
				}
				filterCategorySearch();
			});
		}
	}

	private void filterCategorySearch() {
		ArrayList<GroceryItem> filteredItems = new ArrayList<>();
		ArrayList<String> chosenCategories = new ArrayList<>();
		String searchText = searchInput.getText().toString().toLowerCase();

		for (int i = 0; i < allCategories.size(); i++) {
			if (isCategorySelected.get(i)) {
				chosenCategories.add(allCategories.get(i).toLowerCase());
			}
		}
		if (chosenCategories.size() > 0) {
			for (GroceryItem item : allGroceryItems) {
				ArrayList<String> itemSubstrings = getItemSubstrings(item.getName().toLowerCase());

				if (chosenCategories.contains(item.getCategory().toLowerCase())) {
					if (itemSubstrings.contains(searchText)) {
						filteredItems.add(item);
					}
				}
			}
		} else {
			for (GroceryItem item : allGroceryItems) {
				ArrayList<String> itemSubstrings = getItemSubstrings(item.getName().toLowerCase());

				if (itemSubstrings.contains(searchText)) {
					filteredItems.add(item);
				}
			}
		}
		groceryItemAdapter.setGroceryItems(filteredItems);
		//Update user points for items that appear in search
		updateUserPoints(filteredItems);

	}

	private void filterSearchResults(String searchText) {
		ArrayList<GroceryItem> filteredItems = new ArrayList<>();
		ArrayList<String> chosenCategories = new ArrayList<>();
		for (int i = 0; i < allCategories.size(); i++) {
			if (isCategorySelected.get(i)) {
				chosenCategories.add(allCategories.get(i).toLowerCase());
			}
		}

		for (GroceryItem item : allGroceryItems) {

			// Get substrings of grocery item name
			ArrayList<String> itemSubstrings = getItemSubstrings(item.getName().toLowerCase());

			if (itemSubstrings.contains(searchText.toLowerCase())) {
				if (chosenCategories.size() > 0) {
					if (chosenCategories.contains(item.getCategory().toLowerCase())) {
						filteredItems.add(item);
					}
				} else {
					filteredItems.add(item);
				}
			}
		}
		groceryItemAdapter.setGroceryItems(filteredItems);
		//Update user points for items that appear in search
		updateUserPoints(filteredItems);
	}

	private ArrayList<String> getItemSubstrings(String itemName) {
		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < itemName.length(); i++) {
			for (int j = i; j <= itemName.length(); j++) {
				result.add(itemName.substring(i, j));
			}
		}
		return result;
	}

	private void setCategoriesTextViews(ArrayList<String> allCategories) {
		int n = allCategories.size();
		if (n == 1) {
			firstCategory.setText(allCategories.get(0));
			firstCategory.setVisibility(View.VISIBLE);
			secondCategory.setVisibility(View.GONE);
			thirdCategory.setVisibility(View.GONE);
		} else if (n == 2) {
			firstCategory.setText(allCategories.get(0));
			firstCategory.setVisibility(View.VISIBLE);
			secondCategory.setText(allCategories.get(1));
			secondCategory.setVisibility(View.VISIBLE);
			thirdCategory.setVisibility(View.GONE);
		} else if (n >= 3) {
			firstCategory.setText(allCategories.get(0));
			firstCategory.setVisibility(View.VISIBLE);
			secondCategory.setText(allCategories.get(1));
			secondCategory.setVisibility(View.VISIBLE);
			thirdCategory.setText(allCategories.get(2));
			thirdCategory.setVisibility(View.VISIBLE);
		} else {
			firstCategory.setVisibility(View.GONE);
			secondCategory.setVisibility(View.GONE);
			thirdCategory.setVisibility(View.GONE);
		}
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
				Intent cartIntent = new Intent(this, CartActivity.class);
				cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(cartIntent, ActivityOptions.makeCustomAnimation(
							this,
							R.anim.animation_right_to_left_enter,
							R.anim.animation_right_to_left_exit).toBundle()
					);
				} else {
					startActivity(cartIntent);
				}
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
		searchResultRecyclerView = findViewById(R.id.search_recycler_view);
	}


	// Handle selected categories from dialog menu opened from search activity
	@Override
	public void onSelectedCategoriesResult(ArrayList<String> selectedCategories) {
		for (int i = 0; i < allCategories.size(); i++) {
			if (selectedCategories.contains(allCategories.get(i).toLowerCase())) {
				isCategorySelected.set(i, true);
			} else {
				isCategorySelected.set(i, false);
			}
		}
		setCategoriesColor();
		filterCategorySearch();
	}

	private void setCategoriesColor() {
		if (firstCategory.getVisibility() == View.VISIBLE && isCategorySelected.get(0)) {
			firstCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
		} else if (firstCategory.getVisibility() == View.VISIBLE && !isCategorySelected.get(0)) {
			firstCategory.setTextColor(originalTextViewColor);
		}

		if (secondCategory.getVisibility() == View.VISIBLE && isCategorySelected.get(1)) {
			secondCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
		} else if (secondCategory.getVisibility() == View.VISIBLE && !isCategorySelected.get(1)) {
			secondCategory.setTextColor(originalTextViewColor);
		}

		if (thirdCategory.getVisibility() == View.VISIBLE && isCategorySelected.get(2)) {
			thirdCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
		} else if (thirdCategory.getVisibility() == View.VISIBLE && !isCategorySelected.get(2)) {
			thirdCategory.setTextColor(originalTextViewColor);
		}
	}

	private void updateUserPoints(ArrayList<GroceryItem> groceryItems) {
		for (GroceryItem item : groceryItems) {
			Utils.updateUserPoints(this, item, 1);
		}
	}
}