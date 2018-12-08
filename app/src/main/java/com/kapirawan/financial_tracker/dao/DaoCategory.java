package com.kapirawan.financial_tracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kapirawan.financial_tracker.entity.Category;

import java.util.List;

@Dao
public interface DaoCategory {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Category[] categories);

    @Query("SELECT * from category")
    LiveData<List<Category>> getAllCategoriess();

    @Query("SELECT * from category where accountId = :accountId")
    LiveData<List<Category>> getAccountCategories(long accountId);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);
    
}
