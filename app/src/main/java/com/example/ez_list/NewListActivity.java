package com.example.ez_list;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ez_list.data.Grocery;

public class NewListActivity extends AppCompatActivity implements NewItemDialogFragment.OnDialogTextEntered {

    private static GroceryListViewModel groceryListViewModel;

    Button newItemBtn;

    Button saveItemBtn;

    EditText titleEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titleEditText = findViewById(R.id.editTitleEditText);
        newItemBtn = findViewById(R.id.AddItemButton);
        newItemBtn.setOnClickListener(v -> OnNewItemClick());
        saveItemBtn = findViewById(R.id.SaveListBtn);
        saveItemBtn.setOnClickListener(v ->OnSaveItemClick());
        groceryListViewModel = new ViewModelProvider(this).get(GroceryListViewModel.class);
    }


    public void OnNewItemClick(){
        NewItemDialogFragment diag = new NewItemDialogFragment();
        diag.show(getSupportFragmentManager(), "firstDialog");
    }



    public void OnSaveItemClick(){
        groceryListViewModel.setListName(titleEditText.getText().toString());
        groceryListViewModel.saveNewList();
        finish();
    }

    @Override
    public void onFirstItemTextEntered(String text) {
        groceryListViewModel.addNameToGrocery(text);
        boolean aisleExists = groceryListViewModel.aisleExists(text);
        if(aisleExists)
        {
            // if we were able to find an aisle in the DB, then this item has been created before
            // shortcut to inserting the grocery
            Grocery g = groceryListViewModel.getGroceryByName(text);
            groceryListViewModel.setGrocery(g);
            groceryListViewModel.addItemToNewList();
        } else {
            // if aisle does not exist, we must designate the aisle
            // fire up the next dialog
            NewItemDialogFragment diag = new NewItemDialogFragment();
            diag.show(getSupportFragmentManager(), "secondDialog");
        }
    }

    @Override
    public void onSecondItemTextEntered(String text) {
        groceryListViewModel.addAisleToGrocery(text);
    }

    @Override
    public void onDeleteItemTextEntered(String text) {
        return;
    }


}
