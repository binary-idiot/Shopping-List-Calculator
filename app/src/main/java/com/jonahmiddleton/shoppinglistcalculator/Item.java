package com.jonahmiddleton.shoppinglistcalculator;

public class Item {
    private int id;
    private String name;
    private double amount, price;
    private boolean calculated, pricePerUnit;

    public static final int NO_ID = -1;

    public Item(){
        this(NO_ID, "", 0, 0, true, false);
    }

    public Item(int id, String name, double amount, double price, int calculated, int pricePerUnit){
        this(id, name, amount, price, calculated == 1, pricePerUnit == 1);
    }

    public Item(int id, String name, double amount, double price, boolean calculated, boolean pricePerUnit){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.calculated = calculated;
        this.pricePerUnit = pricePerUnit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public int isCalculatedInt() {
        return (calculated)? 1 : 0;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public boolean isPricePerUnit() {
        return pricePerUnit;
    }

    public int isPricePerUnitInt(){
        return (pricePerUnit)? 1 : 0;
    }

    public void setPricePerUnit(boolean pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
