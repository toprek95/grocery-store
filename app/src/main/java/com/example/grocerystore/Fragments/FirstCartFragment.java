package com.example.grocerystore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.Helpers.CartItemsAdapter;
import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.R;
import com.example.grocerystore.Utils;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment {

	private RelativeLayout cartItemsContainer;
	private Button nextButton;
	private RecyclerView cartItemsRecyclerView;
	private TextView totalPrice, emptyCartLabel;
	private CartItemsAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cart_first, container, false);

		initViews(view);

		adapter = new CartItemsAdapter(getContext(), getActivity());
		cartItemsRecyclerView.setAdapter(adapter);
		cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		initCartItems();

		nextButton.setOnClickListener(v -> {
			// TODO: 19-Jun-2020 Navigate user to second cart fragment
			Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
		});

		return view;
	}

	private void initCartItems() {
		ArrayList<CartItem> cartItems = Utils.getCartItems(getContext());
		if (null != cartItems) {
			if (cartItems.size() > 0) {
				totalPrice.setText(getTotalPrice(cartItems) + " USD");
				adapter.setCartItems(cartItems);
				cartItemsContainer.setVisibility(View.VISIBLE);
				emptyCartLabel.setVisibility(View.GONE);
			} else {
				cartItemsContainer.setVisibility(View.GONE);
				emptyCartLabel.setVisibility(View.VISIBLE);
			}
		} else {
			cartItemsContainer.setVisibility(View.GONE);
			emptyCartLabel.setVisibility(View.VISIBLE);
		}
	}

	private double getTotalPrice(ArrayList<CartItem> cartItems) {
		double price = 0;
		for (CartItem item : cartItems) {
			price += item.getTotalPrice();
		}
		return price;
	}

	private void initViews(View view) {
		cartItemsRecyclerView = view.findViewById(R.id.cart_items_recycler_view);
		totalPrice = view.findViewById(R.id.total_price_text_view);
		nextButton = view.findViewById(R.id.cart_items_next_button);
		cartItemsContainer = view.findViewById(R.id.cart_items_container);
		emptyCartLabel = view.findViewById(R.id.empty_cart_label);
	}
}
