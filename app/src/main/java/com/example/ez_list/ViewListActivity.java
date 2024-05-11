package com.example.ez_list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.model.GroceryListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewListActivity extends AppCompatActivity {

    GroceryListViewModel listViewModel;

    LinearLayout listContainer;

    TextView titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        String listName = i.getStringExtra("LIST_NAME");
        titleView = findViewById(R.id.ListTitleView);
        listContainer = findViewById(R.id.string_holder);
        listViewModel = new ViewModelProvider(this).get(GroceryListViewModel.class);
        listViewModel.setList(listName);
        listViewModel.getList().observe(this, groceryListModel -> {
            titleView.setText(groceryListModel.listName);
            updateUI(getStaticStringList(groceryListModel));
        });

    }

    // man frick adapters, I like strings
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

    private ArrayList<String> getStaticStringList(GroceryListModel listModel) {
        ArrayList<String> staticStringList = new ArrayList<>();
        String current_aisle = "";
        for (Grocery grocery :
                listModel.list) {
            if(!Objects.equals(current_aisle, grocery.mAisleName.toUpperCase())) {
                current_aisle = "AISLE  --  " + grocery.mAisleName.toUpperCase();
                staticStringList.add(current_aisle);
            }
            staticStringList.add(grocery.mName);
        }
        return staticStringList;
    }


}