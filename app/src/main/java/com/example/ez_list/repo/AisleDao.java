package com.example.ez_list.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ez_list.data.Aisle;

import java.util.List;

@Dao
public interface AisleDao {
    @Query("SELECT * FROM Aisle WHERE store_id = :store_id")
    LiveData<List<Aisle>> getAislesFromStore(long store_id);

    @Update
    int updateAisles(List<Aisle> aisles);

    @Delete
    void deleteAisle(Aisle aisle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAisle(Aisle aisle);
}
