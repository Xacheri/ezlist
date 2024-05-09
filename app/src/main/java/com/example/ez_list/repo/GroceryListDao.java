package com.example.ez_list.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ez_list.data.GroceryList;

import java.util.List;

@Dao
public interface GroceryListDao {

   @Query("SELECT * FROM GroceryList")
   LiveData<List<GroceryList>> getAllLists();

   @Insert
   void insertList(GroceryList groceryList);

   @Update
   void updateList(GroceryList groceryList);

   @Delete
   void deleteList(GroceryList groceryList);


}

