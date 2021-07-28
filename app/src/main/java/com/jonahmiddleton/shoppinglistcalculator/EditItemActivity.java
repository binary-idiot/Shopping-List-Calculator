package com.jonahmiddleton.shoppinglistcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * Activity displays interface to edit or create an item
 */
public class EditItemActivity extends AppCompatActivity {

    EditText itemNameEditText;
    EditText itemAmountEditText;
    EditText itemPriceEditText;
    CheckBox calculatePerAmountCheckBox;
    CheckBox includeItemInTotalCheckBox;
    Button updateItemButton;
    Button cancelItemButton;

    /**
     * Item currently being edited
     */
    Item currentItem;

    /**
     * Create the EditItem activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle intentData = getIntent().getExtras();
        currentItem = intentData.getParcelable(MainActivity.ITEM_TO_EDIT);

        itemNameEditText = (EditText)findViewById(R.id.itemNameEditText);
        itemAmountEditText = (EditText)findViewById(R.id.itemAmountEditText);
        itemPriceEditText = (EditText)findViewById(R.id.itemPriceEditText);
        calculatePerAmountCheckBox = (CheckBox)findViewById(R.id.calculatePerAmountCheckBox);
        includeItemInTotalCheckBox = (CheckBox)findViewById(R.id.includeItemInTotalCheckBox);
        updateItemButton = (Button)findViewById(R.id.updateItemButton);
        cancelItemButton = (Button)findViewById(R.id.cancelItemButton);

        itemNameEditText.setOnFocusChangeListener(new EditItemListener());
        itemAmountEditText.setOnFocusChangeListener(new EditItemListener());
        itemPriceEditText.setOnFocusChangeListener(new EditItemListener());
        calculatePerAmountCheckBox.setOnClickListener(new EditItemListener());
        includeItemInTotalCheckBox.setOnClickListener(new EditItemListener());

        updateItemButton.setOnClickListener(new ButtonListener(true));
        cancelItemButton.setOnClickListener(new ButtonListener(false));

        displayItem();
    }

    /**
     * Handle action bar home
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            confirmCancel();
        }
        return true;
    }

    /**
     * Handle back button
     */
    @Override
    public void onBackPressed() {
        confirmCancel();
    }

    /**
     * Update current item with the values entered by the user
     */
    private void updateItem(){
        NumberFormat numberFormat = NumberFormat.getInstance();

        try {
            String name = itemNameEditText.getText().toString();
            double amount = Double.parseDouble(itemAmountEditText.getText().toString());
            String priceAmtStr = itemPriceEditText.getText().toString()
                    .replace(Currency.getInstance(Locale.getDefault()).getSymbol(), "");
            double price = numberFormat.parse(priceAmtStr).doubleValue();
            boolean calculated = calculatePerAmountCheckBox.isChecked();
            boolean perUnit = includeItemInTotalCheckBox.isChecked();

            currentItem.setName(name);
            currentItem.setAmount(amount);
            currentItem.setPrice(price);
            currentItem.setCalculated(calculated);
            currentItem.setPricePerUnit(perUnit);
        }catch (ParseException e){
            Toast.makeText(getApplicationContext(), "Invalid value entered", Toast.LENGTH_SHORT).show();
        }

        displayItem();
    }

    /**
     * Display the values in current item in the respective views
     */
    private void displayItem(){
        DecimalFormat decimal = new DecimalFormat("#.##");
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        itemNameEditText.setText(currentItem.getName());
        itemAmountEditText.setText(decimal.format(currentItem.getAmount()));
        itemPriceEditText.setText(currency.format(currentItem.getPrice()));

        calculatePerAmountCheckBox.setChecked(currentItem.isCalculated());
        includeItemInTotalCheckBox.setChecked(currentItem.isPricePerUnit());
    }

    /**
     * Save the item to the database
     * @param item item to save
     * @return if the item was successfully saved
     */
    private boolean saveItem(Item item){
        DBHelper dbHelper = new DBHelper(this);
        boolean saved = ItemData.saveItem(dbHelper, item);
        dbHelper.close();
        return saved;
    }

    /**
     * Display dialog to confirm cancel edit
     */
    private void confirmCancel(){
        new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Are you sure you want to cancel editing this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Handles actions on editor views
     */
    private class EditItemListener implements View.OnFocusChangeListener, View.OnClickListener {

        /**
         * Handle click events
         * @param v target view
         */
        @Override
        public void onClick(View v) {
            updateItem();
        }

        /**
         * Handle focus change events
         * @param v target view
         * @param hasFocus if target view has focus
         */
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus){
                updateItem();
            }
        }
    }

    /**
     * Handles clicks on update and cancel buttons
     */
    private class ButtonListener implements View.OnClickListener {
        /**
         * If button should save current item
         */
        boolean save;

        /**
         * Create ButtonListener
         * @param saveOnClick if button should save current item
         */
        public ButtonListener(boolean saveOnClick){
            this.save = saveOnClick;
        }

        /**
         * Handle click on a button
         * @param v target view
         */
        @Override
        public void onClick(View v) {
            if(save){
                if(saveItem(currentItem)){
                    Toast.makeText(getApplicationContext(), currentItem.getName() + " successfully saved", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Error saving " + currentItem.getName(), Toast.LENGTH_LONG).show();
                }
            }else{
                confirmCancel();
            }
        }
    }
}