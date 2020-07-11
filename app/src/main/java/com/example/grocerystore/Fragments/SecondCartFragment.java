package com.example.grocerystore.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.Models.Order;
import com.example.grocerystore.R;
import com.example.grocerystore.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecondCartFragment extends Fragment {

	public static final String ORDER_KEY = "order_key";
	private TextInputLayout firstNameLayout, lastNameLayout, phoneNumberLayout, emailLayout, addressLayout, cityLayout, zipCodeLayout;
	private TextInputEditText firstNameEditText, lastNameEditText, phoneNumberEditText, emailEditText, addressEditText, cityEditText, zipCodeEditText;
	private TextView backButton;
	private Button nextButton;
	private Order order = new Order();
	private Gson gson = new Gson();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cart_second, container, false);

		initViews(view);

		presetData();

		backButton.setOnClickListener(v -> {
			//Save entered data to bundle and pass it to first fragment
			enterOrderData();

			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			FirstCartFragment firstCartFragment = new FirstCartFragment();
			Bundle bundle = new Bundle();

			bundle.putString(ORDER_KEY, gson.toJson(order));
			firstCartFragment.setArguments(bundle);

			transaction.replace(R.id.cart_fragments_container, firstCartFragment);
			transaction.commit();
		});

		nextButton.setOnClickListener(v -> {
			if (firstNameEditText.getText().toString().isEmpty()) {
				firstNameLayout.setError(getString(R.string.error_first_name));
				showKeyboard(firstNameEditText);
			} else if (lastNameEditText.getText().toString().isEmpty()) {
				lastNameLayout.setError(getString(R.string.error_last_name));
				showKeyboard(lastNameEditText);
			} else if (phoneNumberEditText.getText().toString().isEmpty()) {
				phoneNumberLayout.setError(getString(R.string.error_phone_number));
				showKeyboard(phoneNumberEditText);
			} else if (emailEditText.getText().toString().isEmpty()) {
				emailLayout.setError(getString(R.string.error_email));
				showKeyboard(emailEditText);
			} else if (addressEditText.getText().toString().isEmpty()) {
				addressLayout.setError(getString(R.string.error_street_address));
				showKeyboard(addressEditText);
			} else if (cityEditText.getText().toString().isEmpty()) {
				cityLayout.setError(getString(R.string.error_city));
				showKeyboard(cityEditText);
			} else if (zipCodeEditText.getText().toString().isEmpty()) {
				zipCodeLayout.setError(getString(R.string.error_zip_code));
				showKeyboard(zipCodeEditText);
			} else {
				enterOrderData();
				ThirdCartFragment thirdCartFragment = new ThirdCartFragment();
				Bundle orderBundle = new Bundle();
				orderBundle.putString(ORDER_KEY, gson.toJson(order));
				thirdCartFragment.setArguments(orderBundle);
				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.cart_fragments_container, thirdCartFragment);
				transaction.commit();
			}
		});

		handleWarnings();

		return view;
	}

	private void presetData() {
		Bundle bundle = getArguments();
		if (null != bundle) {
			String jsonOrder = bundle.getString(ORDER_KEY);
			if (null != jsonOrder) {
				Type type = new TypeToken<Order>() {
				}.getType();
				Order savedOrder = gson.fromJson(jsonOrder, type);
				if (null != savedOrder) {
					if (null != savedOrder.getFirstName())
						firstNameEditText.setText(savedOrder.getFirstName());
					if (null != savedOrder.getLastName())
						lastNameEditText.setText(savedOrder.getLastName());
					if (null != savedOrder.getPhoneNumber())
						phoneNumberEditText.setText(savedOrder.getPhoneNumber());
					if (null != savedOrder.getEmail())
						emailEditText.setText(savedOrder.getEmail());
					if (null != savedOrder.getStreetAddress())
						addressEditText.setText(savedOrder.getStreetAddress());
					if (null != savedOrder.getCity())
						cityEditText.setText(savedOrder.getCity());
					if (null != savedOrder.getZipCode())
						zipCodeEditText.setText(savedOrder.getZipCode());
				}
			}
		}
	}

	private void showKeyboard(TextInputEditText editText) {
		editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	private void handleWarnings() {
		firstNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				firstNameLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		lastNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				lastNameLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		phoneNumberEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				phoneNumberLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		emailEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				emailLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		addressEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				addressLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		cityEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				cityLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		zipCodeEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				zipCodeLayout.setError(null);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	private void enterOrderData() {
		ArrayList<CartItem> cartItems = Utils.getCartItems(getContext());
		order.setCartItems(cartItems);
		order.setTotalPrice(getTotalPrice(cartItems));
		order.setFirstName(firstNameEditText.getText().toString());
		order.setLastName(lastNameEditText.getText().toString());
		order.setPhoneNumber(phoneNumberEditText.getText().toString());
		order.setEmail(emailEditText.getText().toString());
		order.setStreetAddress(addressEditText.getText().toString());
		order.setCity(cityEditText.getText().toString());
		order.setZipCode(zipCodeEditText.getText().toString());
	}

	private double getTotalPrice(ArrayList<CartItem> cartItems) {
		double price = 0;
		for (CartItem item : cartItems) {
			price += item.getTotalPrice();
		}
		return Math.round(price * 100.0) / 100.0;
	}

	private void initViews(View view) {
		firstNameLayout = view.findViewById(R.id.first_name_layout);
		lastNameLayout = view.findViewById(R.id.last_name_layout);
		phoneNumberLayout = view.findViewById(R.id.phone_number_layout);
		emailLayout = view.findViewById(R.id.email_layout);
		addressLayout = view.findViewById(R.id.address_layout);
		cityLayout = view.findViewById(R.id.city_layout);
		zipCodeLayout = view.findViewById(R.id.zip_code_layout);
		firstNameEditText = view.findViewById(R.id.first_name_edit_text);
		lastNameEditText = view.findViewById(R.id.last_name_edit_text);
		phoneNumberEditText = view.findViewById(R.id.phone_number_edit_text);
		emailEditText = view.findViewById(R.id.email_edit_text);
		addressEditText = view.findViewById(R.id.address_edit_text);
		cityEditText = view.findViewById(R.id.city_edit_text);
		zipCodeEditText = view.findViewById(R.id.zip_code_edit_text);
		backButton = view.findViewById(R.id.back_button);
		nextButton = view.findViewById(R.id.next_button);
	}
}
