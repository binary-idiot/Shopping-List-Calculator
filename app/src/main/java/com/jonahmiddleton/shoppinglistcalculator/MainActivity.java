package com.jonahmiddleton.shoppinglistcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView shoppingListView;
    TextView calculatedTotalView;
    FloatingActionButton addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListView = (RecyclerView)findViewById(R.id.shoppingListView);
        calculatedTotalView = (TextView)findViewById(R.id.calculatedTotalView);
        addItemButton = (FloatingActionButton)findViewById(R.id.addItemButton);
    }
}