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
    private LiveData<List<Category>> categories;
    private long accountId, accountDatasourceId;

    public AddCategoryDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(long accountId, long accountDatasourceId) {
        this.accountId = accountId;
        this.accountDatasourceId = accountDatasourceId;
        this.categories = repo.readAccountCategories(accountId, accountDatasourceId);
    }

    public void addCategory(String name){
        repo.createCategory(new Category(0, 0, accountId, accountDatasourceId,
                name, new Date()), () ->{});
    }

    public LiveData<List<Category>> getCategories(){
        return categories;
    }

    public LiveData<Preference> getSelectedAccount(){
        return repo.getSelectedAccount();
    }
}