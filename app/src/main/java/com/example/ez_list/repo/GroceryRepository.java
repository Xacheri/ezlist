package com.example.ez_list.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.ez_list.data.Aisle;
import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.data.ListItemJunction;
import com.example.ez_list.model.GroceryListModel;

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
                .fallbackToDestructiveMigration()
                .build();

        mGroceryDao = db.groceryDao();
        mAisleDao = db.aisleDao();
        mListsDao = db.listsDao();
    }
    public LiveData<List<Grocery>> getGroceriesInOrder(List<String> names) {
        return mGroceryDao.getGroceriesInOrder(names);
    }

    public LiveData<List<Grocery>> getGroceriesInAisle(long aisle_id) {
        return mGroceryDao.getGroceriesInAisle(aisle_id);
    }

    public Grocery getGroceryByName(String name) {
        return mGroceryDao.getGroceryByName(name);
    }

    public LiveData<List<Grocery>> getAllGroceries(){
        return mGroceryDao.getAllGroceries();
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

    public void deleteGroceryItemsByName(String name) { mGroceryDao.deleteGroceryItemsByName(name);}

    public long updateAisles(List<Aisle> aisles) {
        return mAisleDao.updateAisles(aisles);
    }

    public void deleteAisle(Aisle aisle) {
        mAisleDao.deleteAisle(aisle);
    }

    public void insertAisle(Aisle aisle) {
        mAisleDao.insertAisle(aisle);
    }

   public LiveData<List<Aisle>> getAislesFromStore() {
        return mAisleDao.getAisles();
   }

   public int getMaxIndexForAisle() {
        return mAisleDao.getMaxIndexForAisle();
   }

   public int getAisleIdByName(String name) {
        return mAisleDao.getAisleIdByName(name);
   }
   public LiveData<List<GroceryList>> getAllLists() {
        return mListsDao.getAllLists();
   }

   public void insertList(GroceryList list) {mListsDao.insertList(list);}

    public GroceryList getListByName(String name) {
        return mListsDao.getListByName(name);
    }

    public LiveData<List<Grocery>> getGroceriesInList(String name) {
        return mGroceryDao.getGroceriesInList(name);
    }

    public void deleteList(GroceryList list) {
        mListsDao.deleteList(list);
    }

    public void insertNewList(GroceryListModel l) {
        GroceryList list = new GroceryList();
        list.name = l.listName;
        insertList(list);
        list = getListByName(l.listName);
        for (Grocery g :
                l.list) {
            addGroceryItem(g);
            g = mGroceryDao.getGroceryByName(g.mName);
            insertListItemJuntion(list.id, g.mId);
        }
    }

    public void insertListItemJuntion(long listId, long foodId)
    {
        ListItemJunction lj = new ListItemJunction();
        lj.listId = listId;
        lj.groceryId = foodId;
        mGroceryDao.addListItemJunction(lj);
    }
}
