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

public class EditExpenseDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long _id, datasourceId;
    private long accountId, accountDatasourceId;
    private Date selectedDate;
    private String category;
    private double amount;
    private String description;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> details;
    private int selectedCategoryPosition;

    public EditExpenseDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Expense expense){
        this._id = expense._id;
        this.datasourceId = expense.datasourceId;
        this.accountId = expense.accountId;
        this.accountDatasourceId = expense.accountDatasourceId;
        this.selectedDate = expense.date;
        this.amount = expense.amount;
        this.category = expense.type;
        this.description = expense.details;
        setAccount(accountId, accountDatasourceId);
    }

    public void updateExpense(){
        Expense expense = new Expense(_id, datasourceId, accountId, accountDatasourceId,
                selectedDate, amount, category, description, new Date());
        repo.updateExpense(expense, () -> {});
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
        if(this.categories == null)
            this.categories = new MutableLiveData<>();
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

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
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
