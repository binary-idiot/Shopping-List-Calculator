package com.jonahmiddleton.shoppinglistcalculator;

public class Item {
    private String name;
    private double amount, price;
    private boolean calculated, pricePerUnit;

    public Item(String name, double amount, double price, boolean calculated, boolean pricePerUnit){
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.calculated = calculated;
        this.pricePerUnit = pricePerUnit;
    }
}
