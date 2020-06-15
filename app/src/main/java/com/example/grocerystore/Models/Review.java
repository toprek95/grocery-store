package com.example.grocerystore.Models;

import org.jetbrains.annotations.NotNull;

public class Review {
	private int groceryItemId;
	private String userName;
	private String review;
	private String date;
	private float rating;

	public Review(int groceryItemId, String userName, String review, String date, float rating) {
		this.groceryItemId = groceryItemId;
		this.userName = userName;
		this.review = review;
		this.date = date;
		this.rating = rating;
	}

	public int getGroceryItemId() {
		return groceryItemId;
	}

	public void setGroceryItemId(int groceryItemId) {
		this.groceryItemId = groceryItemId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@NotNull
	@Override
	public String toString() {
		return "Review{" +
				"groceryItemId=" + groceryItemId +
				", userName='" + userName + '\'' +
				", review='" + review + '\'' +
				", date='" + date + '\'' +
				", rating=" + rating +
				'}';
	}
}
