package com.jonahmiddleton.shoppinglistcalculator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jonahmiddleton.shoppinglistcalculator.DataContract.ItemEntry;

/**
 * Helper to handle the database
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingListCalculator.db";

    /**
     * create a DBHelper
     * @param context
     */
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * create the database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemEntry.SQL_CREATE_ITEMS_TABLE);
    }

    /**
     * upgrade the database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ItemEntry.SQL_DELETE_ITEMS_TABLE);
        onCreate(db);
    }
}
