package com.example.ez_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.repo.GroceryRepository;

import java.util.List;

public class GroceriesViewModel extends AndroidViewModel {

    GroceryRepository repo;
    
    LiveData<List<Grocery>> allGroceries;

    Grocery thisItem;
    public GroceriesViewModel(@NonNull Application application) {
        super(application);
        repo = GroceryRepository.getInstance(application.getApplicationContext());
        allGroceries = repo.getAllGroceries();
    }

    public LiveData<List<Grocery>> getAllGroceries() {
        return allGroceries;
    }

    public void insertNewGrocery(){
        repo.addGroceryItem(thisItem);
    }

    public void setGrocery(Grocery g) {
        thisItem = g;
    }


    public void addNameToGrocery(String name) {
        thisItem = new Grocery();
        thisItem.mName = name;
    }

    public void addAisleToGrocery(String name) {
        boolean aisleExists = aisleExists(name);
        if(!aisleExists){
            Aisle a = new Aisle();
            a.mName = name;
            a.mIndex = repo.getMaxIndexForAisle() + 1;
            repo.insertAisle(a);
        }
        thisItem.mAisleName = name;
        thisItem.mAisleId = repo.getAisleIdByName(name);


        // finish up adding the grocery
        insertNewGrocery();
    }

    public Grocery getGroceryByName(String name) {
        return repo.getGroceryByName(name);
    }
    public boolean aisleExists(String groceryName) {
        Grocery result = repo.getGroceryByName(groceryName);
        if(result == null || result.mAisleId < 1) {
            return false;
        }
        return true;
    }

    public void deleteGroceryByName(String name) {
        repo.deleteGroceryItemsByName(name);
    }
}
