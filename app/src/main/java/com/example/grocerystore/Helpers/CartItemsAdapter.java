package com.example.grocerystore.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.GroceryItemActivity;
import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import static com.example.grocerystore.GroceryItemActivity.GROCERY_ITEM_ID;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

	private ArrayList<CartItem> cartItems = new ArrayList<>();
	private Context mContext;
	private Fragment mFragment;

	private RemoveCartItem removeCartItem;

	public CartItemsAdapter(Context mContext, Fragment mFragment) {
		this.mContext = mContext;
		this.mFragment = mFragment;
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
			MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
			builder.setIcon(R.drawable.ic_warning);
			builder.setTitle("Remove item from cart");
			builder.setMessage("Are you sure you want to remove this item from cart?");
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						removeCartItem = (RemoveCartItem) mFragment;
						removeCartItem.onRemoveCartItemResult(item);
					} catch (ClassCastException e) {
						e.printStackTrace();
					}
				}
			});
			builder.create().show();
		});

		holder.cardItemContainer.setOnClickListener(v -> {
			Intent groceryItemIntent = new Intent(mContext, GroceryItemActivity.class);
			groceryItemIntent.putExtra(GROCERY_ITEM_ID, item.getItem().getId());
			mFragment.startActivity(groceryItemIntent);
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

	public interface RemoveCartItem {
		void onRemoveCartItemResult (CartItem cartItem);
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
