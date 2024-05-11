package com.example.ez_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.repo.GroceryRepository;

import java.util.List;

public class GroceryListCollectionViewModel extends AndroidViewModel {
    private final GroceryRepository repo;

    private LiveData<List<GroceryList>> lists;

    public GroceryListCollectionViewModel(@NonNull Application app) {
        super(app);
        repo = GroceryRepository.getInstance(app.getApplicationContext());

        lists = repo.getAllLists();
    }

    public LiveData<List<GroceryList>> getLists() {
        return lists;
    }

    public void addList(GroceryList list) {
        repo.insertList(list);
    }

    public void deleteList(GroceryList list) {
        repo.deleteList(list);
    }

}
