package com.test.roompagingtest.model;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TestEntityDao {

    @Query("SELECT * FROM TestEntity")
    DataSource.Factory<Integer, TestEntity> getTestEntities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTestEntities(List<TestEntity> entities);

    @Query("SELECT MAX(mIndexInResponse) FROM TestEntity")
    int getMaxLoadedIndex();

    @Query("DELETE FROM TestEntity")
    void deleteTestEntities();

}
