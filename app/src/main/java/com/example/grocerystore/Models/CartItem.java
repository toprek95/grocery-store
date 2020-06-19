package com.example.grocerystore.Models;

public class CartItem {

	private GroceryItem item;
	private int amount;
	private double totalPrice;

	public CartItem(GroceryItem item, int amount) {
		this.item = item;
		this.amount = amount;
		this.totalPrice = setTotalPrice();
	}

	public GroceryItem getItem() {
		return item;
	}

	public void setItem(GroceryItem item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	private double setTotalPrice() {
		double price = amount * item.getPrice();
		price = Math.round(price*100.0)/100.0;
		return price;
	}

	@Override
	public String toString() {
		return "CartItem{" +
				"item=" + item +
				", amount=" + amount +
				", totalPrice=" + totalPrice +
				'}';
	}
}
