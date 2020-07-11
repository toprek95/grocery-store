package com.example.grocerystore.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.grocerystore.MainActivity;
import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.Models.Order;
import com.example.grocerystore.R;
import com.example.grocerystore.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.grocerystore.Fragments.SecondCartFragment.ORDER_KEY;

public class PaymentResultFragment extends Fragment {

	private TextView paymentMessage;
	private Button homeButton;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_payment_result, container, false);

		initViews(view);

		Bundle bundle = getArguments();
		if (null != bundle) {
			String jsonOrder = bundle.getString(ORDER_KEY);
			if (null != jsonOrder) {
				Gson gson = new Gson();
				Type type = new TypeToken<Order>(){}.getType();

				Order order = gson.fromJson(jsonOrder, type);
				if (null != order) {
					if (order.isSuccessful()) {
						paymentMessage.setText(getString(R.string.payment_successful));
						//Clear cart after purchase
						Utils.clearCartItems(getContext());
						//Update bought items popularity points
						for (CartItem item : order.getCartItems()) {
							Utils.updatePopularityPoints(getContext(), item.getItem(), item.getAmount());
						}
					} else {
						paymentMessage.setText(getString(R.string.payment_failed));
					}
				}
			} else {
				// If response from retrofit is not what we are expecting (Order)
				paymentMessage.setText(getString(R.string.payment_failed));
			}
		}

		homeButton.setOnClickListener(v -> {
			Intent intent = new Intent(getContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		});

		return view;
	}

	private void initViews(View view) {
		paymentMessage = view.findViewById(R.id.payment_result_text_view);
		homeButton = view.findViewById(R.id.home_button);
	}
}
