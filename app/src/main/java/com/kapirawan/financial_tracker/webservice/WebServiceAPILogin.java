package com.kapirawan.financial_tracker.webservice;

import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.User;
import com.kapirawan.financial_tracker.entity.UserCredentials;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

public interface WebServiceAPILogin {

    @POST("/api/users/authenticate")
    Call<User> authenticateUser(@Body UserCredentials credentials);

    @GET("api/accounts/user/{userId}")
    Call<Account> getAccounts(@Path("userId") long userId);
}