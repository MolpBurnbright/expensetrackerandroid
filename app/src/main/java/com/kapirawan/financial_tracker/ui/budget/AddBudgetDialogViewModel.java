package com.kapirawan.financial_tracker.ui.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddBudgetDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long userId;
    private long datasourceId;
    private long accountId, accountDatasourceId;
    private Date selectedDate;
    private double amount;
    private String description;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> details;
    private LiveData<Preference> selectedAccount;
    private LiveData<Double> totalBudget, totalFund;
    private int selectedCategoryPosition;

    public AddBudgetDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        this.amount = 0;
        this.description = "";
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.selectedCategoryPosition = 0;
        this.selectedDate = Calendar.getInstance().getTime();
        this.details = repo.getDetails(accountId, accountDatasourceId);
        this.categories = repo.readAccountCategories(accountId, accountDatasourceId);
        this.totalBudget = repo.readTotalBudget(accountId, accountDatasourceId);
        this.totalFund = repo.readTotalFund(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
    }

    public void initDatasourceId(long datasourceId){
        this.datasourceId = datasourceId;
    }

    public void addBudget(){
        Budget budget = new Budget(0, datasourceId, accountId, accountDatasourceId,
                selectedDate, amount, getSelectedCategory(), description, new Date());
        repo.createBudget(budget, () -> {});
    }

    public double getAmount(){
        return this.amount;
    }

    public LiveData<List<Category>> getCategories() {
        return this.categories;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public LiveData<Preference> getSelectedAccount(){
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount(userId);
        return selectedAccount;
    }

    public String getSelectedCategory(){
        return this.categories.getValue().get(this.selectedCategoryPosition).name;
    }

    public int getSelectedCategoryPosition(){
        return this.selectedCategoryPosition;
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }

    public LiveData<Double> getTotalBudget(){
        return totalBudget;
    }

    public LiveData<Double> getTotalFund(){
        return totalFund;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setSelectedDate(Date date){
        this.selectedDate = date;
    }

    public void setSelectedCategoryPosition(int pos){
        this.selectedCategoryPosition = pos;
    }
}