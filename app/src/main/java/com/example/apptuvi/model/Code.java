package com.example.apptuvi.model;

import java.io.Serializable;

/**
 *
 */
public class Code implements Serializable {

    private String idcode;
    private String code;
    private String trangthai;
    private int thang;

    public Code(String idcode, String code, String trangthai, int thang) {
        this.idcode = idcode;
        this.code = code;
        this.trangthai = trangthai;
        this.thang = thang;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", trangthai='" + trangthai + '\'' +
                '}';
    }
}
