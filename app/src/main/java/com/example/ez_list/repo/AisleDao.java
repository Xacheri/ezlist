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
    @Query("SELECT * FROM Aisle ORDER BY Aisle.`index`")
    LiveData<List<Aisle>> getAisles();

    @Query("SELECT MAX(Aisle.`index`) FROM Aisle")
    int getMaxIndexForAisle();

    @Query("SELECT Aisle.id FROM Aisle WHERE name = :name")
    int getAisleIdByName(String name);

    @Query("SELECT Aisle.* FROM Aisle WHERE name = :name")
    List<Aisle> getAisleByName(String name);
    @Update
    int updateAisles(List<Aisle> aisles);

    @Delete
    void deleteAisle(Aisle aisle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAisle(Aisle aisle);
}
