package com.example.ez_list.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Aisle.class, parentColumns = "id", childColumns = "aisle_id"))
public class Grocery {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "aisle_name")
    private String mAisleName;

    @ColumnInfo(name = "aisle_id")
    private String mAisleId;
    public Grocery(@NonNull String groceryName, String aisleName)
    {
        mName = groceryName;
        mAisleName = aisleName;
    }
}
