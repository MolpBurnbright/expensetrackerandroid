package com.kapirawan.financial_tracker.expense;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.summary.Summary;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewModelAddExpense extends AndroidViewModel {
    private AppRepository repo;
    private long accountId, accountDatasourceId;
    private LiveData<List<Category>> categories;
    private Date selectedDate;
    private String selectedCategory;
    private int selectedCategoryPosition;

    public ViewModelAddExpense(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId){
        if(this.categories != null){
            return;
        }
        this.categories = new MutableLiveData<>();
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        setAccount(accountId, accountDatasourceId);
        this.selectedCategoryPosition = 0;
        this.selectedDate = Calendar.getInstance().getTime();
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
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }


    public void setSelectedDate(Date date){
        this.selectedDate = date;
    }

    public int getSelectedCategoryPosition(){
        return this.selectedCategoryPosition;
    }

    public void setSelectedCategoryPosition(int pos){
        this.selectedCategoryPosition = pos;
    }
}
