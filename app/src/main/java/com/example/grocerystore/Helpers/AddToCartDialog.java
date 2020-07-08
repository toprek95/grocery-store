package com.example.grocerystore.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.CartActivity;
import com.example.grocerystore.Fragments.FirstCartFragment;
import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.R;
import com.example.grocerystore.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.grocerystore.GroceryItemActivity.GROCERY_ITEM_ID;

public class AddToCartDialog extends DialogFragment {

	public static final String AVAILABLE_AMOUNT_KEY = "available_amount_key";
	private static final String TAG = "AddToCartDialogDebug";
	private TextView itemName, cancelButton;
	private Button addToCartButton;
	private TextInputEditText numberOfItems;
	private TextInputLayout numberOfItemsLayout;

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_to_cart, null);

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
					numberOfItemsLayout.setHelperText("Max: " + groceryItem.getAvailableAmount());
					itemName.setText(groceryItem.getName());

					addToCartButton.setOnClickListener(v -> {
						String amountEntered = numberOfItems.getText().toString();
						if (amountEntered.isEmpty()) {
							numberOfItemsLayout.setError("Please enter number of items");
						} else {
							int amount = Integer.parseInt(amountEntered);

							if (amount > groceryItem.getAvailableAmount()) {
								numberOfItemsLayout.setError("Not enough items in storage!");
							} else if (amount <= 0) {
								numberOfItemsLayout.setError("Number of items must be greater than zero!");
							} else {
								dismiss();
								Intent intent = new Intent(getContext(), CartActivity.class);
								intent.putExtra(AVAILABLE_AMOUNT_KEY, amount);
								intent.putExtra(GROCERY_ITEM_ID, groceryItemId);
								startActivity(intent);
//								Bundle bundle = new Bundle();
//								bundle.putInt(AVAILABLE_AMOUNT_KEY, amount);
//								bundle.putInt(GROCERY_ITEM_ID, groceryItemId);
//								FirstCartFragment fragment = new FirstCartFragment();
//								fragment.setArguments(bundle);
//								FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//								transaction.replace(R.id.cart_fragments_container, fragment);
//								transaction.commit();
							}
						}
					});
				}
			}
		}

		cancelButton.setOnClickListener(v -> dismiss());

		numberOfItems.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (null != numberOfItemsLayout.getError()) {
					numberOfItemsLayout.setError(null);
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (null != numberOfItemsLayout.getError()) {
					numberOfItemsLayout.setError(null);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return builder.create();
	}

	private void initViews(View view) {
		itemName = view.findViewById(R.id.add_to_cart_item_name);
		cancelButton = view.findViewById(R.id.add_to_cart_cancel);
		addToCartButton = view.findViewById(R.id.add_to_cart_button);
		numberOfItems = view.findViewById(R.id.number_of_items_edit_text);
		numberOfItemsLayout = view.findViewById(R.id.number_of_items_layout);
	}
}
