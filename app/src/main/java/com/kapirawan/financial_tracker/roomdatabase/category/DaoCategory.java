package com.kapirawan.financial_tracker.roomdatabase.category;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.kapirawan.financial_tracker.roomdatabase.DaoBase;

import java.util.List;

@Dao
public interface DaoCategory extends DaoBase<Category> {

    @Query("select * from category where _id = :categoryId and datasourceId = :datasourceId")
    Category getCategory(long categoryId, long datasourceId);

    @Query("select * from category where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    List<Category> getAccountCategories(long accountId, long accountDatasourceId);

    @Query("select * from category where accountId = :accountId " +
            "and accountDatasourceId = :accountDatasourceId")
    LiveData<List<Category>> getAccountCategoriesLD(long accountId, long accountDatasourceId);

    @Query("select * from category")
    List<Category> getAllCategories();

    @Query("select MAX(_id) from category where datasourceId = :datasourceId")
    long getMaxId(long datasourceId);

    @Query("delete from category")
    void deleteAllCategories();
}