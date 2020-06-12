package com.example.grocerystore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.grocerystore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {

	private BottomNavigationView bottomNavigationView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		initViews(view);
		bottomNavigationView.setSelectedItemId(R.id.home);
		initBottomNavigationView();

		return view;
	}

	private void initViews(View view) {
		bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
	}

	private void initBottomNavigationView() {
		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.search) {
				Toast.makeText(getActivity(), "Search selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.home) {
				Toast.makeText(getActivity(), "Home selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.cart) {
				Toast.makeText(getActivity(), "Cart selected", Toast.LENGTH_SHORT).show();
			}

			return false;
		});
	}
}
