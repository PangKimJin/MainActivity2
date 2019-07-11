package com.example.mainactivity;

public class ExpenditureList extends List {
    private String location;
    public ExpenditureList(int id, String name, String createdDate, String location) {
        super(id, name, createdDate);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


}
