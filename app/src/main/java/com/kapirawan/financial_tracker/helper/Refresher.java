package com.kapirawan.financial_tracker.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.AppRoomDatabase;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.entity.Budget;
import com.kapirawan.financial_tracker.entity.Category;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.entity.Fund;
import com.kapirawan.financial_tracker.entity.Source;
import com.kapirawan.financial_tracker.model.User;
import com.kapirawan.financial_tracker.webservice.RetrofitClient;
import com.kapirawan.financial_tracker.webservice.WebServiceAPIAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Refresher {

    private WebServiceAPIAccount webServiceAPI;
    private AppRoomDatabase db;
    private Application app;

    public Refresher(Application app){
        this.app = app;
        db = AppRoomDatabase.getDatabase(app);
        webServiceAPI = RetrofitClient.getRetrofitInstance().create(WebServiceAPIAccount.class);
    }

    public void refresh() {
        Log.i("Refresher", "Refresher was invoked");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(app);
        String userJSON = sharedPref.getString(app.getResources().getString(R.string.PREF_USER), "");
        if (userJSON != "") {
            User user = new Gson().fromJson(userJSON, User.class);
            RetrofitClient.setToken(user.getToken());
            refreshGetAccounts(user.get_id());
        }else{
            Toast.makeText(app, "You are not logged in",Toast.LENGTH_LONG);
        }
    }

    private void refreshGetAccounts(long userId){
        Call<Account[]> call = webServiceAPI.getAccounts(userId);
        call.enqueue(new Callback<Account[]>() {
            @Override
            public void onResponse(Call<Account[]> call, Response<Account[]> response) {
                if(response.code() == 200) {
                    Account[] accounts = response.body();
                    Log.i("Refresher", "Accounts retrieved successfully");
                    Log.i("Refresher", "Number of Accounts: " + accounts.length);
/*                    new AsyncAccountMultipleInsert(db.daoAccount()).execute(accounts);
                    for(Account account: accounts){
                        getExpenses(account._id);
                        getBudgets(account._id);
                        getFunds(account._id);
                        getCategories(account._id);
                        getSources(account._id);

                    }
*/
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
//                    new AsyncExpenseMultipleInsert(db.daoExpense()).execute(expenses);
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

    private void getBudgets(final long accountId){
        Call<Budget[]> call = webServiceAPI.getBudgets(accountId);
        call.enqueue(new Callback<Budget[]>() {
            @Override
            public void onResponse(Call<Budget[]> call, Response<Budget[]> response) {
                if(response.code() == 200){
                    Budget[] budgets = response.body();
                    new AsyncBudgetMultipleInsert(db.daoBudget()).execute(budgets);
                    Log.i("Refresher", "Budgets retrieved successfully for account " + accountId);
                    Log.i("Refresher", "Number of Budgets: " + budgets.length);
                } else {
                    Log.e("Refresher", "Connection failed on retrieving budgets for account "
                            + accountId + ", response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Budget[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving budgets for account " + accountId + t.getMessage());
            }
        });
    }

    private void getFunds(final long accountId){
        Call<Fund[]> call = webServiceAPI.getFunds(accountId);
        call.enqueue(new Callback<Fund[]>() {
            @Override
            public void onResponse(Call<Fund[]> call, Response<Fund[]> response) {
                if(response.code() == 200){
                    Fund[] funds = response.body();
                    new AsyncFundMultipleInsert(db.daoFund()).execute(funds);
                    Log.i("Refresher", "Funds retrieved successfully for account " + accountId);
                    Log.i("Refresher", "Number of Funds: " + funds.length);
                } else {
                    Log.e("Refresher", "Connection failed on retrieving funds for account "
                            + accountId + ", response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Fund[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving funds for account " + accountId + t.getMessage());
            }
        });
    }

    private void getCategories(final long accountId){
        Call<Category[]> call = webServiceAPI.getCategories(accountId);
        call.enqueue(new Callback<Category[]>() {
            @Override
            public void onResponse(Call<Category[]> call, Response<Category[]> response) {
                if(response.code() == 200){
                    Category[] budgets = response.body();
                    new AsyncCategoryMultipleInsert(db.daoCategory()).execute(budgets);
                    Log.i("Refresher", "Categories retrieved successfully for account " + accountId);
                    Log.i("Refresher", "Number of Categories: " + budgets.length);
                } else {
                    Log.e("Refresher", "Connection failed on retrieving categories for account "
                            + accountId + ", response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Category[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving categorys for account " + accountId + t.getMessage());
            }
        });
    }

    private void getSources(final long accountId){
        Call<Source[]> call = webServiceAPI.getSources(accountId);
        call.enqueue(new Callback<Source[]>() {
            @Override
            public void onResponse(Call<Source[]> call, Response<Source[]> response) {
                if(response.code() == 200){
                    Source[] budgets = response.body();
                    new AsyncSourceMultipleInsert(db.daoSource()).execute(budgets);
                    Log.i("Refresher", "Sources retrieved successfully for account " + accountId);
                    Log.i("Refresher", "Number of Sources: " + budgets.length);
                } else {
                    Log.e("Refresher", "Connection failed on retrieving Sources for account "
                            + accountId + ", response code: " + response.code());
                    Log.e("Refresher", "Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Source[]> call, Throwable t) {
                Log.i("Refresher", "Call failed in retrieving sources for account " + accountId + t.getMessage());
            }
        });
    }
} 