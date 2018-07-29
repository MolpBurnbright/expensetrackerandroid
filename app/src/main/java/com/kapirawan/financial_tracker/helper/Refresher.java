package com.kapirawan.financial_tracker.helper;

import android.util.Log;

import com.google.gson.Gson;
import com.kapirawan.financial_tracker.entity.Account;
import com.kapirawan.financial_tracker.entity.Expense;
import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.webservice.RetrofitClient;
import com.kapirawan.financial_tracker.webservice.WebServiceAPIAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Refresher {

    private WebServiceAPIAccount webServiceAPI;
    private AppRepository appRepository;
    private long userID;

    public Refresher(long userID, AppRepository repository){
        this.userID = userID;
        this.appRepository = repository;
        webServiceAPI = RetrofitClient.getRetrofitInstance().create(WebServiceAPIAccount.class);
    }

    public void refresh(){
        Call<Account[]> call = webServiceAPI.getAccounts(this.userID);
        call.enqueue(new Callback<Account[]>() {
            @Override
            public void onResponse(Call<Account[]> call, Response<Account[]> response) {
                if(response.code() == 200) {
                    Account[] accounts = response.body();
                    Log.i("Refresher", "Accounts retrieved successfully");
                    Log.i("Refresher", "Number of Accounts: " + accounts.length);
                    appRepository.insert(accounts);
                    for(Account account: accounts){
                        getExpenses(account._id);
                    }
                } else {
                    Log.e("Refresher", "Connection failed on retrieving accounts, response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Account[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving accounts:" + t.getMessage());
            }
        });
    }
    
    private void getExpenses(final long accountId){
        Call<Expense[]> call = webServiceAPI.getExpenses(accountId);
        call.enqueue(new Callback<Expense[]>() {
            @Override
            public void onResponse(Call<Expense[]> call, Response<Expense[]> response) {
                if(response.code() == 200){
                    Expense[] expenses = response.body();
                    Log.i("Refresher", "Expenses retrieved successfully for account " + accountId);
                    Log.i("Refresher", "Number of Expenses: " + expenses.length);
                } else {
                    Log.e("Refresher", "Connection failed on retrieving expenses for account "
                            + accountId + ", response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Expense[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving expenses for account " + accountId + t.getMessage());
            }
        });
    }
} 