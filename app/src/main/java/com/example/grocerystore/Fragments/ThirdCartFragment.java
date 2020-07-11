package com.example.grocerystore.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.Helpers.OrderEndpoint;
import com.example.grocerystore.Models.Order;
import com.example.grocerystore.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.grocerystore.Fragments.SecondCartFragment.ORDER_KEY;

public class ThirdCartFragment extends Fragment {

	private static final String TAG = "ThirdCartFragment";
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

			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
					.setLevel(HttpLoggingInterceptor.Level.BODY);

			OkHttpClient client = new OkHttpClient.Builder()
					.addInterceptor(interceptor)
					.build();

			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl("https://jsonplaceholder.typicode.com/")
					.addConverterFactory(GsonConverterFactory.create())
					.client(client)
					.build();

			OrderEndpoint endpoint = retrofit.create(OrderEndpoint.class);
			Call<Order> call = endpoint.newOrder(order);
			call.enqueue(new Callback<Order>() {
				@Override
				public void onResponse(Call<Order> call, Response<Order> response) {
					Log.d(TAG, "onResponse, Code: " + response.code());
					if (response.isSuccessful()) {
						Bundle responseBundle = new Bundle();
						Gson gson = new Gson();
						responseBundle.putString(ORDER_KEY, gson.toJson(response.body()));
						PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
						paymentResultFragment.setArguments(responseBundle);
						FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
						transaction.replace(R.id.cart_fragments_container, paymentResultFragment);
						transaction.commit();

					} else {
						// If response from retrofit is not what we are expecting (Order)
						FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
						transaction.replace(R.id.cart_fragments_container, new PaymentResultFragment());
						transaction.commit();
					}
				}

				@Override
				public void onFailure(Call<Order> call, Throwable t) {
					t.printStackTrace();
				}
			});
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
