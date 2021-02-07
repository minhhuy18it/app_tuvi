package com.example.apptuvi.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apptuvi.R;
import com.example.apptuvi.model.ThongTin;

import java.util.ArrayList;
import java.util.Calendar;

public class ItemCenterActivity extends AppCompatActivity {
    TextView txtgioiam,txtcuc,txtnamduong,txtthangduong,txtngayduong,txtgioduong,txtmang,txtchumenh,txtchuthan;
    TextView txtnamam,txtthangam,txtngayam,txtgioam,txtcannamam,txtcanthangam,txtcanngayam,txtcangioam,txtcansanhtu,txtconnha;
    TextView txtconnhaphuquy,txtsaotrongnam,txttuoiam,txthantrongnam,txttamtai,txtnamhungnien,txtcannamxem,txthoangoc,txtkimlau;
    TextView txtvannien,txtthaitue,txthovaten;
    Spinner spnnamxem;
    ThongTin thongTin;
    ArrayList<String> listyear;
    String namAm;
    Integer chigio = 0,chingay = 0,chithang = 0,chinam = 0,cansanhtu =0,namhientai = 0,tuoiam = 0;
    String namduong ;
    String namsinh;
    String giophutsinh;
    String[] arrThangAm = {"Dần", "Mẹo" , "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu"};
    String[] arrNamSinh;
    String canDuong,chiDuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_center);

        anhxacode();
        addControls();
        mang();
        chumenh();
        cansanhtu();
        tamtai();
        setNgay();
        addEvents();
        setCanChiNamDuong();
        setHungNien();
        setOcHoang();
        setKimLau();
        setVanNien();
        setThaitue();
        txthovaten.setText(thongTin.getTen());
    }

    private void addEvents() {
        spnnamxem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                namhientai = Integer.parseInt(listyear.get(position));
                tuoiam = (namhientai - Integer.parseInt(thongTin.getNam())) + 1;
                txttuoiam.setText(String.valueOf(tuoiam));
                tamtai();
                setCanChiNamDuong();
                setHungNien();
                setOcHoang();
                setKimLau();
                setVanNien();
                setThaitue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    // 6/2/2020 setCanChiNamDuong setHungNien setOcHoang setKimLau()
    private void setThaitue() {
        String[] arrThaiTue = {"Trực", "Xung", "Hại", "Phá", "Hình"};
        String thaiTue = "";
        String[] arrThaiTueTheoChi = getResources().getStringArray(R.array.arrThaiTueCungLon);
        int namSinh = AnsaoActivity.linearSearch(arrThangAm,arrNamSinh[1]);
        int namXem = AnsaoActivity.linearSearch(arrThangAm,chiDuong);
        int i = 0;
        for (int j = namXem*5 ;  j <= (namXem*5)+4 ; j++) {
            if (arrThangAm[namSinh].equals(arrThaiTueTheoChi[j])) thaiTue = thaiTue + arrThaiTue[i]+" ";
            i++;
        }
        txtthaitue.setText(thaiTue.trim());
    }

    private void setVanNien(){
        String[] arrVanNien = getResources().getStringArray(R.array.arrVanNien);
        int namSinh = AnsaoActivity.linearSearch(arrThangAm,arrNamSinh[1]);
        int namXem = AnsaoActivity.linearSearch(arrThangAm,chiDuong);
        if (namXem == 0) namXem++;
        int i = (namXem >= namSinh) ? namXem-namSinh : (namXem+11)-namSinh;
        System.out.println(namSinh+" "+namXem+" "+i);
        txtvannien.setText(arrVanNien[i]);
    }

    private void setKimLau(){
        int tuoi = Integer.parseInt(txttuoiam.getText().toString());
        String s = "";
        switch (tuoi%9) {
            case 1:
                s = "Kim lâu Thân";
                break;
            case 3:
                s = " Kim lâu Thê";
                break;
            case 6:
                s = "Kim lâu Tử";
                break;
            case 8:
                s = "Kim lâu Súc";
                break;
        }

        txtkimlau.setText(s);
    }

    private void setOcHoang(){
        String[] arrOcHoang = getResources().getStringArray(R.array.arrOcHoang);
        int tuoi = Integer.parseInt(txttuoiam.getText().toString());

        txthoangoc.setText(arrOcHoang[tuoi-1]);
    }

    private void setCanChiNamDuong() {
        String[] arrCan = getResources().getStringArray(R.array.arrCan);
        int namGoc = 2010;
        int namChon = Integer.parseInt(spnnamxem.getSelectedItem().toString());
        int n = (int) Math.ceil((Math.abs(namChon - namGoc)*1.0/60));

        if (namChon >= namGoc) {
            namGoc = namGoc + 60*n;
        }

        int i = 12 - ((namGoc-namChon)%12);
        if (i == 12) {
            i = 0;
        }
        chiDuong = arrThangAm[i];

        int can = namChon%10;
        int j = (can >= 4) ? can-4 : can+6;
        canDuong = arrCan[j];
        txtcannamxem.setText(canDuong+" "+chiDuong);
    }

    private void setHungNien() {
        String kq = "";
        int n = AnsaoActivity.linearSearch(arrThangAm,arrNamSinh[1]);
        if (thongTin.getGioiTinh().equals("Nam")) {
            int i = (n<=4) ? n+7 : n-5;
            kq = arrThangAm[i];
        } else {
            int[] arr = {1,3,5,7,9,11};
            if (n>=6) {
                kq = arrThangAm[n-arr[n-6]];
            } else {
                int i = 0;
                switch (n) {
                    case 0:
                        i = n+11;
                        break;
                    case 1:
                        i = n+9;
                        break;
                    case 2:
                        i = n+7;
                        break;
                    case 3:
                        i = n+5;
                        break;
                    case 4:
                        i = n+3;
                        break;
                    case 5:
                        i = n+1;
                        break;
                }
                kq = arrThangAm[i];
            }
        }

        txtnamhungnien.setText(kq);
    }

    public void setNgay() {
        try {
            String[] arrCan = getResources().getStringArray(R.array.arrCan);
            String s = arrCan[(int) thongTin.subDate()% 10];
            int n = (int) (thongTin.subDate()%12);
            int i = (n>=4) ? n-4 : n+8;
            txtcanngayam.setText(s +" "+ arrThangAm[i]);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    private void tamtai() {
        Integer nam = Integer.parseInt(spnnamxem.getSelectedItem().toString());
        namduong = String.valueOf(nam % 60);

        if (giophutsinh.equals("Thân")){
            if (namduong.equals("6") || namduong.equals("18") || namduong.equals("30") || namduong.equals("42") || namduong.equals("54")){
                txttamtai.setText("năm 1");
            }else if (namduong.equals("7") || namduong.equals("19") || namduong.equals("31") || namduong.equals("43") || namduong.equals("55")){
                txttamtai.setText("năm 2");
            }else if (namduong.equals("8") || namduong.equals("20") || namduong.equals("32") || namduong.equals("44") || namduong.equals("56")){
                txttamtai.setText("năm 3");
            } else {
                txttamtai.setText("");
            }
        }else if (giophutsinh.equals("Hợi") || giophutsinh.equals("Mẹo") || giophutsinh.equals("Mùi")){
            if (namduong.equals("9") || namduong.equals("21") || namduong.equals("33") || namduong.equals("45") || namduong.equals("57")){
                txttamtai.setText("năm 1");
            }else if (namduong.equals("10") || namduong.equals("22") || namduong.equals("34") || namduong.equals("46") || namduong.equals("58")){
                txttamtai.setText("năm 2");
            }else if (namduong.equals("11") || namduong.equals("23") || namduong.equals("35") || namduong.equals("47") || namduong.equals("59")){
                txttamtai.setText("năm 3");
            } else {
                txttamtai.setText("");
            }
        }else if (giophutsinh.equals("Dần") || giophutsinh.equals("Ngọ") || giophutsinh.equals("Tuất")){
            if (namduong.equals("0") || namduong.equals("12") || namduong.equals("24") || namduong.equals("36") || namduong.equals("48")){
                txttamtai.setText("năm 1");
            }else if (namduong.equals("1") || namduong.equals("13") || namduong.equals("25") || namduong.equals("37") || namduong.equals("49")){
                txttamtai.setText("năm 2");
            }else if (namduong.equals("2") || namduong.equals("14") || namduong.equals("26") || namduong.equals("38") || namduong.equals("50")){
                txttamtai.setText("năm 3");
            } else {
                txttamtai.setText("");
            }
        }else if (giophutsinh.equals("Tỵ") || giophutsinh.equals("Dậu") || giophutsinh.equals("Sửu")){
            if (namduong.equals("3") || namduong.equals("15") || namduong.equals("27") || namduong.equals("39") || namduong.equals("51")){
                txttamtai.setText("năm 1");
            }else if (namduong.equals("4") || namduong.equals("16") || namduong.equals("28") || namduong.equals("40") || namduong.equals("52")){
                txttamtai.setText("năm 2");
            }else if (namduong.equals("5") || namduong.equals("17") || namduong.equals("29") || namduong.equals("41") || namduong.equals("53")){
                txttamtai.setText("năm 3");
            } else {
                txttamtai.setText("");
            }
        }else if (giophutsinh.equals("Tý") || giophutsinh.equals("Thìn")){
            if (namduong.equals("6") || namduong.equals("18") || namduong.equals("30") || namduong.equals("42") || namduong.equals("54")){
                txttamtai.setText("năm 1");
            }else if (namduong.equals("7") || namduong.equals("19") || namduong.equals("31") || namduong.equals("43") || namduong.equals("55")){
                txttamtai.setText("năm 2");
            }else if (namduong.equals("8") || namduong.equals("20") || namduong.equals("32") || namduong.equals("44") || namduong.equals("56")){
                txttamtai.setText("năm 3");
            } else {
                txttamtai.setText("");
            }
        } else {
            txttamtai.setText("");
        }
    }

    private void cansanhtu() {
        if (giophutsinh.equals("Tý") || giophutsinh.equals("Tỵ")){
            chigio = 16;
        }else if(giophutsinh.equals("Sửu") || giophutsinh.equals("Tuất") || giophutsinh.equals("Hợi")){
            chigio = 6;
        }else if (giophutsinh.equals("Dần")){
            chigio = 7;
        }else if (giophutsinh.equals("Mẹo") || giophutsinh.equals("Ngọ")){
            chigio = 10;
        }else if (giophutsinh.equals("Thìn") || giophutsinh.equals("Dậu")){
            chigio = 9;
        }else if (giophutsinh.equals("Mùi") || giophutsinh.equals("Thân")){
            chigio = 8;
        }
        Intent intent = getIntent();
        if (intent.hasExtra("ThongTin")) {
            ThongTin thongTin = (ThongTin) intent.getSerializableExtra("ThongTin");
            if (thongTin.ngayam().equals("1") || thongTin.ngayam().equals("19")){
                chingay = 5;
            }else if (thongTin.ngayam().equals("2") || thongTin.ngayam().equals("21")){
                chingay = 10;
            }else if (thongTin.ngayam().equals("3") || thongTin.ngayam().equals("23") || thongTin.ngayam().equals("7") || thongTin.ngayam().equals("9") || thongTin.ngayam().equals("16") || thongTin.ngayam().equals("13") || thongTin.ngayam().equals("28")){
                chingay = 8;
            }else if (thongTin.ngayam().equals("4") || thongTin.ngayam().equals("20") || thongTin.ngayam().equals("6") || thongTin.ngayam().equals("25")){
                chingay = 15;
            }else if (thongTin.ngayam().equals("5") || thongTin.ngayam().equals("8") || thongTin.ngayam().equals("10") || thongTin.ngayam().equals("29")){
                chingay = 16;
            }else if (thongTin.ngayam().equals("11") || thongTin.ngayam().equals("17") || thongTin.ngayam().equals("22") || thongTin.ngayam().equals("24")){
                chingay = 9;
            }else if (thongTin.ngayam().equals("12") || thongTin.ngayam().equals("14") || thongTin.ngayam().equals("15")){
                chingay = 17;
            }else if (thongTin.ngayam().equals("18") || thongTin.ngayam().equals("26")){
                chingay = 18;
            }else if (thongTin.ngayam().equals("27")){
                chingay = 7;
            }else if (thongTin.ngayam().equals("30")){
                chingay = 6;
            }

            if (thongTin.thangam().equals("1")){
                chithang = 6;
            }else if (thongTin.thangam().equals("2")){
                chithang = 7;
            }else if (thongTin.thangam().equals("3") || thongTin.thangam().equals("9")){
                chithang = 18;
            }else if (thongTin.thangam().equals("4") || thongTin.thangam().equals("7") || thongTin.thangam().equals("11")){
                chithang = 9;
            }else if (thongTin.thangam().equals("5") || thongTin.thangam().equals("12")){
                chithang = 5;
            }else if (thongTin.thangam().equals("6")){
                chithang = 16;
            }else if (thongTin.thangam().equals("8")){
                chithang = 15;
            }else if (thongTin.thangam().equals("10")){
                chithang = 8;
            }

            if (namsinh.equals("Giáp Tý") || namsinh.equals("Canh Thìn") || namsinh.equals("Tân Mẹo") || namsinh.equals("Quý Mẹo") || namsinh.equals("Mậu Thìn") || namsinh.equals("Giáp Dần")){
                chinam = 12;
            }else if (namsinh.equals("Ất Sửu") || namsinh.equals("Tân Mùi") || namsinh.equals("Quý Dậu") || namsinh.equals("Đinh Sửu") || namsinh.equals("Mậu Dần") || namsinh.equals("Nhâm Ngọ") || namsinh.equals("Kỷ Sửu") || namsinh.equals("Bính Thìn") || namsinh.equals("Ất Mẹo") || namsinh.equals("Canh Thân") || namsinh.equals("Giáp Thìn")){
                chinam = 8;
            }else if (namsinh.equals("Bính Dần") || namsinh.equals("Tân Tỵ") || namsinh.equals("Bính Tuất") || namsinh.equals("Ất Mùi") || namsinh.equals("Đinh Tỵ") || namsinh.equals("Kỷ Mùi")){
                chinam = 6;
            }else if (namsinh.equals("Đinh Mẹo") || namsinh.equals("Nhâm Thân") || namsinh.equals("Quý Mùi") || namsinh.equals("Quý Tỵ") || namsinh.equals("Canh Tý") || namsinh.equals("Tân Sửu") || namsinh.equals("Ất Tỵ") || namsinh.equals("Quý Hợi")){
                chinam = 7;
            }else if (namsinh.equals("Kỷ Tỵ") || namsinh.equals("Giáp Thân") || namsinh.equals("Bính Thân") || namsinh.equals("Đinh Mùi") || namsinh.equals("Kỷ Dậu") || namsinh.equals("Canh Tuất") || namsinh.equals("Nhâm Tý") ||namsinh.equals("Quý Sửu")){
                chinam = 5;
            }else if (namsinh.equals("Canh Ngọ") || namsinh.equals("Ất Hợi") || namsinh.equals("Canh Dần") || namsinh.equals("Nhâm Dần")){
                chinam = 9;
            }else if (namsinh.equals("Giáp Tuất") || namsinh.equals("Ất Dậu") || namsinh.equals("Mậu Tý") || namsinh.equals("Giáp Ngọ")){
                chinam = 15;
            }else if (namsinh.equals("Bính Tý") || namsinh.equals("Đinh Hợi") || namsinh.equals("Tân Dậu")){
                chinam = 16;
            }else if (namsinh.equals("Kỷ Mẹo") || namsinh.equals("Kỷ Hợi") || namsinh.equals("Mậu Ngọ")){
                chinam = 19;
            }else if (namsinh.equals("Nhâm Thìn") || namsinh.equals("Nhâm Tuất")){
                chinam = 10;
            }else if (namsinh.equals("Đinh Dậu") || namsinh.equals("Mậu Tuất") || namsinh.equals("Mậu Thân")){
                chinam = 14;
            }else if (namsinh.equals("Bính Ngọ")){
                chinam = 13;
            }
        }

        cansanhtu = chigio + chingay + chithang + chinam;
        String x = String.valueOf(cansanhtu);
        txtcansanhtu.setText(x.substring(0, 1) + "." + x.substring(1, x.length())+" lượng");
    }

    private void chumenh() {
        arrNamSinh = namsinh.split(" ");
        if (arrNamSinh[1].equals("Tý")){
            txtchumenh.setText("Tham Lang");
            txtchuthan.setText("Linh Tinh");
        }else if (arrNamSinh[1].equals("Sửu")){
            txtchumenh.setText("Cự Môn");
            txtchuthan.setText("Thiên Tướng");
        }else if (arrNamSinh[1].equals("Dần")){
            txtchumenh.setText("Lộc Tồn");
            txtchuthan.setText("Thiên Lương");
        }else if (arrNamSinh[1].equals("Mẹo")){
            txtchumenh.setText("Văn Khúc");
            txtchuthan.setText("Thiên Đồng");
        }else if (arrNamSinh[1].equals("Thìn")){
            txtchumenh.setText("Liêm Trinh");
            txtchuthan.setText("Văn Xương");
        }else if (arrNamSinh[1].equals("Tỵ")){
            txtchumenh.setText("Vũ Khúc");
            txtchuthan.setText("Thiên Cơ");
        }else if (arrNamSinh[1].equals("Ngọ")){
            txtchumenh.setText("Phá Quân");
            txtchuthan.setText("Hỏa Tinh");
        }else if (arrNamSinh[1].equals("Mùi")){
            txtchumenh.setText("Vũ Khúc");
            txtchuthan.setText("Thiên Tướng");
        }else if (arrNamSinh[1].equals("Thân")){
            txtchumenh.setText("Liêm Trinh");
            txtchuthan.setText("Thiên Lương");
        }else if (arrNamSinh[1].equals("Dậu")){
            txtchumenh.setText("Văn Khúc");
            txtchuthan.setText("Thiên Đồng");
        }else if (arrNamSinh[1].equals("Tuất")){
            txtchumenh.setText("Lộc Tồn");
            txtchuthan.setText("Văn Xương");
        }else if (arrNamSinh[1].equals("Hợi")){
            txtchumenh.setText("Cự Môn");
            txtchuthan.setText("Thiên Cơ");
        }
    }

    private void mang() {
        Calendar calendar = Calendar.getInstance();
        namhientai = calendar.get(Calendar.YEAR);
        tuoiam = (namhientai - Integer.parseInt(thongTin.getNam())) + 1;
        txttuoiam.setText(String.valueOf(tuoiam));

        if (thongTin.getGioiTinh().equals("Nam")){
            if (tuoiam.equals(1) ||tuoiam.equals(10) ||tuoiam.equals(19) ||tuoiam.equals(28) ||tuoiam.equals(37) ||tuoiam.equals(46) ||tuoiam.equals(55) ||tuoiam.equals(64) ||tuoiam.equals(73) ||tuoiam.equals(82) ||tuoiam.equals(91) ||tuoiam.equals(100)){
                txtsaotrongnam.setText("La Hầu");
            }else if (tuoiam.equals(2) ||tuoiam.equals(11) ||tuoiam.equals(20) ||tuoiam.equals(29) ||tuoiam.equals(38) ||tuoiam.equals(47) ||tuoiam.equals(56) ||tuoiam.equals(65) ||tuoiam.equals(74) ||tuoiam.equals(83) ||tuoiam.equals(92) ||tuoiam.equals(101)){
                txtsaotrongnam.setText("Thổ Tú");
            }else if (tuoiam.equals(3) ||tuoiam.equals(12) ||tuoiam.equals(21) ||tuoiam.equals(30) ||tuoiam.equals(39) ||tuoiam.equals(48) ||tuoiam.equals(57) ||tuoiam.equals(66) ||tuoiam.equals(75) ||tuoiam.equals(84) ||tuoiam.equals(93) ||tuoiam.equals(102)){
                txtsaotrongnam.setText("Thủy Diệu");
            }else if (tuoiam.equals(4) ||tuoiam.equals(13) ||tuoiam.equals(22) ||tuoiam.equals(31) ||tuoiam.equals(40) ||tuoiam.equals(49) ||tuoiam.equals(58) ||tuoiam.equals(67) ||tuoiam.equals(76) ||tuoiam.equals(85) ||tuoiam.equals(94) ||tuoiam.equals(103)){
                txtsaotrongnam.setText("Thái Bạch");
            }else if (tuoiam.equals(5) ||tuoiam.equals(14) ||tuoiam.equals(23) ||tuoiam.equals(32) ||tuoiam.equals(41) ||tuoiam.equals(50) ||tuoiam.equals(59) ||tuoiam.equals(68) ||tuoiam.equals(77) ||tuoiam.equals(86) ||tuoiam.equals(95) ||tuoiam.equals(104)){
                txtsaotrongnam.setText("Thái Dương");
            }else if (tuoiam.equals(6) ||tuoiam.equals(15) ||tuoiam.equals(24) ||tuoiam.equals(33) ||tuoiam.equals(42) ||tuoiam.equals(51) ||tuoiam.equals(60) ||tuoiam.equals(69) ||tuoiam.equals(78) ||tuoiam.equals(87) ||tuoiam.equals(96) ||tuoiam.equals(105)){
                txtsaotrongnam.setText("Vân Hớn");
            }else if (tuoiam.equals(7) ||tuoiam.equals(16) ||tuoiam.equals(25) ||tuoiam.equals(34) ||tuoiam.equals(43) ||tuoiam.equals(52) ||tuoiam.equals(61) ||tuoiam.equals(70) ||tuoiam.equals(79) ||tuoiam.equals(88) ||tuoiam.equals(97) ||tuoiam.equals(106)){
                txtsaotrongnam.setText("Kế Đô");
            }else if (tuoiam.equals(8) ||tuoiam.equals(17) ||tuoiam.equals(26) ||tuoiam.equals(35) ||tuoiam.equals(44) ||tuoiam.equals(53) ||tuoiam.equals(62) ||tuoiam.equals(71) ||tuoiam.equals(80) ||tuoiam.equals(89) ||tuoiam.equals(98) ||tuoiam.equals(107)){
                txtsaotrongnam.setText("Thái Âm");
            }else if (tuoiam.equals(9) ||tuoiam.equals(18) ||tuoiam.equals(27) ||tuoiam.equals(36) ||tuoiam.equals(45) ||tuoiam.equals(54) ||tuoiam.equals(63) ||tuoiam.equals(72) ||tuoiam.equals(81) ||tuoiam.equals(90) ||tuoiam.equals(99) ||tuoiam.equals(108)){
                txtsaotrongnam.setText("Mộc Đức");
            }
        }else {
            if (tuoiam.equals(1) ||tuoiam.equals(10) ||tuoiam.equals(19) ||tuoiam.equals(28) ||tuoiam.equals(37) ||tuoiam.equals(46) ||tuoiam.equals(55) ||tuoiam.equals(64) ||tuoiam.equals(73) ||tuoiam.equals(82) ||tuoiam.equals(91) ||tuoiam.equals(100)){
                txtsaotrongnam.setText("Kế Đô");
            }else if (tuoiam.equals(2) ||tuoiam.equals(11) ||tuoiam.equals(20) ||tuoiam.equals(29) ||tuoiam.equals(38) ||tuoiam.equals(47) ||tuoiam.equals(56) ||tuoiam.equals(65) ||tuoiam.equals(74) ||tuoiam.equals(83) ||tuoiam.equals(92) ||tuoiam.equals(101)){
                txtsaotrongnam.setText("Vân Hớn");
            }else if (tuoiam.equals(3) ||tuoiam.equals(12) ||tuoiam.equals(21) ||tuoiam.equals(30) ||tuoiam.equals(39) ||tuoiam.equals(48) ||tuoiam.equals(57) ||tuoiam.equals(66) ||tuoiam.equals(75) ||tuoiam.equals(84) ||tuoiam.equals(93) ||tuoiam.equals(102)){
                txtsaotrongnam.setText("Mộng Đức");
            }else if (tuoiam.equals(4) ||tuoiam.equals(13) ||tuoiam.equals(22) ||tuoiam.equals(31) ||tuoiam.equals(40) ||tuoiam.equals(49) ||tuoiam.equals(58) ||tuoiam.equals(67) ||tuoiam.equals(76) ||tuoiam.equals(85) ||tuoiam.equals(94) ||tuoiam.equals(103)){
                txtsaotrongnam.setText("Thái Âm");
            }else if (tuoiam.equals(5) ||tuoiam.equals(14) ||tuoiam.equals(23) ||tuoiam.equals(32) ||tuoiam.equals(41) ||tuoiam.equals(50) ||tuoiam.equals(59) ||tuoiam.equals(68) ||tuoiam.equals(77) ||tuoiam.equals(86) ||tuoiam.equals(95) ||tuoiam.equals(104)){
                txtsaotrongnam.setText("Thổ Tú");
            }else if (tuoiam.equals(6) ||tuoiam.equals(15) ||tuoiam.equals(24) ||tuoiam.equals(33) ||tuoiam.equals(42) ||tuoiam.equals(51) ||tuoiam.equals(60) ||tuoiam.equals(69) ||tuoiam.equals(78) ||tuoiam.equals(87) ||tuoiam.equals(96) ||tuoiam.equals(105)){
                txtsaotrongnam.setText("La Hầu");
            }else if (tuoiam.equals(7) ||tuoiam.equals(16) ||tuoiam.equals(25) ||tuoiam.equals(34) ||tuoiam.equals(43) ||tuoiam.equals(52) ||tuoiam.equals(61) ||tuoiam.equals(70) ||tuoiam.equals(79) ||tuoiam.equals(88) ||tuoiam.equals(97) ||tuoiam.equals(106)){
                txtsaotrongnam.setText("Thái Dương");
            }else if (tuoiam.equals(8) ||tuoiam.equals(17) ||tuoiam.equals(26) ||tuoiam.equals(35) ||tuoiam.equals(44) ||tuoiam.equals(53) ||tuoiam.equals(62) ||tuoiam.equals(71) ||tuoiam.equals(80) ||tuoiam.equals(89) ||tuoiam.equals(98) ||tuoiam.equals(107)){
                txtsaotrongnam.setText("Thái Bạch");
            }else if (tuoiam.equals(9) ||tuoiam.equals(18) ||tuoiam.equals(27) ||tuoiam.equals(36) ||tuoiam.equals(45) ||tuoiam.equals(54) ||tuoiam.equals(63) ||tuoiam.equals(72) ||tuoiam.equals(81) ||tuoiam.equals(90) ||tuoiam.equals(99) ||tuoiam.equals(108)){
                txtsaotrongnam.setText("Thủy Diệu");
            }
        }

        if (thongTin.getGioiTinh().equals("Nam")){
            if (tuoiam.equals(1) ||tuoiam.equals(9) ||tuoiam.equals(10) ||tuoiam.equals(18) ||tuoiam.equals(27) ||tuoiam.equals(36) ||tuoiam.equals(45) ||tuoiam.equals(54) ||tuoiam.equals(63) ||tuoiam.equals(72) ||tuoiam.equals(81) ||tuoiam.equals(89) ||tuoiam.equals(90) ||tuoiam.equals(98)){
                txthantrongnam.setText("Huỳnh Tuyền");
            }else if (tuoiam.equals(2) ||tuoiam.equals(11) ||tuoiam.equals(19) ||tuoiam.equals(20) ||tuoiam.equals(28) ||tuoiam.equals(37) ||tuoiam.equals(46) ||tuoiam.equals(55) ||tuoiam.equals(64) ||tuoiam.equals(73) ||tuoiam.equals(82) ||tuoiam.equals(91) ||tuoiam.equals(100) ||tuoiam.equals(99)){
                txthantrongnam.setText("Tam Kheo");
            }else if (tuoiam.equals(3) ||tuoiam.equals(12) ||tuoiam.equals(21) ||tuoiam.equals(29) ||tuoiam.equals(30) ||tuoiam.equals(38) ||tuoiam.equals(47) ||tuoiam.equals(56) ||tuoiam.equals(65) ||tuoiam.equals(74) ||tuoiam.equals(83) ||tuoiam.equals(92) ||tuoiam.equals(101)){
                txthantrongnam.setText("Ngũ Mộ");
            }else if (tuoiam.equals(4) ||tuoiam.equals(13) ||tuoiam.equals(22) ||tuoiam.equals(31) ||tuoiam.equals(39) ||tuoiam.equals(40) ||tuoiam.equals(48) ||tuoiam.equals(57) ||tuoiam.equals(66) ||tuoiam.equals(75) ||tuoiam.equals(84) ||tuoiam.equals(93) ||tuoiam.equals(102)){
                txthantrongnam.setText("Thiên Tinh");
            }else if (tuoiam.equals(5) ||tuoiam.equals(14) ||tuoiam.equals(23) ||tuoiam.equals(32) ||tuoiam.equals(41) ||tuoiam.equals(49) ||tuoiam.equals(50) ||tuoiam.equals(58) ||tuoiam.equals(67) ||tuoiam.equals(76) ||tuoiam.equals(85) ||tuoiam.equals(94) ||tuoiam.equals(103)){
                txthantrongnam.setText("Tán Tận");
            }else if (tuoiam.equals(6) ||tuoiam.equals(15) ||tuoiam.equals(24) ||tuoiam.equals(33) ||tuoiam.equals(42) ||tuoiam.equals(51) ||tuoiam.equals(59) ||tuoiam.equals(60) ||tuoiam.equals(68) ||tuoiam.equals(77) ||tuoiam.equals(86) ||tuoiam.equals(95) ||tuoiam.equals(104)){
                txthantrongnam.setText("Thiên La");
            }else if (tuoiam.equals(7) ||tuoiam.equals(16) ||tuoiam.equals(25) ||tuoiam.equals(34) ||tuoiam.equals(43) ||tuoiam.equals(52) ||tuoiam.equals(61) ||tuoiam.equals(69) ||tuoiam.equals(70) ||tuoiam.equals(78) ||tuoiam.equals(87) ||tuoiam.equals(96) ||tuoiam.equals(105)){
                txthantrongnam.setText("Địa Võng");
            }else if (tuoiam.equals(8) ||tuoiam.equals(17) ||tuoiam.equals(26) ||tuoiam.equals(35) ||tuoiam.equals(44) ||tuoiam.equals(53) ||tuoiam.equals(62) ||tuoiam.equals(71) ||tuoiam.equals(79) ||tuoiam.equals(80) ||tuoiam.equals(88) ||tuoiam.equals(97) ||tuoiam.equals(106)){
                txthantrongnam.setText("Diêm Vương");
            }
        }else {
            if (tuoiam.equals(1) ||tuoiam.equals(9) ||tuoiam.equals(10) ||tuoiam.equals(18) ||tuoiam.equals(27) ||tuoiam.equals(36) ||tuoiam.equals(45) ||tuoiam.equals(54) ||tuoiam.equals(63) ||tuoiam.equals(72) ||tuoiam.equals(81) ||tuoiam.equals(89) ||tuoiam.equals(90) ||tuoiam.equals(98)){
                txthantrongnam.setText("Tán Tận");
            }else if (tuoiam.equals(2) ||tuoiam.equals(11) ||tuoiam.equals(19) ||tuoiam.equals(20) ||tuoiam.equals(28) ||tuoiam.equals(37) ||tuoiam.equals(46) ||tuoiam.equals(55) ||tuoiam.equals(64) ||tuoiam.equals(73) ||tuoiam.equals(82) ||tuoiam.equals(91) ||tuoiam.equals(100) ||tuoiam.equals(99)){
                txthantrongnam.setText("Thiên Tinh");
            }else if (tuoiam.equals(3) ||tuoiam.equals(12) ||tuoiam.equals(21) ||tuoiam.equals(29) ||tuoiam.equals(30) ||tuoiam.equals(38) ||tuoiam.equals(47) ||tuoiam.equals(56) ||tuoiam.equals(65) ||tuoiam.equals(74) ||tuoiam.equals(83) ||tuoiam.equals(92) ||tuoiam.equals(101)){
                txthantrongnam.setText("Ngũ Mộ");
            }else if (tuoiam.equals(4) ||tuoiam.equals(13) ||tuoiam.equals(22) ||tuoiam.equals(31) ||tuoiam.equals(39) ||tuoiam.equals(40) ||tuoiam.equals(48) ||tuoiam.equals(57) ||tuoiam.equals(66) ||tuoiam.equals(75) ||tuoiam.equals(84) ||tuoiam.equals(93) ||tuoiam.equals(102)){
                txthantrongnam.setText("Tam Kheo");
            }else if (tuoiam.equals(5) ||tuoiam.equals(14) ||tuoiam.equals(23) ||tuoiam.equals(32) ||tuoiam.equals(41) ||tuoiam.equals(49) ||tuoiam.equals(50) ||tuoiam.equals(58) ||tuoiam.equals(67) ||tuoiam.equals(76) ||tuoiam.equals(85) ||tuoiam.equals(94) ||tuoiam.equals(103)){
                txthantrongnam.setText("Huỳnh Tuyền");
            }else if (tuoiam.equals(6) ||tuoiam.equals(15) ||tuoiam.equals(24) ||tuoiam.equals(33) ||tuoiam.equals(42) ||tuoiam.equals(51) ||tuoiam.equals(59) ||tuoiam.equals(60) ||tuoiam.equals(68) ||tuoiam.equals(77) ||tuoiam.equals(86) ||tuoiam.equals(95) ||tuoiam.equals(104)){
                txthantrongnam.setText("Diêm Vương");
            }else if (tuoiam.equals(7) ||tuoiam.equals(16) ||tuoiam.equals(25) ||tuoiam.equals(34) ||tuoiam.equals(43) ||tuoiam.equals(52) ||tuoiam.equals(61) ||tuoiam.equals(69) ||tuoiam.equals(70) ||tuoiam.equals(78) ||tuoiam.equals(87) ||tuoiam.equals(96) ||tuoiam.equals(105)){
                txthantrongnam.setText("Địa Võng");
            }else if (tuoiam.equals(8) ||tuoiam.equals(17) ||tuoiam.equals(26) ||tuoiam.equals(35) ||tuoiam.equals(44) ||tuoiam.equals(53) ||tuoiam.equals(62) ||tuoiam.equals(71) ||tuoiam.equals(79) ||tuoiam.equals(80) ||tuoiam.equals(88) ||tuoiam.equals(97) ||tuoiam.equals(106)){
                txthantrongnam.setText("Thiên La");
            }
        }

        namAm = thongTin.namam();
        Integer mang = Integer.parseInt(namAm)%60;

        if (mang == 0 || mang == 1){
            txtmang.setText("Thạch Lựu Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 2 || mang == 3){
            txtmang.setText("Đại Hải Thủy");
            txtconnhaphuquy.setText("(Quan Lộc,Tận Khổ)");
            txtconnha.setText("Hắc Đế");
        }else if (mang == 4 || mang == 5){
            txtmang.setText("Hải Trung Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 6 || mang == 7){
            txtmang.setText("Lư Trung Hỏa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 8 || mang == 9){
            txtmang.setText("Đại Lâm Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Trường Mạng)");
        }else if (mang == 10 || mang == 11){
            txtmang.setText("Lộ Bàng Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 12 || mang == 13){
            txtmang.setText("Kiếm Phong Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 14 || mang == 15){
            txtmang.setText("Sơn Đầu Hỏa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 16 || mang == 17){
            txtmang.setText("Giang Hạ Thủy");
            txtconnha.setText("Hắc Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 18 || mang == 19){
            txtmang.setText("Thành Đầu Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 20 || mang == 21){
            txtmang.setText("Bạch Lạp Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(Trường Mạng)");
        }else if (mang == 22 || mang == 23){
            txtmang.setText("Dương Liễu Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Trường Mạng)");
        }else if (mang == 24 || mang == 25){
            txtmang.setText("Tuyền Trung Thủy");
            txtconnha.setText("Hắc Đế");
            txtconnhaphuquy.setText("(Từ Tánh,Phú Quý)");
        }else if (mang == 26 || mang == 27){
            txtmang.setText("Ốc Thượng Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 28 || mang == 29){
            txtmang.setText("Thích Lịch Hỏa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 30 || mang == 31){
            txtmang.setText("Tòng Bá Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Trường Mạng)");
        }else if (mang == 32 || mang == 33){
            txtmang.setText("Trường Lưu Thủy");
            txtconnha.setText("Hắc Đế");
            txtconnhaphuquy.setText("(Trường Mạng)");
        }else if (mang == 34 || mang == 35){
            txtmang.setText("Sa Trung Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(An Mạng,Phú Quý)");
        }else if (mang == 36 || mang == 37){
            txtmang.setText("Sơn Hạ Hỏa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }else if (mang == 38 || mang == 39){
            txtmang.setText("Bình Địa Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 40 || mang == 41){
            txtmang.setText("Bích Thượng Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Quan Lộc,Cô Quạnh)");
        }else if (mang == 42 || mang == 43){
            txtmang.setText("Kim Bạch Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 44 || mang == 45){
            txtmang.setText("Phúc Đăng Hoa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Tận Khổ)");
        }else if (mang == 46 || mang == 47){
            txtmang.setText("Thiên Hà Thủy");
            txtconnha.setText("Hắc Đế");
            txtconnhaphuquy.setText("(Tận Khổ)");
        }else if (mang == 48 || mang == 49){
            txtmang.setText("Đại Trạch Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Quan Lộc)");
        }else if (mang == 50 || mang == 51){
            txtmang.setText("Xoa Xuyến Kim");
            txtconnha.setText("Bạch Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 52 || mang == 53){
            txtmang.setText("Tang Đố Mộc");
            txtconnha.setText("Thanh Đế");
            txtconnhaphuquy.setText("(Quan Lộc,Tận Khổ)");
        }else if (mang == 54 || mang == 55){
            txtmang.setText("Đại Khê Thủy");
            txtconnha.setText("Hắc Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 56 || mang == 57){
            txtmang.setText("Sa Trung Thổ");
            txtconnha.setText("Huỳnh Đế");
            txtconnhaphuquy.setText("(Phú Quý)");
        }else if (mang == 58 || mang == 59){
            txtmang.setText("Thiên Thượng Hỏa");
            txtconnha.setText("Xích Đế");
            txtconnhaphuquy.setText("(Cô Quạnh)");
        }
    }

    private void addControls() {
        Intent intent = getIntent();
        if (intent.hasExtra("ThongTin")) {
            thongTin = (ThongTin) intent.getSerializableExtra("ThongTin");
            namAm = thongTin.namam();

            if (thongTin.namam().equals("1930") || thongTin.namam().equals("1990")){
                namsinh = "Canh Ngọ";
            }else if (thongTin.namam().equals("1931") || thongTin.namam().equals("1991")){
                namsinh = "Tân Mùi";
            }else if (thongTin.namam().equals("1932") || thongTin.namam().equals("1992")){
                namsinh = "Nhâm Thân";
            }else if (thongTin.namam().equals("1933") || thongTin.namam().equals("1993")){
                namsinh = "Quý Dậu";
            }else if (thongTin.namam().equals("1934") || thongTin.namam().equals("1994")){
                namsinh = "Giáp Tuất";
            }else if (thongTin.namam().equals("1935") || thongTin.namam().equals("1995")){
                namsinh = "Ất Hợi";
            }else if (thongTin.namam().equals("1936") || thongTin.namam().equals("1996")){
                namsinh = "Bính Tý";
            }else if (thongTin.namam().equals("1937") || thongTin.namam().equals("1997")){
                namsinh = "Đinh Sửu";
            }else if (thongTin.namam().equals("1938") || thongTin.namam().equals("1998")){
                namsinh = "Mậu Dần";
            }else if (thongTin.namam().equals("1939") || thongTin.namam().equals("1999")){
                namsinh = "Kỷ Mẹo";
            }else if (thongTin.namam().equals("1940") || thongTin.namam().equals("2000")){
                namsinh = "Canh Thìn";
            }else if (thongTin.namam().equals("1941") || thongTin.namam().equals("2001")){
                namsinh = "Tân Tỵ";
            }else if (thongTin.namam().equals("1942") || thongTin.namam().equals("2002")){
                namsinh = "Nhâm Ngọ";
            }else if (thongTin.namam().equals("1943") || thongTin.namam().equals("2003")){
                namsinh = "Quý Mùi";
            }else if (thongTin.namam().equals("1944") || thongTin.namam().equals("2004")){
                namsinh = "Giáp Thân";
            }else if (thongTin.namam().equals("1945") || thongTin.namam().equals("2005")){
                namsinh = "Ất Dậu";
            }else if (thongTin.namam().equals("1946") || thongTin.namam().equals("2006")){
                namsinh = "Bính Tuất";
            }else if (thongTin.namam().equals("1947") || thongTin.namam().equals("2007")){
                namsinh = "Đinh Hợi";
            }else if (thongTin.namam().equals("1948") || thongTin.namam().equals("2008")){
                namsinh = "Mậu Tý";
            }else if (thongTin.namam().equals("1949") || thongTin.namam().equals("2009")){
                namsinh = "Kỷ Sửu";
            }else if (thongTin.namam().equals("1950") || thongTin.namam().equals("2010")){
                namsinh = "Canh Dần";
            }else if (thongTin.namam().equals("1951") || thongTin.namam().equals("2011")){
                namsinh = "Tân Mẹo";
            }else if (thongTin.namam().equals("1952") || thongTin.namam().equals("2012")){
                namsinh = "Nhâm Thìn";
            }else if (thongTin.namam().equals("1953") || thongTin.namam().equals("2013")){
                namsinh = "Quý Tỵ";
            }else if (thongTin.namam().equals("1954") || thongTin.namam().equals("2014")){
                namsinh = "Giáp Ngọ";
            }else if (thongTin.namam().equals("1955") || thongTin.namam().equals("2015")){
                namsinh = "Ất Mùi";
            }else if (thongTin.namam().equals("1956") || thongTin.namam().equals("2016")){
                namsinh = "Bính Thân";
            }else if (thongTin.namam().equals("1957") || thongTin.namam().equals("2017")){
                namsinh = "Đinh Dậu";
            }else if (thongTin.namam().equals("1958") || thongTin.namam().equals("2018")){
                namsinh = "Mậu Tuất";
            }else if (thongTin.namam().equals("1959") || thongTin.namam().equals("2019")){
                namsinh = "Kỷ Hợi";
            }else if (thongTin.namam().equals("1960") || thongTin.namam().equals("2020")){
                namsinh = "Canh Tý";
            }else if (thongTin.namam().equals("1961") || thongTin.namam().equals("2021")){
                namsinh = "Tân Sửu";
            }else if (thongTin.namam().equals("1962") || thongTin.namam().equals("2022")){
                namsinh = "Nhâm Dần";
            }else if (thongTin.namam().equals("1963") || thongTin.namam().equals("2023")){
                namsinh = "Quý Mẹo";
            }else if (thongTin.namam().equals("1964") || thongTin.namam().equals("2024")){
                namsinh = "Giáp Thìn";
            }else if (thongTin.namam().equals("1965") || thongTin.namam().equals("2025")){
                namsinh = "Ất Tỵ";
            }else if (thongTin.namam().equals("1966") || thongTin.namam().equals("2026")){
                namsinh = "Bính Ngọ";
            }else if (thongTin.namam().equals("1967") || thongTin.namam().equals("2027")){
                namsinh = "Đinh Mùi";
            }else if (thongTin.namam().equals("1968") || thongTin.namam().equals("2028")){
                namsinh = "Mậu Thân";
            }else if (thongTin.namam().equals("1969") || thongTin.namam().equals("2029")){
                namsinh = "Kỷ Dậu";
            }else if (thongTin.namam().equals("1970") || thongTin.namam().equals("2030")){
                namsinh = "Canh Tuất";
            }else if (thongTin.namam().equals("1971") || thongTin.namam().equals("2031")){
                namsinh = "Tân Hợi";
            }else if (thongTin.namam().equals("1972") || thongTin.namam().equals("2032")){
                namsinh = "Nhâm Tý";
            }else if (thongTin.namam().equals("1973") || thongTin.namam().equals("2033")){
                namsinh = "Quý Sửu";
            }else if (thongTin.namam().equals("1974") || thongTin.namam().equals("2034")){
                namsinh = "Giáp Dần";
            }else if (thongTin.namam().equals("1975") || thongTin.namam().equals("2035")){
                namsinh = "Ất Mẹo";
            }else if (thongTin.namam().equals("1976") || thongTin.namam().equals("2036")){
                namsinh = "Bính Thìn";
            }else if (thongTin.namam().equals("1977") || thongTin.namam().equals("2037")){
                namsinh = "Đinh Tỵ";
            }else if (thongTin.namam().equals("1978") || thongTin.namam().equals("2038")){
                namsinh = "Mậu Ngọ";
            }else if (thongTin.namam().equals("1979") || thongTin.namam().equals("2039")){
                namsinh = "Kỷ Mùi";
            }else if (thongTin.namam().equals("1980") || thongTin.namam().equals("2040")){
                namsinh = "Canh Thân";
            }else if (thongTin.namam().equals("1981") || thongTin.namam().equals("2041")){
                namsinh = "Tân Dậu";
            }else if (thongTin.namam().equals("1982") || thongTin.namam().equals("2042")){
                namsinh = "Nhâm Tuất";
            }else if (thongTin.namam().equals("1983") || thongTin.namam().equals("2043")){
                namsinh = "Quý Hợi";
            }else if (thongTin.namam().equals("1984") || thongTin.namam().equals("2044")){
                namsinh = "Giáp Tý";
            }else if (thongTin.namam().equals("1985") || thongTin.namam().equals("2045")){
                namsinh = "Ất Sửu";
            }else if (thongTin.namam().equals("1986") || thongTin.namam().equals("2046")){
                namsinh = "Bính Dần";
            }else if (thongTin.namam().equals("1987") || thongTin.namam().equals("2047")){
                namsinh = "Đinh Mẹo";
            }else if (thongTin.namam().equals("1988") || thongTin.namam().equals("2048")){
                namsinh = "Mậu Thìn";
            }else if (thongTin.namam().equals("1989") || thongTin.namam().equals("2049")){
                namsinh = "Kỷ Tỵ";
            }

            if(thongTin.thangam().equals("1") || thongTin.thangam().equals("9")){
                if (Integer.parseInt(thongTin.giophut()) >= 420 && Integer.parseInt(thongTin.giophut()) <= 619){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 620 && Integer.parseInt(thongTin.giophut()) <= 819){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 820 && Integer.parseInt(thongTin.giophut()) <= 1019){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1020 && Integer.parseInt(thongTin.giophut()) <= 1219){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1220 && Integer.parseInt(thongTin.giophut()) <= 1419){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1420 && Integer.parseInt(thongTin.giophut()) <= 1619){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1620 && Integer.parseInt(thongTin.giophut()) <= 1819){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1820 && Integer.parseInt(thongTin.giophut()) <= 2019){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2020 && Integer.parseInt(thongTin.giophut()) <= 2219){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2220 || Integer.parseInt(thongTin.giophut()) <= 19){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 20 && Integer.parseInt(thongTin.giophut()) <= 219){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 220 && Integer.parseInt(thongTin.giophut()) <= 419){
                    giophutsinh = "Sửu";
                }
            }else if (thongTin.thangam().equals("3") || thongTin.thangam().equals("7")){
                if (Integer.parseInt(thongTin.giophut()) >= 430 && Integer.parseInt(thongTin.giophut()) < 629){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 630 && Integer.parseInt(thongTin.giophut()) <= 829){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 830 && Integer.parseInt(thongTin.giophut()) <= 1029){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1030 && Integer.parseInt(thongTin.giophut()) <= 1229){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1230 && Integer.parseInt(thongTin.giophut()) <= 1429){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1430 && Integer.parseInt(thongTin.giophut()) <= 1629){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1630 && Integer.parseInt(thongTin.giophut()) <= 1829){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1830 && Integer.parseInt(thongTin.giophut()) <= 2029){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2030 && Integer.parseInt(thongTin.giophut()) <= 2229){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2230 || Integer.parseInt(thongTin.giophut()) <= 29){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 30 && Integer.parseInt(thongTin.giophut()) <= 229){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 230 && Integer.parseInt(thongTin.giophut()) <= 429){
                    giophutsinh = "Sửu";
                }
            }else if (thongTin.thangam().equals("5")){
                if (Integer.parseInt(thongTin.giophut()) >= 520 && Integer.parseInt(thongTin.giophut()) <= 719){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 720 && Integer.parseInt(thongTin.giophut()) <= 919){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 920 && Integer.parseInt(thongTin.giophut()) <= 1119){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1120 && Integer.parseInt(thongTin.giophut()) <= 1319){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1320 && Integer.parseInt(thongTin.giophut()) <= 1519){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1520 && Integer.parseInt(thongTin.giophut()) <= 1719){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1720 && Integer.parseInt(thongTin.giophut()) <= 1919){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1920 && Integer.parseInt(thongTin.giophut()) <= 2119){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2120 && Integer.parseInt(thongTin.giophut()) <= 2319){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2320 || Integer.parseInt(thongTin.giophut()) <= 119){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 120 && Integer.parseInt(thongTin.giophut()) <= 319){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 320 && Integer.parseInt(thongTin.giophut()) <= 519){
                    giophutsinh = "Sửu";
                }
            }else if (thongTin.thangam().equals("2") || thongTin.thangam().equals("8") || thongTin.thangam().equals("10") || thongTin.thangam().equals("12")){
                if (Integer.parseInt(thongTin.giophut()) >= 400 && Integer.parseInt(thongTin.giophut()) <= 559){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 600 && Integer.parseInt(thongTin.giophut()) <= 759){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 800 && Integer.parseInt(thongTin.giophut()) <= 959){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1000 && Integer.parseInt(thongTin.giophut()) <= 1159){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1200 && Integer.parseInt(thongTin.giophut()) <= 1359){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1400 && Integer.parseInt(thongTin.giophut()) <= 1559){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1600 && Integer.parseInt(thongTin.giophut()) <= 1759){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1800 && Integer.parseInt(thongTin.giophut()) <= 1959){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2000 && Integer.parseInt(thongTin.giophut()) <= 2159){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2200 && Integer.parseInt(thongTin.giophut()) <= 2359){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 0 && Integer.parseInt(thongTin.giophut()) <= 159){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 200 && Integer.parseInt(thongTin.giophut()) <= 359){
                    giophutsinh = "Sửu";
                }
            }else if (thongTin.thangam().equals("4") || thongTin.thangam().equals("6")){
                if (Integer.parseInt(thongTin.giophut()) >= 440 && Integer.parseInt(thongTin.giophut()) <= 639){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 640 && Integer.parseInt(thongTin.giophut()) <= 839){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 840 && Integer.parseInt(thongTin.giophut()) <= 1039){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1040 && Integer.parseInt(thongTin.giophut()) <= 1239){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1240 && Integer.parseInt(thongTin.giophut()) <= 1439){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1440 && Integer.parseInt(thongTin.giophut()) <= 1639){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1640 && Integer.parseInt(thongTin.giophut()) <= 1839){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1840 && Integer.parseInt(thongTin.giophut()) <= 2039){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2040 && Integer.parseInt(thongTin.giophut()) <= 2239){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2240 || Integer.parseInt(thongTin.giophut()) <= 39){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 40 && Integer.parseInt(thongTin.giophut()) <= 239){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 240 && Integer.parseInt(thongTin.giophut()) <= 439){
                    giophutsinh = "Sửu";
                }
            }else if (thongTin.thangam().equals("11")){
                if (Integer.parseInt(thongTin.giophut()) >= 340 && Integer.parseInt(thongTin.giophut()) <= 539){
                    giophutsinh = "Dần";
                }else if (Integer.parseInt(thongTin.giophut()) >= 540 && Integer.parseInt(thongTin.giophut()) <= 739){
                    giophutsinh = "Mẹo";
                }else if (Integer.parseInt(thongTin.giophut()) >= 740 && Integer.parseInt(thongTin.giophut()) <= 939){
                    giophutsinh = "Thìn";
                }else if (Integer.parseInt(thongTin.giophut()) >= 940 && Integer.parseInt(thongTin.giophut()) <= 1139){
                    giophutsinh = "Tỵ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1140 && Integer.parseInt(thongTin.giophut()) <= 1339){
                    giophutsinh = "Ngọ";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1340 && Integer.parseInt(thongTin.giophut()) <= 1539){
                    giophutsinh = "Mùi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1540 && Integer.parseInt(thongTin.giophut()) <= 1739){
                    giophutsinh = "Thân";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1740 && Integer.parseInt(thongTin.giophut()) <= 1939){
                    giophutsinh = "Dậu";
                }else if (Integer.parseInt(thongTin.giophut()) >= 1940 && Integer.parseInt(thongTin.giophut()) <= 2139){
                    giophutsinh = "Tuất";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2140 && Integer.parseInt(thongTin.giophut()) <= 2339){
                    giophutsinh = "Hợi";
                }else if (Integer.parseInt(thongTin.giophut()) >= 2340 || Integer.parseInt(thongTin.giophut()) <= 139){
                    giophutsinh = "Tý";
                }else if (Integer.parseInt(thongTin.giophut()) >= 140 && Integer.parseInt(thongTin.giophut()) <= 339){
                    giophutsinh = "Sửu";
                }
            }

            String[] output = namsinh.split(" ");

            txtnamduong.setText(thongTin.getNam() + "");
            txtthangduong.setText(thongTin.getThang() + "");
            txtngayduong.setText(thongTin.getNgay() + "");
            txtgioduong.setText(thongTin.getGio() + ":" + thongTin.getPhut());

            txtnamam.setText(thongTin.namam() + "");
            txtthangam.setText(thongTin.thangam() + "");
            txtngayam.setText(thongTin.ngayam() + "");
            txtgioam.setText(giophutsinh);

            txtcannamam.setText(namsinh);

            if (output[0].equals("Giáp") || output[0].equals("Kỷ")){
                if (thongTin.thangam().equals("1")){
                    txtcanthangam.setText("Bính Dần");
                }else if (thongTin.thangam().equals("2")){
                    txtcanthangam.setText("Đinh Mẹo");
                }else if (thongTin.thangam().equals("3")){
                    txtcanthangam.setText("Mậu Thìn");
                }else if (thongTin.thangam().equals("4")){
                    txtcanthangam.setText("Kỷ Tỵ");
                }else if (thongTin.thangam().equals("5")){
                    txtcanthangam.setText("Canh Ngọ");
                }else if (thongTin.thangam().equals("6")){
                    txtcanthangam.setText("Tân Mùi");
                }else if (thongTin.thangam().equals("7")){
                    txtcanthangam.setText("Nhâm Thân");
                }else if (thongTin.thangam().equals("8")){
                    txtcanthangam.setText("Quý Dậu");
                }else if (thongTin.thangam().equals("9")){
                    txtcanthangam.setText("Giáp Tuất");
                }else if (thongTin.thangam().equals("10")){
                    txtcanthangam.setText("Ất Hợi");
                }else if (thongTin.thangam().equals("11")){
                    txtcanthangam.setText("Bính Tý");
                }else if (thongTin.thangam().equals("12")){
                    txtcanthangam.setText("Đinh Sửu");
                }
            }
            else if (output[0].equals("Ất") || output[0].equals("Canh")){
                if (thongTin.thangam().equals("1")){
                    txtcanthangam.setText("Mậu Dần");
                }else if (thongTin.thangam().equals("2")){
                    txtcanthangam.setText("Kỷ Mẹo");
                }else if (thongTin.thangam().equals("3")){
                    txtcanthangam.setText("Canh Thìn");
                }else if (thongTin.thangam().equals("4")){
                    txtcanthangam.setText("Tân Tỵ");
                }else if (thongTin.thangam().equals("5")){
                    txtcanthangam.setText("Nhâm Ngọ");
                }else if (thongTin.thangam().equals("6")){
                    txtcanthangam.setText("Quý Mùi");
                }else if (thongTin.thangam().equals("7")){
                    txtcanthangam.setText("Giáp Thân");
                }else if (thongTin.thangam().equals("8")){
                    txtcanthangam.setText("Ất Dậu");
                }else if (thongTin.thangam().equals("9")){
                    txtcanthangam.setText("Bính Tuất");
                }else if (thongTin.thangam().equals("10")){
                    txtcanthangam.setText("Đinh Hợi");
                }else if (thongTin.thangam().equals("11")){
                    txtcanthangam.setText("Mậu Tý");
                }else if (thongTin.thangam().equals("12")){
                    txtcanthangam.setText("Kỷ Sửu");
                }
            }
            else if (output[0].equals("Bính") || output[0].equals("Tân")){
                if (thongTin.thangam().equals("1")){
                    txtcanthangam.setText("Canh Dần");
                }else if (thongTin.thangam().equals("2")){
                    txtcanthangam.setText("Tân Mẹo");
                }else if (thongTin.thangam().equals("3")){
                    txtcanthangam.setText("Nhâm Thìn");
                }else if (thongTin.thangam().equals("4")){
                    txtcanthangam.setText("Quý Tỵ");
                }else if (thongTin.thangam().equals("5")){
                    txtcanthangam.setText("Giáp Ngọ");
                }else if (thongTin.thangam().equals("6")){
                    txtcanthangam.setText("Ất Mùi");
                }else if (thongTin.thangam().equals("7")){
                    txtcanthangam.setText("Bính Thân");
                }else if (thongTin.thangam().equals("8")){
                    txtcanthangam.setText("Đinh Dậu");
                }else if (thongTin.thangam().equals("9")){
                    txtcanthangam.setText("Mậu Tuất");
                }else if (thongTin.thangam().equals("10")){
                    txtcanthangam.setText("Kỷ Hợi");
                }else if (thongTin.thangam().equals("11")){
                    txtcanthangam.setText("Canh Tý");
                }else if (thongTin.thangam().equals("12")){
                    txtcanthangam.setText("Tân Sửu");
                }
            }
            else if (output[0].equals("Đinh") || output[0].equals("Nhâm")){
                if (thongTin.thangam().equals("1")){
                    txtcanthangam.setText("Nhâm Dần");
                }else if (thongTin.thangam().equals("2")){
                    txtcanthangam.setText("Quý Mẹo");
                }else if (thongTin.thangam().equals("3")){
                    txtcanthangam.setText("Giáp Thìn");
                }else if (thongTin.thangam().equals("4")){
                    txtcanthangam.setText("Ất Tỵ");
                }else if (thongTin.thangam().equals("5")){
                    txtcanthangam.setText("Bính Ngọ");
                }else if (thongTin.thangam().equals("6")){
                    txtcanthangam.setText("Đinh Mùi");
                }else if (thongTin.thangam().equals("7")){
                    txtcanthangam.setText("Mậu Thân");
                }else if (thongTin.thangam().equals("8")){
                    txtcanthangam.setText("Kỷ Dậu");
                }else if (thongTin.thangam().equals("9")){
                    txtcanthangam.setText("Canh Tuất");
                }else if (thongTin.thangam().equals("10")){
                    txtcanthangam.setText("Tân Hợi");
                }else if (thongTin.thangam().equals("11")){
                    txtcanthangam.setText("Nhâm Tý");
                }else if (thongTin.thangam().equals("12")){
                    txtcanthangam.setText("Quý Sửu");
                }
            }
            else if (output[0].equals("Mậu") || output[0].equals("Quý")){
                if (thongTin.thangam().equals("1")){
                    txtcanthangam.setText("Giáp Dần");
                }else if (thongTin.thangam().equals("2")){
                    txtcanthangam.setText("Ất Mẹo");
                }else if (thongTin.thangam().equals("3")){
                    txtcanthangam.setText("Bính Thìn");
                }else if (thongTin.thangam().equals("4")){
                    txtcanthangam.setText("Đinh Tỵ");
                }else if (thongTin.thangam().equals("5")){
                    txtcanthangam.setText("Mậu Ngọ");
                }else if (thongTin.thangam().equals("6")){
                    txtcanthangam.setText("Kỷ Mùi");
                }else if (thongTin.thangam().equals("7")){
                    txtcanthangam.setText("Canh Thân");
                }else if (thongTin.thangam().equals("8")){
                    txtcanthangam.setText("Tân Dậu");
                }else if (thongTin.thangam().equals("9")){
                    txtcanthangam.setText("Nhâm Tuất");
                }else if (thongTin.thangam().equals("10")){
                    txtcanthangam.setText("Quý Hợi");
                }else if (thongTin.thangam().equals("11")){
                    txtcanthangam.setText("Giáp Tý");
                }else if (thongTin.thangam().equals("12")){
                    txtcanthangam.setText("Ất Sửu");
                }
            }

            if (output[0].equals("Ất") || output[0].equals("Đinh") || output[0].equals("Kỷ") || output[0].equals("Tân") || output[0].equals("Quý")){
                if(thongTin.getGioiTinh().equals("Nam")){
                    txtgioiam.setText("Âm Nam");
                }
                else{
                    txtgioiam.setText("Âm Nữ");
                }
            }else {
                if(thongTin.getGioiTinh().equals("Nam")){
                    txtgioiam.setText("Dương Nam");
                }
                else{
                    txtgioiam.setText("Dương Nữ");
                }
            }
        }
    }

    private void anhxacode(){
        txtgioiam = findViewById(R.id.txtgioiam);
        txtcuc = findViewById(R.id.txtcuc);
        txtnamduong = findViewById(R.id.txtnamduong);
        txtthangduong = findViewById(R.id.txtthangduong);
        txtngayduong = findViewById(R.id.txtngayduong);
        txtgioduong = findViewById(R.id.txtgioduong);
        txtmang = findViewById(R.id.txtmang);
        txtchumenh = findViewById(R.id.txtchumenh);
        txtchuthan = findViewById(R.id.txtchuthan);
        txtngayam = findViewById(R.id.txtngayam);
        txtthangam = findViewById(R.id.txtthangam);
        txtnamam = findViewById(R.id.txtnamam);
        txtgioam = findViewById(R.id.txtgioam);
        txtcanthangam = findViewById(R.id.txtcanthangam);
        txtcannamam = findViewById(R.id.txtcannamam);
        txtcanngayam = findViewById(R.id.txtcanngayam);
        txtcangioam = findViewById(R.id.txtcangioam);
        txtcansanhtu = findViewById(R.id.txtcansanhtu);
        txtconnha = findViewById(R.id.txtconnha);
        txtconnhaphuquy = findViewById(R.id.txtconnhaphuquy);
        txtsaotrongnam = findViewById(R.id.txtsaotrongnam);
        txttuoiam = findViewById(R.id.txttuoiam);
        txthantrongnam = findViewById(R.id.txthantrongnam);
        txttamtai = findViewById(R.id.txttamtai);
        txtnamhungnien = findViewById(R.id.txtnamhungnien);
        txtcannamxem = findViewById(R.id.txtcannamxem);
        txthoangoc = findViewById(R.id.txthoangoc);
        txtkimlau = findViewById(R.id.txtkimlau);
        txtvannien = findViewById(R.id.txtvannien);
        txtthaitue = findViewById(R.id.txtthaitue);
        txthovaten = findViewById(R.id.txthovaten);
        spnnamxem = findViewById(R.id.spnnamxem);

        listyear = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 2000; i <= 2079; i++ ){
            listyear.add(String.valueOf(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listyear){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnnamxem.setAdapter(arrayAdapter);
        spnnamxem.setSelection(listyear.indexOf(""+calendar.get(Calendar.YEAR)));
    }
}