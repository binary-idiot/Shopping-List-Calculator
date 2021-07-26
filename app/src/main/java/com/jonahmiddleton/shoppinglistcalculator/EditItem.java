package com.jonahmiddleton.shoppinglistcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {

    EditText itemNameEditText;
    EditText itemAmountEditText;
    EditText itemPriceEditText;
    CheckBox calculatePerAmountCheckBox;
    CheckBox includeItemInTotalCheckBox;
    Button updateItemButton;
    Button cancelItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemNameEditText = (EditText)findViewById(R.id.itemNameEditText);
        itemAmountEditText = (EditText)findViewById(R.id.itemAmountEditText);
        itemPriceEditText = (EditText)findViewById(R.id.itemPriceEditText);
        calculatePerAmountCheckBox = (CheckBox)findViewById(R.id.calculatePerAmountCheckBox);
        includeItemInTotalCheckBox = (CheckBox)findViewById(R.id.includeItemInTotalCheckBox);
        updateItemButton = (Button)findViewById(R.id.updateItemButton);
        cancelItemButton = (Button)findViewById(R.id.cancelItemButton);
    }
}