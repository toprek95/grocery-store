package com.example.grocerystore.Helpers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.R;
import com.example.grocerystore.Utils;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.MyViewHolder> {

	private ArrayList<String> categories;
	private ArrayList<Boolean> isCategorySelected = new ArrayList<>();
	private Context context;

	public AllCategoriesAdapter(Context context) {
		this.context = context;
		this.categories = Utils.getAllCategories(context);
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		String categoryName = categories.get(position);
		final boolean[] isSelected = {isCategorySelected.get(position)};
		final ColorStateList originalTextViewColor = holder.categoryItemName.getTextColors();

		holder.categoryItemName.setText(categoryName);
		if (isSelected[0]) {
			holder.categoryItemName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
		} else {
			holder.categoryItemName.setTextColor(originalTextViewColor);
		}

		holder.categoryItemName.setOnClickListener(v -> {
			isSelected[0] = isCategorySelected.get(position);
			if (isSelected[0]) {
				holder.categoryItemName.setTextColor(originalTextViewColor);
				isCategorySelected.set(position, false);
			} else {
				holder.categoryItemName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
				isCategorySelected.set(position, true);
			}
		});
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	public void setIsCategorySelected(ArrayList<Boolean> isCategorySelected) {
		this.isCategorySelected = isCategorySelected;
		notifyDataSetChanged();
	}

	public ArrayList<String> getSelectedCategories() {
		ArrayList<String> newSelectedCategories = new ArrayList<>();
		for (int i = 0; i < categories.size(); i++) {
			if (isCategorySelected.get(i)) {
				newSelectedCategories.add(categories.get(i).toLowerCase());
			}
		}
		return newSelectedCategories;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {
		private TextView categoryItemName;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			categoryItemName = itemView.findViewById(R.id.category_item_text);
		}
	}
}
