package com.example.ez_list.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Store.class, parentColumns = "id", childColumns = "store_id", onDelete = ForeignKey.CASCADE))
public class Aisle {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "index")
    private int mIndex;

    @ColumnInfo(name = "store_id")
    private long mStoreId;
}
