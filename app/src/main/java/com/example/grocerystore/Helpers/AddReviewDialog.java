package com.example.grocerystore.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.example.grocerystore.R;
import com.example.grocerystore.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.example.grocerystore.GroceryItemActivity.GROCERY_ITEM_ID;

public class AddReviewDialog extends DialogFragment {

	private TextView reviewItemName, cancelReview;
	private TextInputEditText reviewUserName, reviewText;
	private TextInputLayout reviewUserNameLayout, reviewTextLayout;
	private MaterialRatingBar reviewRating;
	private Button addNewReviewButton;

	private AddReview addReview;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_new_review, null);

		initViews(view);

		setCancelable(false);

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
				.setView(view);

		Bundle incomingBundle = getArguments();

		if (null != incomingBundle) {
			int groceryItemId = incomingBundle.getInt(GROCERY_ITEM_ID, -1);

			if (groceryItemId != -1) {
				final GroceryItem groceryItem = Utils.getGroceryItemById(getContext(), groceryItemId);

				if (null != groceryItem) {
					reviewItemName.setText(groceryItem.getName());

					// Get new rating
					final float[] newRating = {0};
					reviewRating.setOnRatingChangeListener((ratingBar, rating) -> newRating[0] = rating);

					addNewReviewButton.setOnClickListener(v -> {
						String userName = reviewUserName.getText().toString();
						String review = reviewText.getText().toString();
						String currentDate = getCurrentDate();

						if (userName.isEmpty()) {
							reviewUserNameLayout.setError("Enter user name");
						} else if (newRating[0] <= 3 && review.isEmpty()) {
							reviewTextLayout.setError("Please explain why your rating is that low");

						} else {
							try {
								addReview = (AddReview) getActivity();
								addReview.onAddReviewResult(new Review(groceryItemId, userName, review, currentDate, newRating[0]));
								dismiss();
							} catch (ClassCastException e) {
								e.printStackTrace();
								Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
								dismiss();
							}
						}
					});

					cancelReview.setOnClickListener(v -> dismiss());

				}
			}
		}

		handleInputError();

		return builder.create();
	}

	private void handleInputError() {
		reviewUserName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				reviewUserNameLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		reviewText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				reviewTextLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YYYY HH:mm", Locale.getDefault());
		return simpleDateFormat.format(calendar.getTime());
	}

	private void initViews(View view) {
		reviewItemName = view.findViewById(R.id.add_new_review_item_name);
		cancelReview = view.findViewById(R.id.add_new_review_cancel);
		reviewUserName = view.findViewById(R.id.add_new_review_user_name);
		reviewUserNameLayout = view.findViewById(R.id.add_new_review_user_name_layout);
		reviewText = view.findViewById(R.id.add_new_review_text);
		reviewTextLayout = view.findViewById(R.id.add_new_review_text_layout);
		reviewRating = view.findViewById(R.id.add_new_review_rating);
		addNewReviewButton = view.findViewById(R.id.add_new_review_button);
	}

	// Callback interface to add new review on closing dialog in GroceryItemActivity
	public interface AddReview {
		void onAddReviewResult(Review review);
	}
}
