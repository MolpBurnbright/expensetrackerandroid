package com.kapirawan.financial_tracker.ui.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;
import com.kapirawan.financial_tracker.roomdatabase.preference.Preference;

import java.util.List;

public class CategoryFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private long userId;
    private LiveData<Preference> selectedAccount;
    private LiveData<List<Category>> categories;
    private LiveData<Account> account;

    public CategoryFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void initAccount(long accountId, long accountDatasourceId) {
        categories = repo.readAccountCategories(accountId, accountDatasourceId);
        account = repo.readAccount(accountId, accountDatasourceId);
    }

    public void initUserId(long userId){
        this.userId = userId;
    }

    public LiveData<Account> getAccount(){
        return account;
    }
    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<Preference> getSelectedAccount(){
        if(selectedAccount == null)
            selectedAccount = repo.getSelectedAccount(userId);
        return selectedAccount;
    }
}