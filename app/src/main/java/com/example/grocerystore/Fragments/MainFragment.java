package com.example.grocerystore.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.CartActivity;
import com.example.grocerystore.Helpers.GroceryItemAdapter;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.R;
import com.example.grocerystore.SearchActivity;
import com.example.grocerystore.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class MainFragment extends Fragment {

	private BottomNavigationView bottomNavigationView;
	private RecyclerView newItemsRecyclerView, popularItemsRecyclerView, suggestedItemsRecyclerView;
	private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		initViews(view);

		initBottomNavigationView();

		initRecyclerViews();

		// Get all grocery store items
		ArrayList<GroceryItem> allGroceryItems = new ArrayList<>();
		ArrayList<GroceryItem> popularGroceryItems = new ArrayList<>();
		ArrayList<GroceryItem> suggestedGroceryItems = new ArrayList<>();
		if (null != getContext()) {
			allGroceryItems = Utils.getAllItems(getContext());
			popularGroceryItems = Utils.getAllItems(getContext());
			suggestedGroceryItems = Utils.getAllItems(getContext());
		}

		if (null != allGroceryItems) {
			// To show the newest items first
			Collections.sort(allGroceryItems, Collections.reverseOrder(GroceryItem.groceryItemIdComparator));
			newItemsAdapter.setGroceryItems(allGroceryItems);
		}

		if (null != popularGroceryItems) {
			// To show the popular items first
			Collections.sort(popularGroceryItems, Collections.reverseOrder(GroceryItem.groceryItemPopularityComparator));
			popularItemsAdapter.setGroceryItems(popularGroceryItems);
		}

		if (null != suggestedGroceryItems) {
			// To show the suggested items first
			Collections.sort(suggestedGroceryItems, Collections.reverseOrder(GroceryItem.groceryItemSuggestedComparator));
			suggestedItemsAdapter.setGroceryItems(suggestedGroceryItems);
		}

		return view;
	}

	private void initViews(View view) {
		bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
		newItemsRecyclerView = view.findViewById(R.id.new_items_recycler_view);
		suggestedItemsRecyclerView = view.findViewById(R.id.suggested_items_recycler_view);
		popularItemsRecyclerView = view.findViewById(R.id.popular_items_recycler_view);
	}

	private void initBottomNavigationView() {
		bottomNavigationView.setSelectedItemId(R.id.home);

		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.search) {
				Intent searchIntent = new Intent(getContext(), SearchActivity.class);
				searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(searchIntent, ActivityOptions.makeCustomAnimation(
							getActivity(),
							R.anim.animation_left_to_right_enter,
							R.anim.animation_left_to_right_exit).toBundle()
					);
				} else {
					startActivity(searchIntent);
				}
			}
			if (item.getItemId() == R.id.home) {
				// Already here
			}
			if (item.getItemId() == R.id.cart) {
				Intent cartIntent = new Intent(getContext(), CartActivity.class);
				cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					startActivity(cartIntent, ActivityOptions.makeCustomAnimation(
							getActivity(),
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

	private void initRecyclerViews() {
		newItemsAdapter = new GroceryItemAdapter(getContext());
		newItemsRecyclerView.setAdapter(newItemsAdapter);
		newItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

		popularItemsAdapter = new GroceryItemAdapter(getContext());
		popularItemsRecyclerView.setAdapter(popularItemsAdapter);
		popularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

		suggestedItemsAdapter = new GroceryItemAdapter(getContext());
		suggestedItemsRecyclerView.setAdapter(suggestedItemsAdapter);
		suggestedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
	}
}
