package com.example.grocerystore.Models;

import com.example.grocerystore.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

public class GroceryItem {
	// Sorting comparators
	public static Comparator<GroceryItem> groceryItemIdComparator =
			(item1, item2) -> item1.getId() - item2.getId();

	public static Comparator<GroceryItem> groceryItemPopularityComparator =
			(item1, item2) -> item1.getPopularityPoints() - item2.getPopularityPoints();

	public static Comparator<GroceryItem> groceryItemSuggestedComparator =
			(item1, item2) -> item1.getUserPoints() - item2.getUserPoints();


	private int id;
	private String name;
	private String description;
	private String imageUrl;
	private String category;
	private double price;
	private int availableAmount;
	private float averageRating;
	private int userPoints;
	private int popularityPoints;
	private ArrayList<Review> reviews;

	public GroceryItem(String name, String description, String imageUrl, String category, double price, int availableAmount) {
		this.id = Utils.getID();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.category = category;
		this.price = price;
		this.availableAmount = availableAmount;
		this.averageRating = calculateAverageRating();
		this.userPoints = 0;
		this.popularityPoints = 0;
		reviews = new ArrayList<>();
	}

	private float calculateAverageRating() {
		float avg = 0;
		int n = reviews.size();
		for (Review r : reviews) {
			avg += r.getRating();
		}

		if (n != 0) {
			return (avg / n);
		} else {
			return  0;
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(int availableAmount) {
		this.availableAmount = availableAmount;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public int getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(int userPoints) {
		this.userPoints = userPoints;
	}

	public int getPopularityPoints() {
		return popularityPoints;
	}

	public void setPopularityPoints(int popularityPoints) {
		this.popularityPoints = popularityPoints;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
		// Change averageRating when new review is added
		this.averageRating = calculateAverageRating();
	}

	@NotNull
	@Override
	public String toString() {
		return "GroceryItem{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", category='" + category + '\'' +
				", price=" + price +
				", availableAmount=" + availableAmount +
				", rating=" + averageRating +
				", userPoints=" + userPoints +
				", popularityPoints=" + popularityPoints +
				", reviews=" + reviews.toString() +
				'}';
	}
}
