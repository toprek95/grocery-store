package com.example.grocerystore.Models;

import com.example.grocerystore.Utils;

import java.util.ArrayList;

public class Order {

	private int id;
	private ArrayList<CartItem> cartItems;
	private double totalPrice;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String streetAddress;
	private String city;
	private String zipCode;
	private String paymentMethod;
	private boolean isSuccessful;

	public Order(ArrayList<CartItem> cartItems, double totalPrice, String firstName, String lastName, String phoneNumber, String email, String streetAddress, String city, String zipCode, String paymentMethod, boolean isSuccessful) {
		this.id = Utils.getOrderID();
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.paymentMethod = paymentMethod;
		this.isSuccessful = isSuccessful;
	}

	public Order() {
		this.id = Utils.getOrderID();
	}

	public int getId() {
		return id;
	}

	public ArrayList<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean successful) {
		isSuccessful = successful;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", cartItems=" + cartItems +
				", totalPrice=" + totalPrice +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", streetAddress='" + streetAddress + '\'' +
				", city='" + city + '\'' +
				", zipCode='" + zipCode + '\'' +
				", paymentMethod='" + paymentMethod + '\'' +
				", isSuccessful=" + isSuccessful +
				'}';
	}
}
