package com.jonahmiddleton.shoppinglistcalculator;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.jonahmiddleton.shoppinglistcalculator.MainActivity.ITEM_TO_EDIT;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private final List<Item> items;
    private Context context;
    private DBHelper dbHelper;

    public ListItemAdapter(Context context, List<Item> itemList, DBHelper dbHelper){
        this.items = itemList;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItemView = inflater.inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ViewHolder holder, int position) {
        DecimalFormat decimal = new DecimalFormat("#.##");
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        Item item = items.get(position);
        String amountPriceText = (item.isPricePerUnit())
                ? decimal.format(item.getAmount()) + " X " + currency.format(item.getPrice())
                : "X" + decimal.format(item.getAmount());
        double itemTotalPrice = ((item.isPricePerUnit()) ? item.getAmount() : 1) * item.getPrice();

        holder.itemNameView.setText(item.getName());
        holder.amountPriceView.setText(amountPriceText);
        holder.totalItemPriceView.setText(currency.format(itemTotalPrice));
        holder.includeListItemInTotalCHeckBox.setChecked(item.isCalculated());

        ItemListener listener = new ItemListener(item, position, context);
        holder.includeListItemInTotalCHeckBox.setOnClickListener(listener);
        holder.itemView.setOnCreateContextMenuListener(listener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public void updateItem(int position, Item item){
        if(ItemData.saveItem(dbHelper, item)){
            items.set(position, item);
            this.notifyItemChanged(position);
            try{
                AdapterCallBack callBack = (AdapterCallBack)context;
                callBack.onCallback();
            }catch (Exception ignored){}
        }
    }

    public void removeItem(int position){
        Item item = items.get(position);
        if(ItemData.deleteItem(dbHelper, item)){
            items.remove(position);
            this.notifyItemRemoved(position);
            try{
                AdapterCallBack callBack = (AdapterCallBack)context;
                callBack.onCallback();
            }catch (Exception ignored){}
            Toast.makeText(context.getApplicationContext(), item.getName() + " removed from list", Toast.LENGTH_LONG).show();
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemNameView;
        public TextView amountPriceView;
        public TextView totalItemPriceView;
        public CheckBox includeListItemInTotalCHeckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNameView = (TextView)itemView.findViewById(R.id.itemNameView);
            amountPriceView = (TextView)itemView.findViewById(R.id.amountPriceView);
            totalItemPriceView = (TextView)itemView.findViewById(R.id.totalItemPriceView);
            includeListItemInTotalCHeckBox = (CheckBox)itemView.findViewById(R.id.includeListItemInTotalCheckBox);
        }
    }

    private class ItemListener implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private Item item;
        private int position;
        private Context context;

        public ItemListener(Item item, int position, Context context){
            this.item = item;
            this.position = position;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            item.setCalculated(!item.isCalculated());
            ListItemAdapter.this.updateItem(position, item);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(context);
            inflater.inflate(R.menu.item_menu, menu);
            menu.findItem(R.id.editMenuItem).setOnMenuItemClickListener(this);
            menu.findItem(R.id.deleteMenuItem).setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.editMenuItem:
                    Intent intent = new Intent(context, EditItemActivity.class);
                    intent.putExtra(ITEM_TO_EDIT, item);
                    context.startActivity(intent);
                    break;
                case R.id.deleteMenuItem:
                    ListItemAdapter.this.removeItem(position);
                    break;
            }

            return false;
        }
    }
}
