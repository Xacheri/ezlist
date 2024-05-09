package com.example.ez_list.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"listId", "groceryId"},
        foreignKeys = {
                @ForeignKey(entity = GroceryList.class,
                        parentColumns = "id",
                        childColumns = "listId"),
                @ForeignKey(entity = Grocery.class,
                        parentColumns = "mId",
                        childColumns = "groceryId")
        })
public class ListItemJunction {
    @ColumnInfo(name = "listId")
    public long listId;

    @ColumnInfo(name = "groceryId")
    public long groceryId;
}
