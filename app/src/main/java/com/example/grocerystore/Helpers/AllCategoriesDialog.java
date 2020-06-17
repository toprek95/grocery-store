package com.example.grocerystore.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.R;
import com.example.grocerystore.SearchActivity;
import com.example.grocerystore.Utils;

import java.util.ArrayList;

public class AllCategoriesDialog extends DialogFragment {

	private static final String TAG = "SearchActivityDebug";

	public static final String START_ACTIVITY = "activity";
	public static final String SELECTED_CATEGORIES = "selected_categories";

	private Button confirmButton;
	private TextView cancelButton;
	private RecyclerView categoriesRecyclerView;

	private ArrayList<Boolean> isCategorySelected = new ArrayList<>();
	private ArrayList<String> allCategories;
	private ArrayList<String> newSelectedCategories = new ArrayList<>();

	private SelectedCategories selectedCategoriesCallback;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_categories, null);

		initViews(view);

		// Initialize all categories
		allCategories = Utils.getAllCategories(getContext());

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
				.setView(view);

		setCancelable(false);

		// Two different places from where is this dialog is going to be called
		// First: from within search activity - callback interface
		// Second: from Main menu navigation - going to redirect to search activity as new intent
		// To know from where this dialog is called, I'm going to make constant START_ACTIVITY

		// Receiving bundle on dialog create to know from where is dialog opened
		Bundle searchBundle = getArguments();
		if (null != searchBundle) {
			// Receiving items from bundle
			String startActivity = searchBundle.getString(START_ACTIVITY);

			if (startActivity != null ) {

				initDialogElements(searchBundle, startActivity);

				// Set adapter for recycler view
				AllCategoriesAdapter adapter = new AllCategoriesAdapter(getContext());
				categoriesRecyclerView.setAdapter(adapter);
				categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
				adapter.setIsCategorySelected(isCategorySelected);

				// Send selected categories to respective start activities
				sendSelectedCategoriesToStartActivity(adapter, startActivity);
			}

			// Cancel button
			cancelButton.setOnClickListener(v -> dismiss());
		}

		return builder.create();
	}

	private void sendSelectedCategoriesToStartActivity(AllCategoriesAdapter adapter, String startActivity) {

		if (startActivity.equalsIgnoreCase(getString(R.string.search_activity_name))) {
			// Get result if confirm is clicked and pass it to parent activity via callback
			confirmButton.setOnClickListener(v -> {
				// Init callback interface
				selectedCategoriesCallback = (SelectedCategories) getActivity();
				if (null != selectedCategoriesCallback) {
					newSelectedCategories = adapter.getSelectedCategories();
					selectedCategoriesCallback.onSelectedCategoriesResult(newSelectedCategories);
					dismiss();
				}
			});
		}
		if (startActivity.equalsIgnoreCase(getString(R.string.main_activity_name))) {
			// Get result if confirm is clicked and pass it to search activity via new intent
			confirmButton.setOnClickListener(v -> {
				Intent newSearchIntent = new Intent(getContext(), SearchActivity.class);
				newSearchIntent.putStringArrayListExtra(SELECTED_CATEGORIES, adapter.getSelectedCategories());
				// clear back stack
				newSearchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(newSearchIntent);
				dismiss();
			});
		}
	}

	private void initDialogElements(Bundle searchBundle, String startActivity) {
		if (startActivity.equalsIgnoreCase(getString(R.string.search_activity_name))) {
			// Init selected categories
			boolean[] selectedCategories = searchBundle.getBooleanArray(SELECTED_CATEGORIES);
			if (selectedCategories != null) {
				for (boolean b : selectedCategories) {
					isCategorySelected.add(b);
				}
			}

		}
		if (startActivity.equalsIgnoreCase(getString(R.string.main_activity_name))) {
			// None categories should be selected when opening dialog from main activity
			for (int i = 0; i < allCategories.size(); i++) {
				isCategorySelected.add(false);
			}
		}
	}

	private void initViews(View view) {
		confirmButton = view.findViewById(R.id.all_categories_confirm_button);
		cancelButton = view.findViewById(R.id.all_categories_cancel);
		categoriesRecyclerView = view.findViewById(R.id.all_categories_recycler_view);
	}

	public interface SelectedCategories {
		void onSelectedCategoriesResult(ArrayList<String> selectedCategories);
	}
}
