package com.example.ez_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ez_list.data.GroceryList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GroceryListItem.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private GroceryListItem.ItemAdapter listAdapter;
    private GroceryListCollectionViewModel groceryListCollectionViewModel;
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
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.grocery_lists_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryListCollectionViewModel = new ViewModelProvider(this).get(GroceryListCollectionViewModel.class);

        groceryListCollectionViewModel.getLists().observe(this, lists -> {
            if (lists.isEmpty()) {
                GroceryList exampleList = new GroceryList();
                exampleList.name = "Example";
                groceryListCollectionViewModel.addList(exampleList);  // This line adds the starter list to your ViewModel/Data Layer
            }
            updateUI(lists);
        });

        listAdapter = new GroceryListItem.ItemAdapter(new ArrayList<>(), this, this);
        recyclerView.setAdapter(listAdapter);
    }

    private void updateUI(List<GroceryList> lists) {
       listAdapter.updateList(lists);
    }



    @Override
    public void onDeleteClick(GroceryList list){
        groceryListCollectionViewModel.deleteList(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        int itemId = item.getItemId();

        if (itemId == R.id.new_list) {
            Intent i = new Intent(this, NewListActivity.class);
            startActivity(i);
        }
        else if (itemId == R.id.your_aisles) {
            Toast.makeText(this, "Your Aisles Clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.groceries) {
            Intent i = new Intent(this, GroceriesActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}