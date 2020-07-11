package com.example.grocerystore.Models;

import java.util.ArrayList;

public class Order {

	private int id;
	private ArrayList<CartItem> cartItems;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String streetAddress;
	private String city;
	private String zipCode;
	private boolean isSuccessful;

	public Order(ArrayList<CartItem> cartItems, String firstName, String lastName, String phoneNumber, String email, String streetAddress, String city, String zipCode, boolean isSuccessful) {
		this.cartItems = cartItems;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.isSuccessful = isSuccessful;
	}

	public Order() {
	}

	public ArrayList<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
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
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", streetAddress='" + streetAddress + '\'' +
				", city='" + city + '\'' +
				", zipCode='" + zipCode + '\'' +
				", isSuccessful=" + isSuccessful +
				'}';
	}
}
