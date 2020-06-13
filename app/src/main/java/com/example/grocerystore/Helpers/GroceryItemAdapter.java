package com.example.grocerystore.Helpers;

import android.content.Context;
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
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.MyViewHolder> {

	private ArrayList<GroceryItem> allGroceryItems = new ArrayList<>();
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
		GroceryItem groceryItem = allGroceryItems.get(position);

		holder.groceryItemPrice.setText(groceryItem.getPrice() + " $");
		holder.groceryItemName.setText(groceryItem.getName());
		Glide.with(context)
				.asBitmap()
				.load(Uri.parse(groceryItem.getImageUrl()))
				.into(holder.groceryItemImage);

		holder.groceryItemParent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, groceryItem.getName() + " selected", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public int getItemCount() {
		return allGroceryItems.size();
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
