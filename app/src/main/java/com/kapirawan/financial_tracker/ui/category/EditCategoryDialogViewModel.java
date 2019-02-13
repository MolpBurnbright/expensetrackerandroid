package com.kapirawan.financial_tracker.ui.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.util.List;

public class EditCategoryDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Category category;
    private LiveData<List<Category>> categories;

    public EditCategoryDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Category category) {
        this.category = category;
        this.categories = repo.readAccountCategories(category.accountId, category.accountDatasourceId);
    }

    public LiveData<List<Category>> getCategories(){
        return categories;
    }

    public String getCategoryName(){
        return category.name;
    }

    public void updateCategory(String name){
        category.name = name;
        repo.updateCategory(category, () -> {});
    }
}