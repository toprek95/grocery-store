package com.example.grocerystore.Models;

import com.example.grocerystore.Utils;

import java.util.ArrayList;

public class GroceryItem {
	private int id;
	private String name;
	private String description;
	private String imageUrl;
	private String category;
	private double price;
	private int availableAmount;
	private int rating;
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
		this.rating = 0;
		this.userPoints = 0;
		this.popularityPoints = 0;
		reviews = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
	}

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
				", rating=" + rating +
				", userPoints=" + userPoints +
				", popularityPoints=" + popularityPoints +
				", reviews=" + reviews.toString() +
				'}';
	}
}
