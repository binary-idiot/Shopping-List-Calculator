package com.jonahmiddleton.shoppinglistcalculator;

import android.content.ContentValues;
import android.database.Cursor;
import com.jonahmiddleton.shoppinglistcalculator.DataContract.ItemEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemData {

    public static List<Item> getItems(DBHelper dbHelper){
        return getItems(dbHelper, ItemEntry._ID, true);
    }

    public static List<Item> getItems(DBHelper dbHelper, String sortBy, boolean orderDesc){
        return getItemData(dbHelper, null, null,
                sortBy + " " + ((orderDesc)? "DESC" : "ASC"));
    }

    public static Item getItem(DBHelper dbHelper, int id){
        List<Item> items = getItemData(
                dbHelper,
                ItemEntry._ID + " = ?",
                new String[]{Integer.toString(id)},
                null
        );

        Iterator<Item> iterator = items.iterator();

        if (!iterator.hasNext()) {
            return null;
        }

        Item item = iterator.next();

        if (iterator.hasNext()) {
            throw new RuntimeException("Collection contains more than one item");
        }

        return item;
    }

    public static boolean saveItem(DBHelper dbHelper, Item item){
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_NAME_ITEMNAME, item.getName());
        values.put(ItemEntry.COLUMN_NAME_AMOUNT, item.getAmount());
        values.put(ItemEntry.COLUNM_NAME_PRICE, item.getPrice());
        values.put(ItemEntry.COLUMN_NAME_CALCULATED, item.isCalculatedInt());
        values.put(ItemEntry.COLUMN_NAME_PERITEM, item.isPricePerUnitInt());

        if(item.getId() != Item.NO_ID){
            return dbHelper.getWritableDatabase().insert(ItemEntry.TABLE_NAME, null, values) != -1;
        }else{
            return dbHelper.getReadableDatabase().update(
                    ItemEntry.TABLE_NAME,
                    values,
                    ItemEntry._ID + " = ?",
                    new String[]{Integer.toString(item.getId())}
            ) == 0;
        }
    }

    private static List<Item> getItemData(DBHelper dbHelper, String selection, String[] selectionArgs, String sort){
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_NAME_ITEMNAME,
                ItemEntry.COLUMN_NAME_AMOUNT,
                ItemEntry.COLUNM_NAME_PRICE,
                ItemEntry.COLUMN_NAME_CALCULATED,
                ItemEntry.COLUMN_NAME_PERITEM
        };

        Cursor cursor = dbHelper.getReadableDatabase().query(
                ItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sort
        );

        List<Item> items = new ArrayList<>();
        while (cursor.moveToNext()){
            Item i = new Item(cursor.getInt(cursor.getColumnIndexOrThrow(ItemEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_ITEMNAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_AMOUNT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ItemEntry.COLUNM_NAME_PRICE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_CALCULATED)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_PERITEM)));

            items.add(i);
        }

        return items;
    }
}
