package com.example.grocerystore.Helpers;

import com.example.grocerystore.Models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderEndpoint {

	@POST("posts")
	Call<Order> newOrder (@Body Order order);
}
