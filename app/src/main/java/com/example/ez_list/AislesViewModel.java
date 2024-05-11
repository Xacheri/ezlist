package com.example.ez_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.repo.GroceryRepository;

import java.util.List;

public class AislesViewModel extends AndroidViewModel {

    GroceryRepository repo;

    LiveData<List<Aisle>> allAisles;


    public AislesViewModel(@NonNull Application application) {
        super(application);
        repo = GroceryRepository.getInstance(application.getApplicationContext());

        allAisles = repo.getAisles();
        if (allAisles == null) {
            firstAisle();
        }
    }

    public LiveData<List<Aisle>> getAisles() {
        return allAisles;
    }

    public void addAisle(String aisleName) {
        Aisle aisle = new Aisle();
        aisle.mName = aisleName;
        int maxIndex = repo.getMaxIndexForAisle();
        aisle.mIndex = maxIndex + 1;
        repo.insertAisle(aisle);
    }

    public void firstAisle() {
        Aisle firstAisle = new Aisle();
        firstAisle.mName = "Produce";
        firstAisle.mIndex = 1;
        if (repo.getAisleIdByName("Produce") != 1){
            repo.insertAisle(firstAisle);
        }
    }

    public void updateAisles(List<Aisle> aisles) {
        repo.updateAisles(aisles);
    }

    public void deleteAisle(Aisle aisle) {

        repo.deleteAisle(aisle);
    }

    public void deleteAislesByName(String name){
        List<Aisle> delThese = repo.getAislesByName(name);
        for (Aisle a :
                delThese) {
            repo.deleteAisle(a);
        }
    }
}