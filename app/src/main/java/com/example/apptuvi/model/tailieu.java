package com.example.apptuvi.model;

import java.io.Serializable;

public class tailieu implements Serializable {
    private int anhamduong;
    private String tenamduong;

    public tailieu(int anhamduong, String tenamduong) {
        this.anhamduong = anhamduong;
        this.tenamduong = tenamduong;
    }

    public int getAnhamduong() {
        return anhamduong;
    }

    public void setAnhamduong(int anhamduong) {
        this.anhamduong = anhamduong;
    }

    public String getTenamduong() {
        return tenamduong;
    }

    public void setTenamduong(String tenamduong) {
        this.tenamduong = tenamduong;
    }
}
