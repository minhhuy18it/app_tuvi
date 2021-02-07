package com.example.apptuvi.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class AnCung implements Serializable {
    private ArrayList<String> list;
    private String thang;

    public AnCung() {
    }

    public AnCung(ArrayList<String> list, String thang) {
        this.list = list;
        this.thang = thang;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "{"
                + "\"list\":" + list
                + ", \"thang\":\"" + thang + "\""
                + "}";
    }
}
