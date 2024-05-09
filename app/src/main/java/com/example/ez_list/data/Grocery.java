package com.example.ez_list.data;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Aisle.class, parentColumns = "id", childColumns = "aisle_id"))
public class Grocery {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long mId;

    @NonNull
    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "aisle_name")
    public String mAisleName;


    @ColumnInfo(name = "aisle_id")
    public String mAisleId;
}
