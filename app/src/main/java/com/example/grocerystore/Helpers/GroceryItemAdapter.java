package com.example.grocerystore.Helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.GroceryItemActivity;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.grocerystore.GroceryItemActivity.GROCERY_ITEM_ID;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.MyViewHolder> {

	private ArrayList<GroceryItem> groceryItems = new ArrayList<>();
	private Context context;

	public GroceryItemAdapter(Context context) {
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		GroceryItem groceryItem = groceryItems.get(position);

		holder.groceryItemPrice.setText(groceryItem.getPrice() + " $");
		holder.groceryItemName.setText(groceryItem.getName());
		Glide.with(context)
				.asBitmap()
				.load(Uri.parse(groceryItem.getImageUrl()))
				.into(holder.groceryItemImage);

		holder.groceryItemParent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent groceryItemIntent = new Intent(context, GroceryItemActivity.class);
				groceryItemIntent.putExtra(GROCERY_ITEM_ID, groceryItem.getId());
				context.startActivity(groceryItemIntent);
			}
		});

	}

	@Override
	public int getItemCount() {
		return groceryItems.size();
	}

	public void setGroceryItems(ArrayList<GroceryItem> groceryItems) {
		this.groceryItems = groceryItems;
		notifyDataSetChanged();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView groceryItemPrice, groceryItemName;
		private ImageView groceryItemImage;
		private MaterialCardView groceryItemParent;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			groceryItemImage = itemView.findViewById(R.id.grocery_item_image);
			groceryItemName = itemView.findViewById(R.id.grocery_item_name);
			groceryItemPrice = itemView.findViewById(R.id.grocery_item_price);
			groceryItemParent = itemView.findViewById(R.id.grocery_item_parent);
		}
	}
}
