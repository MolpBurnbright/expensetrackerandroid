package com.kapirawan.financial_tracker.ui.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;

import java.util.Date;
import java.util.List;

public class EditExpenseDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Expense expense;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> details;

    public EditExpenseDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Expense expense){
        this.expense = expense;
        if(this.categories == null)
            this.categories = new MutableLiveData<>();
        repo.readAccountCategory(expense.accountId, expense.accountDatasourceId, categs -> {
            ((MutableLiveData<List<Category>>)this.categories).setValue(categs);
        });
        this.details = repo.getDetails(expense.accountId, expense.accountDatasourceId);
    }

    public void updateExpense(){
        repo.updateExpense(this.expense, () -> {});
    }

    public LiveData<List<Category>> getCategories() {
        return this.categories;
    }

    public double getAmount(){
        return this.expense.amount;
    }

    public void setAmount(double amount){
        this.expense.amount = amount;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public String getCategory(){
        return this.expense.type;
    }

    public void setCategory(String category){
        this.expense.type = category;
    }

    public String getDescription(){
        return this.expense.details;
    }

    public void setDescription(String description){
        this.expense.details = description;
    }

    public Date getDate(){
        return this.expense.date;
    }

    public void setDate(Date date){
        this.expense.date = date;
    }
}
