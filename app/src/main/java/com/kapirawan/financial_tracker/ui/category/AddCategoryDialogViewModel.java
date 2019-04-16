package com.kapirawan.financial_tracker.ui.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.Date;
import java.util.List;

public class AddCategoryDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<Preference> selectedAccount;
    private LiveData<List<Category>> categories;
    private long userId;
    private long datasourceId;
    private long accountId, accountDatasourceId;

    public AddCategoryDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void initAccount(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.selectedAccount = repo.getSelectedAccount(userId);
        this.categories = repo.readAccountCategories(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
    }

    public void initDatasourceId(long datasourceId){
        this.datasourceId = datasourceId;
    }

    public void addCategory(String name){
        repo.createCategory(new Category(0, datasourceId, accountId, accountDatasourceId,
                name, new Date()), () ->{});
    }

    public LiveData<List<Category>> getCategories(){
        return categories;
    }

    public LiveData<Preference> getSelectedAccount(){
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount(userId);
        return selectedAccount;
    }
}