package com.example.grocerystore.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.GroceryItemActivity;
import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.grocerystore.GroceryItemActivity.GROCERY_ITEM_ID;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

	private ArrayList<CartItem> cartItems = new ArrayList<>();
	private Context mContext;
	private Activity mActivity;

	public CartItemsAdapter(Context mContext, Activity mActivity) {
		this.mContext = mContext;
		this.mActivity = mActivity;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		CartItem item = cartItems.get(position);

		holder.cartItemName.setText(item.getItem().getName());
		holder.amount.setText(String.valueOf(item.getAmount()));
		holder.price.setText(item.getTotalPrice() + " USD");

		Glide.with(mContext)
				.asBitmap()
				.load(Uri.parse(item.getItem().getImageUrl()))
				.into(holder.cartItemImage);

		holder.deleteCartItem.setOnClickListener(v -> {
			// TODO: 19-Jun-2020 Implement cart item removal
			Toast.makeText(mContext, "Delete clicked. Item: " + item.getItem().getName(), Toast.LENGTH_SHORT).show();
		});

		holder.cardItemContainer.setOnClickListener(v -> {
			Intent groceryItemIntent = new Intent(mContext, GroceryItemActivity.class);
			groceryItemIntent.putExtra(GROCERY_ITEM_ID, item.getItem().getId());
			mActivity.startActivity(groceryItemIntent);
		});
	}

	@Override
	public int getItemCount() {
		return cartItems.size();
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
		notifyDataSetChanged();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {
		private ImageView cartItemImage, deleteCartItem;
		private TextView cartItemName, amount, price;
		private MaterialCardView cardItemContainer;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			cartItemImage = itemView.findViewById(R.id.cart_item_image);
			deleteCartItem = itemView.findViewById(R.id.cart_item_delete);
			cartItemName = itemView.findViewById(R.id.cart_item_name);
			amount = itemView.findViewById(R.id.amount_text_view);
			price = itemView.findViewById(R.id.price_text_view);
			cardItemContainer = itemView.findViewById(R.id.cart_item_container);
		}
	}
}
