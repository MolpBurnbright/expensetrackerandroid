package com.kapirawan.financial_tracker.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddExpenseDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long accountId, accountDatasourceId;
    private Date selectedDate;
    private String category;
    private double amount;
    private String description;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> details;
    private int selectedCategoryPosition;

    public AddExpenseDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        this.amount = 0;
        this.description = "";
        if(this.categories == null)
            this.categories = new MutableLiveData<>();
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.selectedCategoryPosition = 0;
        this.selectedDate = Calendar.getInstance().getTime();
        setAccount(accountId, accountDatasourceId);
    }

    public void addExpense(){
        Expense expense = new Expense(0, 0, accountId, accountDatasourceId,
                selectedDate, amount, getSelectedCategory(), description, new Date());
        repo.createExpense(expense, () -> {});
    }

    public LiveData<List<Category>> getCategories() {
        return this.categories;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public long getAccountDatasourceId(){
        return this.accountDatasourceId;
    }

    public void setAccount(long accountId, long accountDatasourceId){
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        repo.readAccountCategory(accountId, accountDatasourceId, categs -> {
            ((MutableLiveData<List<Category>>)this.categories).setValue(categs);
        });
        this.details = repo.getDetails(accountId, accountDatasourceId);
    }

    public double getAmount(){
        return this.amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }

    public void setSelectedDate(Date date){
        this.selectedDate = date;
    }

    public String getSelectedCategory(){
        return this.categories.getValue().get(this.selectedCategoryPosition).name;
    }

    public int getSelectedCategoryPosition(){
        return this.selectedCategoryPosition;
    }

    public void setSelectedCategoryPosition(int pos){
        this.selectedCategoryPosition = pos;
    }
}