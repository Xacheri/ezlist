package com.example.ez_list.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ez_list.model.Aisle;
import com.example.ez_list.model.Grocery;
import com.example.ez_list.model.Store;

@Database(entities = {Store.class, Grocery.class, Aisle.class}, version = 1)
public abstract class EZListDatabase extends RoomDatabase {
    public abstract AisleDao aisleDao();
    public abstract GroceryDao groceryDao();
}
