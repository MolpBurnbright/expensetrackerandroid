package com.kapirawan.financial_tracker.ui.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.util.List;

public class CategoryFragmentViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Account account;
    private LiveData<List<Category>> categories;

    public CategoryFragmentViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Account account) {
        this.account = account;
        categories = repo.readAccountCategories(account._id, account.datasourceId);
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }
}