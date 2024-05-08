package com.example.ez_list;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ez_list.model.Grocery;
import com.example.ez_list.model.GroceryList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements GroceryListItem.ItemAdapter.OnButtonClickListener {

    private RecyclerView recyclerView;
    private GroceryListItem.ItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {


            actionBar.setIcon(R.drawable.cart_icon);
            // methods to display the icon in the ActionBar
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.grocery_lists_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<GroceryList> groceryList = new ArrayList<GroceryList>();
        GroceryList exampleList = new GroceryList();
        exampleList.name = "Example";
        groceryList.add(exampleList);
        groceryList.add(exampleList);
        groceryList.add(exampleList);
        listAdapter = new GroceryListItem.ItemAdapter(groceryList);
        recyclerView.setAdapter(listAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        int itemId = item.getItemId();

        if (itemId == R.id.your_lists) {
            Toast.makeText(this, "Your Lists Clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.your_aisles) {
            Toast.makeText(this, "Your Aisles Clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.groceries) {
            Toast.makeText(this, "Groceries Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteListClick(int pos) {

    }

    @Override
    public void onOpenListClick(int pos) {

    }
}