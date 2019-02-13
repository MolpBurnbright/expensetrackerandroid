package com.kapirawan.financial_tracker.ui.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.kapirawan.financial_tracker.repository.AppRepository;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;


public class RemoveCategoryDialogViewModel extends AndroidViewModel {
    private AppRepository repo;
    private Category category;

    public RemoveCategoryDialogViewModel(@NonNull Application app) {
        super(app);
        repo = AppRepository.getInstance(app);
    }

    public void init(Category category) {
        this.category = category;
    }

    public String getCategoryName(){
        return category.name;
    }

    public void removeCategory(){
        repo.deleteCategory(category, () -> {});
    }
}