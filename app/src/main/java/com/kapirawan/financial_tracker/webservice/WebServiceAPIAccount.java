package com.kapirawan.financial_tracker.webservice;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.entity.Budget;
import com.kapirawan.financial_tracker.entity.Category;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.entity.Fund;
import com.kapirawan.financial_tracker.entity.Source;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface WebServiceAPIAccount {

    @GET("api/accounts/user/{userId}")
    Call<Account[]> getAccounts(@Path("userId") Long userId);

    @GET("api/expenses/account/{accountId}")
    Call<Expense[]> getExpenses(@Path("accountId") Long accountId);

    @GET("api/funds/account/{accountId}")
    Call<Fund[]> getFunds(@Path("accountId") Long accountId);

    @GET("api/budgets/account/{accountId}")
    Call<Budget[]> getBudgets(@Path("accountId") Long accountId);

    @GET("api/categories/account/{accountId}")
    Call<Category[]> getCategories(@Path("accountId") Long accountId);

    @GET("api/sources/account/{accountId}")
    Call<Source[]> getSources(@Path("accountId") Long accountId);
}