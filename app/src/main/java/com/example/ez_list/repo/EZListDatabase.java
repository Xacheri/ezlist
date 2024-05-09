package com.example.ez_list.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.data.Store;

@Database(entities = {Store.class, Grocery.class, Aisle.class, GroceryList.class}, version = 1)
public abstract class EZListDatabase extends RoomDatabase {
    public abstract AisleDao aisleDao();
    public abstract GroceryDao groceryDao();

    public abstract GroceryListDao listsDao();
}
