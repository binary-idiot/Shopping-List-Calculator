package com.jonahmiddleton.shoppinglistcalculator;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Data for an item
 */
public class Item implements Parcelable {
    private int id;
    private String name;
    private double amount, price;
    private boolean calculated, pricePerUnit;

    /**
     * Constant representing the lack of an Id for an item
     */
    public static final int NO_ID = -1;

    /**
     * Creator for parcels of item
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * Create default new item
     */
    public Item(){
        this(NO_ID, "", 0, 0, false, true);
    }

    /**
     * Create item from source parcel
     * @param source
     */
    public Item(Parcel source){
        this.id = source.readInt();
        this.name = source.readString();
        this.amount = source.readDouble();
        this.price = source.readDouble();
        this.calculated = (boolean)source.readValue(null);
        this.pricePerUnit = (boolean)source.readValue(null);
    }

    /**
     * Create item with int representation of booleans
     * @param id
     * @param name
     * @param amount
     * @param price
     * @param calculated
     * @param pricePerUnit
     */
    public Item(int id, String name, double amount, double price, int calculated, int pricePerUnit){
        this(id, name, amount, price, calculated == 1, pricePerUnit == 1);
    }

    /**
     * Create item directly from params
     * @param id
     * @param name
     * @param amount
     * @param price
     * @param calculated
     * @param pricePerUnit
     */
    public Item(int id, String name, double amount, double price, boolean calculated, boolean pricePerUnit){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.calculated = calculated;
        this.pricePerUnit = pricePerUnit;
    }

    /**
     * Unused method from parcel interface
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Wtite the item to a parcel
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.amount);
        dest.writeDouble(this.price);
        dest.writeValue(this.calculated);
        dest.writeValue(this.pricePerUnit);
    }

    /**
     * @return id of item
     */
    public int getId() {
        return id;
    }

    /**
     * @return name of item
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name for item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return amount of item
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount new amount of item
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return price of item
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price new price of item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return if item should be included in calculated total
     */
    public boolean isCalculated() {
        return calculated;
    }

    /**
     * @return int representation of if item should be included in calculated total
     */
    public int isCalculatedInt() {
        return (calculated)? 1 : 0;
    }

    /**
     * @param calculated if item should be included in calculated total
     */
    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    /**
     * @return if price should be calculated per amount of item
     */
    public boolean isPricePerUnit() {
        return pricePerUnit;
    }

    /**
     * @return int representation of if price should be calculated per amount of item
     */
    public int isPricePerUnitInt(){
        return (pricePerUnit)? 1 : 0;
    }

    /**
     * @param pricePerUnit if price should be calculated per amount of item
     */
    public void setPricePerUnit(boolean pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

}
