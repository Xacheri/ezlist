package com.example.ez_list.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ez_list.model.Grocery;

import java.util.List;

@Dao
public interface GroceryDao {
    @Query("SELECT * FROM Grocery WHERE aisle_id = :aisle_id")
    LiveData<List<Grocery>> getGroceriesInAisle(long aisle_id);

    @Query("SELECT Grocery.* FROM Grocery INNER JOIN Aisle ON Grocery.aisle_id = Aisle.id " +
            "INNER JOIN Store ON Aisle.store_id = Store.id " +
            "WHERE Store.name = :store_name and Grocery.name IN (:names) ORDER BY Aisle.`index`")
    LiveData<List<Grocery>> getGroceriesInOrder(List<String> names, String store_name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addGroceryItem(Grocery item);

    @Update
    void updateGroceryItem(Grocery item);

    @Delete
    void deleteGroceryItem(Grocery item);
}
