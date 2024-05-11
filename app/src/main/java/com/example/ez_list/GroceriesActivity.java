package com.example.ez_list;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.model.GroceryListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroceriesActivity extends AppCompatActivity implements NewItemDialogFragment.OnDialogTextEntered {
    GroceriesViewModel groceriesViewModel;

    Button deleteGroceryBtn;
    Button addGroceryBtn;
    LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_groceries);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listContainer = findViewById(R.id.allGroceriesHolder);
        addGroceryBtn = findViewById(R.id.addGroceryBtn);
        deleteGroceryBtn = findViewById(R.id.deleteGroceryBtn);
        groceriesViewModel = new ViewModelProvider(this).get(GroceriesViewModel.class);
        groceriesViewModel.getAllGroceries().observe(this, groceries -> {
            updateUI(getStaticStringList(groceries));
        });

        addGroceryBtn.setOnClickListener(v->OnNewItemClick());
        deleteGroceryBtn.setOnClickListener(v->OnDeleteItemClick());
    }


    public void updateUI(List<String> displayStrings) {
        listContainer.removeAllViews();
        for (String item :
                displayStrings) {
            TextView newTextView = new TextView(this);
            newTextView.setText(item);
            newTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            newTextView.setTextSize(18);
            listContainer.addView(newTextView);
        }
    }
    private ArrayList<String> getStaticStringList(List<Grocery> groceries) {
        ArrayList<String> staticStringList = new ArrayList<>();
        String current_aisle = "";
        for (Grocery grocery :
                groceries) {
            if(!Objects.equals(current_aisle, grocery.mAisleName.toUpperCase())) {
                current_aisle = "AISLE  --  " + grocery.mAisleName.toUpperCase();
                staticStringList.add(current_aisle);
            }
            staticStringList.add(grocery.mName);
        }
        return staticStringList;
    }

    public void OnNewItemClick(){
        NewItemDialogFragment diag = new NewItemDialogFragment();
        diag.show(getSupportFragmentManager(), "firstDialog");
    }


    public void OnDeleteItemClick(){
        NewItemDialogFragment diag = new NewItemDialogFragment();
        diag.show(getSupportFragmentManager(), "deleteDialog");
    }

    @Override
    public void onFirstItemTextEntered(String text) {
        groceriesViewModel.addNameToGrocery(text);

        boolean aisleExists = groceriesViewModel.aisleExists(text);
        if(aisleExists)
        {
            // if we were able to find an aisle in the DB, then this item has been created before
            // shortcut to inserting the grocery
            Grocery g = groceriesViewModel.getGroceryByName(text);
            groceriesViewModel.setGrocery(g);
            groceriesViewModel.insertNewGrocery();
        } else {
            // if aisle does not exist, we must designate the aisle
            // fire up the next dialog
            NewItemDialogFragment diag = new NewItemDialogFragment();
            diag.show(getSupportFragmentManager(), "secondDialog");
        }

    }

    @Override
    public void onSecondItemTextEntered(String text) {
        groceriesViewModel.addAisleToGrocery(text);
    }

    @Override
    public void onDeleteItemTextEntered(String text) {
        groceriesViewModel.deleteGroceryByName(text);
    }
}