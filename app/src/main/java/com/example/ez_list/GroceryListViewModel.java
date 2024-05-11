package com.example.ez_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.model.GroceryListModel;
import com.example.ez_list.repo.GroceryRepository;

import java.util.ArrayList;
import java.util.List;

public class GroceryListViewModel extends AndroidViewModel {
    private final GroceryRepository repo;

    private MutableLiveData<GroceryList> rawList = new MutableLiveData<>();
    private LiveData<GroceryListModel> populatedList =
            Transformations.switchMap(rawList, list -> {
                if(list.name == null)
                {
                    return new MutableLiveData<>(null);
                } else {
                    return getPopulatedListFromRepo(list);
                }
            });

    private MutableLiveData<GroceryListModel> listInProgress = new MutableLiveData<>();

    private Grocery thisItem;

    public GroceryListViewModel(@NonNull Application app) {
        super(app);
        repo = GroceryRepository.getInstance(app.getApplicationContext());
    }

    private LiveData<GroceryListModel> getPopulatedListFromRepo(GroceryList list) {
        String name = list.name;
        long id = list.id;
        LiveData<List<Grocery>> groceries = repo.getGroceriesInList(name);
        return Transformations.map(groceries, groceryList -> {

            GroceryListModel model = new GroceryListModel();
            if(groceryList == null) {
                model.listId = id;
                model.listName = name;
                model.list = new ArrayList<Grocery>();
            } else {
                model.listId = id;
                model.listName = name;
                model.list = groceryList;
            }
            return model;
        });
    }

    public LiveData<GroceryListModel> getListInProgress()
    {
        return listInProgress;
    }

    public void startNewList(String listName)
    {
        if(listName == null) {
            listName = "Untitled List";
        }
        GroceryListModel model = new GroceryListModel();
        model.listName = listName;
        model.list = new ArrayList<Grocery>();
        listInProgress.setValue(model);
    }

    public Grocery getGroceryByName(String name) {
        return repo.getGroceryByName(name);
    }

    public void addItemToNewList() {
        GroceryListModel old = listInProgress.getValue();
        if(old == null)
        {
            startNewList(null);
            old = listInProgress.getValue();
        }
        old.list.add(thisItem);
        listInProgress.setValue(old);
    }

    public void setGrocery(Grocery g) {
        thisItem = g;
    }

    public void deleteItemFromNewList(Grocery g) {
        GroceryListModel old = listInProgress.getValue();
        old.list.remove(g);
        listInProgress.setValue(old);
    }

    public void saveNewList() {
       repo.insertNewList(listInProgress.getValue());
    }
    public LiveData<GroceryListModel> getNewList(){
        return listInProgress;
    }
    public void setList(String list) {
        GroceryList l = repo.getListByName(list);
        if (l == null)
        {
            return;
        }
        rawList.setValue(l);
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
        addItemToNewList();
    }

    public boolean aisleExists(String groceryName) {
        Grocery result = repo.getGroceryByName(groceryName);
        if(result == null || result.mAisleId < 1) {
            return false;
        }
        return true;
    }

    public void setListName(String name) {
        GroceryListModel glm = listInProgress.getValue();
        glm.listName = name;
        listInProgress.setValue(glm);
    }

    public LiveData<GroceryListModel> getList() {
        return populatedList;
    }
}
