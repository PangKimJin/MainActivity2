package com.example.mainactivity;

public class Item {
    private String name;
    private int quantity;
    private double price;
    private int id;
    private int listID;
    private boolean isSelected;

    public Item(int id, String name, int quantity, double price, int listID) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.listID = listID;
        this.isSelected = false;

    }
    public boolean isSelected() {
        return isSelected;
    }

    public void selected() {
        isSelected = true;
    }

    public void notSelected() {
        isSelected = false;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getListID() {
        return this.listID;
    }

    public double calculatePrice() {
        return this.getPrice() * this.getQuantity();
    }
}

