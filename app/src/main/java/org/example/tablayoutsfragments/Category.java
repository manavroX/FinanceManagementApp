package org.example.tablayoutsfragments;

import java.util.Date;

public class Category {
    int id;
    int EI;
    int want_need;
    String title;
    int value;
    String category;
    String date;

    public Category() {
    }

    public String getDate() {
        return date;
    }

    public Category(int EI, int want_need, String title, int value, String category, String date) {
        this.EI = EI;
        this.want_need = want_need;
        this.title = title;
        this.value = value;
        this.category = category;
        this.date = date;
    }

//    public Category(int EI,int want_need, String title, int value) {
//        this.EI = EI;
//        this.want_need = want_need;
//        this.title = title;
//        this.value = value;
//    }

//    public Category(int EI, int want_need, String title, int value, String cat_date, boolean cat_or_date) {
//        this.EI = EI;
//        this.want_need = want_need;
//        this.title = title;
//        this.value = value;
//        if(cat_or_date)
//            category = cat_date;
//        else
//            date = cat_date;
//
//    }

//    public Category(int id, boolean EI, String title, int value, String date) {
//        this.id = id;
//        this.EI = EI;
//        this.title = title;
//        this.value = value;
//        this.date = date;
//    }




    public int getWant_need() {
        return want_need;
    }

    public void setWant_need(int want_need) {
        this.want_need = want_need;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEI() {
        return EI;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEI(int EI) {
        this.EI = EI;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
