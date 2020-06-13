package com.example.grocerystore;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.grocerystore.Fragments.MainFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private MaterialToolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();

		// Initialize shared preferences
		Utils.initSharedPreferences(this);

		// Set toolbar
		setSupportActionBar(toolbar);
		setToggleToolbar();

		// Navigation from menu
		onNavigationItemSelected();

		FragmentTransaction mainFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mainFragmentTransaction.replace(R.id.main_fragment_container, new MainFragment());
		mainFragmentTransaction.commit();
	}

	private void initViews() {
		drawerLayout = findViewById(R.id.drawer);
		navigationView = findViewById(R.id.navigation_view);
		toolbar = findViewById(R.id.toolbar);
	}

	private void setToggleToolbar() {
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				toolbar,
				R.string.drawer_open,
				R.string.drawer_close
		);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}

	private void onNavigationItemSelected() {
		navigationView.setNavigationItemSelectedListener(item -> {
			if (item.getItemId() == R.id.cart) {
				Toast.makeText(MainActivity.this, "Cart selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.categories) {
				Toast.makeText(MainActivity.this, "Categories selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.about) {
				Toast.makeText(MainActivity.this, "About us selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.terms) {
				Toast.makeText(MainActivity.this, "Terms selected", Toast.LENGTH_SHORT).show();
			}
			if (item.getItemId() == R.id.licences) {
				Toast.makeText(MainActivity.this, "Licences selected", Toast.LENGTH_SHORT).show();
			}
			return false;
		});
	}


}