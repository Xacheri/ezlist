package com.example.ez_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.repo.GroceryRepository;

public class GroceryItemViewModel extends AndroidViewModel {
    private GroceryRepository repo;

    public GroceryItemViewModel(@NonNull Application app) {
        super(app);
        repo = GroceryRepository.getInstance(app.getApplicationContext());
    }

    public void deleteGrocery(Grocery g) {
        repo.deleteGroceryItem(g);
    }
}
