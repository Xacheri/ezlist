package com.example.ez_list;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ez_list.data.Aisle;

import java.util.ArrayList;
import java.util.List;

public class AislesActivity extends AppCompatActivity implements AisleAdapter.OnAisleOrderChangeListener, NewItemDialogFragment.OnDialogTextEntered {

    AislesViewModel aislesViewModel;
    AisleAdapter aisleAdapter;
    RecyclerView aisleRecyclerView;

    Button deleteAisleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aisles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteAisleBtn = findViewById(R.id.deleteAisleBtn);
        aisleRecyclerView = findViewById(R.id.aisleRecycleView);
        aisleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aislesViewModel = new ViewModelProvider(this).get(AislesViewModel.class);

        aisleAdapter = new AisleAdapter(new ArrayList<>(), this);
        aisleRecyclerView.setAdapter(aisleAdapter);


        ItemTouchHelper.Callback cb = new AisleMoveCallback(aisleAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(cb);
        helper.attachToRecyclerView(aisleRecyclerView);

        aislesViewModel.getAisles().observe(this, aisles -> {
            updateUI(aisles);
        });

        deleteAisleBtn.setOnClickListener(v -> OnDeleteItemClick());
    }

    public void updateUI(List<Aisle> aisles) {
        if(aisles.isEmpty()){
            aislesViewModel.firstAisle();
        }
        if(aisleRecyclerView.getAdapter() != null) {
            aisleAdapter.updateData(aisles);
        }
    }

    @Override
    public void OnAisleOrderChangeListener(List<Aisle> data) {
        aislesViewModel.updateAisles(data);
    }


    public void OnDeleteItemClick(){
        NewItemDialogFragment diag = new NewItemDialogFragment();
        diag.show(getSupportFragmentManager(), "deleteDialog");
    }

    @Override
    public void onFirstItemTextEntered(String text) {

    }

    @Override
    public void onSecondItemTextEntered(String text) {

    }

    @Override
    public void onDeleteItemTextEntered(String text) {
        aislesViewModel.deleteAislesByName(text);
    }
}