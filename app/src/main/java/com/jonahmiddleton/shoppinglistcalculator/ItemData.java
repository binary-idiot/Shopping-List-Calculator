package com.jonahmiddleton.shoppinglistcalculator;

import android.content.ContentValues;
import android.database.Cursor;
import com.jonahmiddleton.shoppinglistcalculator.DataContract.ItemEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data access layer for Item table
 */
public class ItemData {

    /**
     * Get a list of items from the database
     * @param dbHelper
     * @return list of all items sorted by Id
     */
    public static List<Item> getItems(DBHelper dbHelper){
        return getItems(dbHelper, ItemEntry._ID, true);
    }

    /**
     * Get a sorted list of items from the database
     * @param dbHelper
     * @param sortBy column to sort the list by
     * @param orderDesc direction of the sort (true for desc or false for asc)
     * @return list of all items sorted by the supplied params
     */
    public static List<Item> getItems(DBHelper dbHelper, String sortBy, boolean orderDesc){
        return getItemData(dbHelper, null, null,
                sortBy + " " + ((orderDesc)? "DESC" : "ASC"));
    }

    /**
     * Get a single item from the database
     * @param dbHelper
     * @param id
     * @return item with the supplied id
     */
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

    /**
     * Save a new or existing item to the database
     * @param dbHelper
     * @param item item to save. If the id is NO_ID (-1) then it will be added to the database,
     *             otherwise the item with the matching id will be updated with the item object
     * @return if the item was successfully saved or not
     */
    public static boolean saveItem(DBHelper dbHelper, Item item){
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_NAME_ITEMNAME, item.getName());
        values.put(ItemEntry.COLUMN_NAME_AMOUNT, item.getAmount());
        values.put(ItemEntry.COLUNM_NAME_PRICE, item.getPrice());
        values.put(ItemEntry.COLUMN_NAME_CALCULATED, item.isCalculatedInt());
        values.put(ItemEntry.COLUMN_NAME_PERITEM, item.isPricePerUnitInt());

        if(item.getId() == Item.NO_ID){
            return dbHelper.getWritableDatabase().insert(ItemEntry.TABLE_NAME, null, values) != -1;
        }else{
            return dbHelper.getReadableDatabase().update(
                    ItemEntry.TABLE_NAME,
                    values,
                    ItemEntry._ID + " = ?",
                    new String[]{Integer.toString(item.getId())}
            ) == 1;
        }
    }

    /**
     * Delete an item from the database
     * @param dbHelper
     * @param item item to delete from the database
     * @return if the item was successfully deleted or not
     */
    public static boolean deleteItem(DBHelper dbHelper, Item item){
        return dbHelper.getWritableDatabase().delete(
                ItemEntry.TABLE_NAME,
                ItemEntry._ID + " = ?",
                new String[]{Integer.toString(item.getId())}
        ) == 1;
    }

    /**
     * Retrieve the items matching the selection and convert it into a list
     * @param dbHelper
     * @param selection selection string to query
     * @param selectionArgs arguments to use in the query
     * @param sort sort of the returned list of items
     * @return list of items matching the supplied selection
     */
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
