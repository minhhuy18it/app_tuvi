package com.example.apptuvi.model;

import java.io.Serializable;

public class Anthaitue implements Serializable {
    private String sao;

    public Anthaitue(String sao) {
        this.sao = sao;
    }

    public String getSao() {
        return sao;
    }

    public void setSao(String sao) {
        this.sao = sao;
    }
}
