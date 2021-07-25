package com.jonahmiddleton.shoppinglistcalculator;

import android.provider.BaseColumns;

public class DataContract {
    private DataContract() {}

    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_ITEMNAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUNM_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CALCULATED = "calculated";
        public static final String COLUMN_NAME_PERITEM = "peritem";

        public static final String SQL_CREATE_ITEMS_TABLE =
                "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
                        ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ItemEntry.COLUMN_NAME_ITEMNAME + " TEXT," +
                        ItemEntry.COLUMN_NAME_AMOUNT + " REAL," +
                        ItemEntry.COLUNM_NAME_PRICE + " REAL," +
                        ItemEntry.COLUMN_NAME_CALCULATED + " INTEGER," +
                        ItemEntry.COLUMN_NAME_PERITEM + " INTEGER)";

        public static final String SQL_DELETE_ITEMS_TABLE =
                "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;


    }
}
