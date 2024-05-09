package com.example.ez_list.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;

import java.util.List;

public class GroceryRepository {
    private static GroceryRepository mGroceryRepository;

    private final GroceryDao mGroceryDao;

    private final AisleDao mAisleDao;

    private final GroceryListDao mListsDao;

    public static GroceryRepository getInstance(Context context)
    {
        if (mGroceryRepository == null) {
            mGroceryRepository = new GroceryRepository(context);
        }
        return mGroceryRepository;
    }

    private GroceryRepository(Context context) {
        EZListDatabase db = Room.databaseBuilder(context, EZListDatabase.class, "ezlist.db")
                .allowMainThreadQueries()
                .build();

        mGroceryDao = db.groceryDao();
        mAisleDao = db.aisleDao();
        mListsDao = db.listsDao();
    }
    public LiveData<List<Grocery>> getGroceriesInOrder(List<String> names, String store_name) {
        return mGroceryDao.getGroceriesInOrder(names, store_name);
    }


    public LiveData<List<Grocery>> getGroceriesInAisle(long aisle_id) {
        return mGroceryDao.getGroceriesInAisle(aisle_id);
    }

    public long addGroceryItem(Grocery item) {
        return mGroceryDao.addGroceryItem(item);
    }

    public void updateGroceryItem(Grocery item) {
        mGroceryDao.updateGroceryItem(item);
    }

    public void deleteGroceryItem(Grocery item) {
        mGroceryDao.deleteGroceryItem(item);
    }

    public long updateAisles(List<Aisle> aisles) {
        return mAisleDao.updateAisles(aisles);
    }

    public void deleteAisle(Aisle aisle) {
        mAisleDao.deleteAisle(aisle);
    }

    public void insertAisle(Aisle aisle) {
        mAisleDao.insertAisle(aisle);
    }

   public LiveData<List<Aisle>> getAislesFromStore(long store_id) {
        return mAisleDao.getAislesFromStore(store_id);
   }

   public LiveData<List<GroceryList>> getAllLists() {
        return mListsDao.getAllLists();
   }

   public void insertList(GroceryList list) {mListsDao.insertList(list);}
}
