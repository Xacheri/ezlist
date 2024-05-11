package com.example.ez_list.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.ListItemJunction;

import java.util.List;

@Dao
public interface GroceryDao {
    @Query("SELECT * FROM Grocery WHERE aisle_id = :aisle_id")
    LiveData<List<Grocery>> getGroceriesInAisle(long aisle_id);

    @Query("SELECT * FROM Grocery WHERE name = :name LIMIT 1")
    Grocery getGroceryByName(String name);
    @Query("SELECT Grocery.* FROM Grocery INNER JOIN Aisle ON Grocery.aisle_id = Aisle.id " +
            "WHERE Grocery.name IN (:names) ORDER BY Aisle.`index`")
    LiveData<List<Grocery>> getGroceriesInOrder(List<String> names);

    @Query("SELECT Grocery.* FROM GroceryList " +
            "INNER JOIN ListItemJunction ON ListItemJunction.listId = GroceryList.id " +
            "INNER JOIN Grocery ON Grocery.id = ListItemJunction.groceryId " +
            "INNER JOIN Aisle ON Grocery.aisle_id = Aisle.id " +
            "WHERE GroceryList.name = :listName " +
            "ORDER BY Aisle.`index`")
    LiveData<List<Grocery>> getGroceriesInList(String listName);

    @Query("SELECT * FROM Grocery")
    LiveData<List<Grocery>> getAllGroceries();
    @Query("DELETE FROM Grocery WHERE name = :name")
    void deleteGroceryItemsByName(String name);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addGroceryItem(Grocery item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addListItemJunction(ListItemJunction ij);

    @Update
    void updateGroceryItem(Grocery item);

    @Delete
    void deleteGroceryItem(Grocery item);
}
