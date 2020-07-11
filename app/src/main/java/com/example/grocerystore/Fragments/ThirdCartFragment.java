package com.example.grocerystore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.Models.Order;
import com.example.grocerystore.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.grocerystore.Fragments.SecondCartFragment.ORDER_KEY;

public class ThirdCartFragment extends Fragment {

	private TextView itemsTextView, totalPriceTextView, fullNameTextView, addressTextView, phoneNumberTextView, backButton;
	private RadioGroup paymentMethodRadioGroup;
	private Button checkoutButton;
	private Order order;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cart_third, container, false);

		initViews(view);

		Bundle orderBundle = getArguments();
		if (null != orderBundle) {
			String jsonOrder = orderBundle.getString(ORDER_KEY);
			if (null != jsonOrder) {
				Gson gson = new Gson();
				Type type = new TypeToken<Order>() {
				}.getType();

				order = gson.fromJson(jsonOrder, type);

				if (null != order) {
					StringBuilder items = new StringBuilder();
					for (int i = 0; i < order.getCartItems().size(); i++) {
						items.append(order.getCartItems().get(i).getItem().getName());
						if (i == order.getCartItems().size() - 1) {
							items.append(".");
						} else {
							items.append(", ");
						}
					}
					itemsTextView.setText(items.toString());
					totalPriceTextView.setText(order.getTotalPrice() + " USD");
					fullNameTextView.setText(order.getFirstName() + " " + order.getLastName());
					addressTextView.setText(order.getStreetAddress() + ", " + order.getZipCode() + " " + order.getCity());
					phoneNumberTextView.setText(order.getPhoneNumber());
				}
			}
		}

		backButton.setOnClickListener(v -> {
			SecondCartFragment secondCartFragment = new SecondCartFragment();
			secondCartFragment.setArguments(orderBundle);
			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.cart_fragments_container, secondCartFragment);
			transaction.commit();
		});

		checkoutButton.setOnClickListener(v -> {
			checkPaymentMethod();
			order.setSuccessful(true); //For testing purposes
			// TODO: 11-Jul-2020 Send request with Retrofit
		});

		return view;
	}

	private void checkPaymentMethod() {
		if (paymentMethodRadioGroup.getCheckedRadioButtonId() == R.id.credit_card_radio_button) {
			order.setPaymentMethod(getString(R.string.creditcard));
		} else if (paymentMethodRadioGroup.getCheckedRadioButtonId() == R.id.paypal_radio_button) {
			order.setPaymentMethod(getString(R.string.paypal));
		} else {
			order.setPaymentMethod(getString(R.string.unknown_payment));
		}
	}

	private void initViews(View view) {
		itemsTextView = view.findViewById(R.id.items_text_view);
		totalPriceTextView = view.findViewById(R.id.total_price_text_view);
		fullNameTextView = view.findViewById(R.id.full_name_text_view);
		addressTextView = view.findViewById(R.id.address_text_view);
		phoneNumberTextView = view.findViewById(R.id.phone_number_text_view);
		backButton = view.findViewById(R.id.back_button);
		paymentMethodRadioGroup = view.findViewById(R.id.payment_method_radio_group);
		checkoutButton = view.findViewById(R.id.checkout_button);
	}
}
