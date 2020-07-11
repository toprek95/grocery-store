package com.example.grocerystore;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

	private static final String DB_NAME = "fake_database";
	private static final String ALL_ITEMS_KEY = "all_items";
	private static final String CART_ITEMS_KEY = "cart_items";
	private static int ID = 0;
	private static int ORDER_ID = 0;
	private static Gson gson = new Gson();
	private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
	}.getType();
	private static Type cartItemsListType = new TypeToken<ArrayList<CartItem>>() {
	}.getType();


	public static void initSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
		ArrayList<CartItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), cartItemsListType);

		if (null == allItems) {
			initAllItems(context);
		}

		if (null == cartItems) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			cartItems = new ArrayList<>();
			editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
			editor.commit();
		}
	}

	private static void initAllItems(Context context) {
		ArrayList<GroceryItem> allItems = new ArrayList<>();
		allItems.add(new GroceryItem("Milk",
				"Milk is a white, nutrient-rich liquid food produced in the mammary glands of mammals. It is the primary source of nutrition for infant mammals before they are able to digest other types of food.",
				"https://www.frieslandcampinainstitute.com/uploads/sites/4/2017/07/what-is-the-difference-between-full-fat-and-skimmed-milk-3.jpg",
				"drink", 3.99, 150
		));

		GroceryItem cocaCola = new GroceryItem("Coca cola",
				"Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine, it was invented in the late 19th century by John Stith Pemberton",
				"https://m.vecernji.hr/media/img/ed/42/1fd6c7e076f936b7a4cc.jpeg",
				"drink", 1.99, 50);
		cocaCola.setPopularityPoints(4);
		cocaCola.setUserPoints(14);
		allItems.add(cocaCola);

		GroceryItem iceCream = new GroceryItem("Ice cream",
				"Ice cream is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavoured with a sweetener, either sugar or an alternative, and any spice, such as cocoa or vanilla.",
				"https://bitzngiggles.com/wp-content/uploads/2020/02/Rainbow-Ice-Cream-14-copy.jpg",
				"food", 9.99, 250);
		iceCream.setPopularityPoints(10);
		iceCream.setUserPoints(7);
		allItems.add(iceCream);


		allItems.add(new GroceryItem("Pepsi",
				"Pepsi is a carbonated soft drink manufactured by PepsiCo. Originally created and developed in 1893 by Caleb Bradham and introduced as Brad's Drink, it was renamed as Pepsi-Cola in 1898, and then shortened to Pepsi in 1961.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT2ENZ3B41dH0UuHg1hI_6JIdVEokUisbKTn5GFYhqqM1YH2NnN&usqp=CAU",
				"drink", 1.98, 150
		));

		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
		editor.commit();
	}

	public static int getID() {
		return ++ID;
	}

	public static int getOrderID() {
		return ++ORDER_ID;
	}

	public static ArrayList<GroceryItem> getAllItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		return gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
	}

	public static GroceryItem getGroceryItemById(Context context, int id) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (allItems != null) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == id) {
					return groceryItem;
				}
			}
		}
		return null;
	}

	public static void addReview(Context context, Review review) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> allItems = getAllItems(context);

		if (null != allItems) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == review.getGroceryItemId()) {
					ArrayList<Review> reviews = groceryItem.getReviews();
					reviews.add(review);
					groceryItem.setReviews(reviews);
				}
			}
			editor.remove(ALL_ITEMS_KEY);
			editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
			editor.commit();
		}
	}

	public static ArrayList<Review> getReviewsByGroceryItemId(Context context, int id) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (null != allItems) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == id) {
					return groceryItem.getReviews();
				}
			}
		}
		return null;
	}

	public static ArrayList<String> getAllCategories(Context context) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (null != allItems) {
			ArrayList<String> categories = new ArrayList<>();
			for (GroceryItem item : allItems) {
				if (!categories.contains(item.getCategory().toLowerCase())) {
					categories.add(item.getCategory().toLowerCase());
				}
			}
			ArrayList<String> categoriesCapitalized = new ArrayList<>();
			for (String str : categories) {
				String s = str.substring(0, 1).toUpperCase() + str.substring(1);
				categoriesCapitalized.add(s);
			}
			return categoriesCapitalized;
		}
		return null;
	}

	public static ArrayList<CartItem> getCartItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		return gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), cartItemsListType);
	}

	public static void addCartItem(Context context, CartItem cartItem) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<CartItem> cartItems = getCartItems(context);
		ArrayList<CartItem> addedCartItems = new ArrayList<>();

		if (null != cartItems) {
			boolean isInCart = false;
			for (CartItem item : cartItems) {
				if (item.getItem().getId() == cartItem.getItem().getId()) {
					isInCart = true;
				}
			}
			if (isInCart) {
				for (CartItem item : cartItems) {
					if (item.getItem().getId() == cartItem.getItem().getId()) {
						int amount = item.getAmount() + cartItem.getAmount();
						addedCartItems.add(new CartItem(cartItem.getItem(), amount));
					} else {
						addedCartItems.add(item);
					}
				}
			} else {
				addedCartItems.addAll(cartItems);
				addedCartItems.add(cartItem);
			}

			updateGroceryItemAmount(context, cartItem.getItem(), cartItem.getAmount());
		}

		editor.remove(CART_ITEMS_KEY);
		editor.putString(CART_ITEMS_KEY, gson.toJson(addedCartItems));
		editor.commit();
	}

	public static void removeCartItem(Context context, CartItem cartItem) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<CartItem> cartItems = getCartItems(context);
		ArrayList<CartItem> addedCartItems = new ArrayList<>();

		if (null != cartItems) {
			for (CartItem item : cartItems) {
				if (item.getItem().getId() != cartItem.getItem().getId()) {
					addedCartItems.add(item);
				}
			}

			updateGroceryItemAmount(context, cartItem.getItem(), -cartItem.getAmount());
		}

		editor.remove(CART_ITEMS_KEY);
		editor.putString(CART_ITEMS_KEY, gson.toJson(addedCartItems));
		editor.commit();
	}

	public static void clearCartItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(CART_ITEMS_KEY);
		editor.commit();
	}

	public static void updatePopularityPoints(Context context, GroceryItem item, int points) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> allItems = getAllItems(context);
		ArrayList<GroceryItem> mewItems = new ArrayList<>();
		if (null != allItems) {
			for (GroceryItem i : allItems) {
				if (i.getId() == item.getId()) {
					i.setPopularityPoints(i.getPopularityPoints() + points);
				}
				mewItems.add(i);
			}
		}

		editor.remove(ALL_ITEMS_KEY);
		editor.putString(ALL_ITEMS_KEY, gson.toJson(mewItems));
		editor.commit();
	}

	private static void updateGroceryItemAmount(Context context, GroceryItem groceryItem, int amount) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> groceryItems = getAllItems(context);
		ArrayList<GroceryItem> editedItems = new ArrayList<>();
		if (null != groceryItems) {
			for (GroceryItem item : groceryItems) {
				if (item.getId() == groceryItem.getId()) {
					int newAmount = item.getAvailableAmount() - amount;
					item.setAvailableAmount(newAmount);
				}
				editedItems.add(item);
			}
		}

		editor.remove(ALL_ITEMS_KEY);
		editor.putString(ALL_ITEMS_KEY, gson.toJson(editedItems));
		editor.commit();
	}
}
