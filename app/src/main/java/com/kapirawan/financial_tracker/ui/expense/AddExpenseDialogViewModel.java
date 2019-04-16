package com.kapirawan.financial_tracker.ui.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddExpenseDialogViewModel extends AndroidViewModel {
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
    private int selectedCategoryPosition;

    public AddExpenseDialogViewModel(@NonNull Application app) {
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
        this.categories = repo.readAccountCategories(accountId, accountDatasourceId);
        this.details = repo.getDetails(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
    }

    public void initDatasourceId(long datasourceId){
        this.datasourceId = datasourceId;
    }

    public void addExpense(){
        Expense expense = new Expense(0, this.datasourceId, accountId, accountDatasourceId,
                selectedDate, amount, getSelectedCategory(), description, new Date());
        repo.createExpense(expense, () -> {});
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getAccountDatasourceId(){
        return this.accountDatasourceId;
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

    public Date getSelectedDate(){
        return this.selectedDate;
    }

    public String getSelectedCategory(){
        return this.categories.getValue().get(this.selectedCategoryPosition).name;
    }

    public int getSelectedCategoryPosition(){
        return this.selectedCategoryPosition;
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