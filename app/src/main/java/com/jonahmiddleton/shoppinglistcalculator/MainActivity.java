package com.jonahmiddleton.shoppinglistcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Activity displays the list of items, their total price, and a button to add more to the list
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView shoppingListView;
    TextView calculatedTotalView;
    FloatingActionButton addItemButton;

    /**
     * Key for the item to edit in intent extras
     */
    public static final String ITEM_TO_EDIT = "com.jonahmiddleton.shoppinglistcalculator.EDITITEM";

    /**
     * Create the Main activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListView = (RecyclerView)findViewById(R.id.shoppingListView);
        calculatedTotalView = (TextView)findViewById(R.id.calculatedTotalView);
        addItemButton = (FloatingActionButton)findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(new AddItemListener());

    }

    /**
     * Event handler for the addItemButton
     */
    private class AddItemListener implements View.OnClickListener{

        /**
         * Handle click on addItemButton
         * @param v target of the click event
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
            intent.putExtra(ITEM_TO_EDIT, new Item());
            startActivity(intent);
        }
    }
}