package com.example.apptuvi.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThongTin implements Serializable {
    private String ten;
    private String ngay;
    private String thang;
    private String nam;
    private String gio;
    private String phut;
    private String gioiTinh;

    public ThongTin() {
    }

    public ThongTin(String ten, String ngay, String thang, String nam, String gio, String phut, String gioiTinh) {
        this.ten = ten;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
        this.gio = gio;
        this.phut = phut;
        this.gioiTinh = gioiTinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getPhut() {
        return phut;
    }

    public void setPhut(String phut) {
        this.phut = phut;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return ten + ", " + gioiTinh +
                ", ngày " + ngay +
                "/" + thang +
                "/" + nam + '\'' +
                ", " + gio + "h" +
                ", " + phut + " phút";
    }

    public String giophut () {
        int i = Integer.parseInt(phut);
        if (i <= 9) {
            return gio + "0"+phut;
        }
        return gio + phut;
    }

    public String convertSolar2Lunar () {
        return ChuyenDuongSangAm.convertSolar2Lunar(Integer.parseInt(ngay),Integer.parseInt(thang),Integer.parseInt(nam),7.0);
    }

    public String namam () {
        return ChuyenDuongSangAm.namam(Integer.parseInt(ngay),Integer.parseInt(thang),Integer.parseInt(nam),7.0);
    }

    public String thangam () {
        return ChuyenDuongSangAm.thangam(Integer.parseInt(ngay),Integer.parseInt(thang),Integer.parseInt(nam),7.0);
    }

    public String ngayam () {
        return ChuyenDuongSangAm.ngayam(Integer.parseInt(ngay),Integer.parseInt(thang),Integer.parseInt(nam),7.0);
    }

    public long subDate() throws Exception {
        String dateFist= "1900-01-01";
        String dateBirth= nam+"-"+thang+"-"+ngay;
        Date date1;
        Date date2;
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dates.parse(dateBirth);
        date2 = dates.parse(dateFist);
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        return differenceDates;
    }
}
