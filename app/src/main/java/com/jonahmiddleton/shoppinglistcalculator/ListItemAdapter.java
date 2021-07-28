package com.jonahmiddleton.shoppinglistcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<Item> items;

    public ListItemAdapter(List<Item> itemsList){
        this.items = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItemView = inflater.inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(listItemView);
        return holder;
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

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
}
