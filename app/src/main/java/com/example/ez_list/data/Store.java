package com.example.ez_list.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Store {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
     long mId;

    @ColumnInfo(name = "name")
    String mName;
}
