package com.kapirawan.financial_tracker.webservice;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.Expense;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface WebServiceAPIAccount {

    @GET("api/accounts/user/{userId}")
    Call<Account[]> getAccounts(@Path("userId") Long userId);

    @GET("api/expenses/account/{accountId}")
    Call<Expense[]> getExpenses(@Path("accountId") Long accountId);
}