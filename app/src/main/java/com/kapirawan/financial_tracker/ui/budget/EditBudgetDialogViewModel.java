package com.kapirawan.financial_tracker.ui.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;

import java.util.Date;
import java.util.List;

public class EditBudgetDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Budget budget;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> details;

    public EditBudgetDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Budget budget){
        this.budget = budget;
        if(this.categories == null)
            this.categories = new MutableLiveData<>();
        repo.readAccountCategories(budget.accountId, budget.accountDatasourceId, categs -> {
            ((MutableLiveData<List<Category>>)this.categories).setValue(categs);
        });
        this.details = repo.getDetails(budget.accountId, budget.accountDatasourceId);
    }

    public void updateBudget(){
        repo.updateBudget(this.budget, () -> {});
    }

    public LiveData<List<Category>> getCategories() {
        return this.categories;
    }

    public double getAmount(){
        return this.budget.amount;
    }

    public void setAmount(double amount){
        this.budget.amount = amount;
    }

    public LiveData<List<String>> getDetails(){
        return this.details;
    }

    public String getCategory(){
        return this.budget.type;
    }

    public void setCategory(String category){
        this.budget.type = category;
    }

    public String getDescription(){
        return this.budget.details;
    }

    public void setDescription(String description){
        this.budget.details = description;
    }

    public Date getDate(){
        return this.budget.date;
    }

    public void setDate(Date date){
        this.budget.date = date;
    }
}
