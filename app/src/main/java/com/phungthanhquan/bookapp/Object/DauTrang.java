package com.phungthanhquan.bookapp.Object;

import java.io.Serializable;

public class DauTrang implements Serializable {
    private String TenChuong;
    private int trang;
    private String DayTime;

    public DauTrang(String tenChuong, int trang, String dayTime) {
        TenChuong = tenChuong;
        this.trang = trang;
        DayTime = dayTime;
    }

    public String getTenChuong() {
        return TenChuong;
    }

    public void setTenChuong(String tenChuong) {
        TenChuong = tenChuong;
    }

    public int getTrang() {
        return trang;
    }

    public void setTrang(int trang) {
        this.trang = trang;
    }

    public String getDayTime() {
        return DayTime;
    }

    public void setDayTime(String dayTime) {
        DayTime = dayTime;
    }
}
