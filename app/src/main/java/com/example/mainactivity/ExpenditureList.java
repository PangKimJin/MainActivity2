package com.example.mainactivity;

public class ExpenditureList extends List {
    private String category;
    public ExpenditureList(int id, String name, String createdDate, String category) {
        super(id, name, createdDate);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }


}
