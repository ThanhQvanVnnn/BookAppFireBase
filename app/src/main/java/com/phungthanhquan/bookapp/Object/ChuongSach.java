package com.phungthanhquan.bookapp.Object;

import java.io.Serializable;

public class ChuongSach implements Serializable {
    private int id_sach;
    private int trang;
    private String tenChuongSach;

    public ChuongSach(int id_sach, int trang, String tenChuongSach) {
        this.id_sach = id_sach;
        this.trang = trang;
        this.tenChuongSach = tenChuongSach;
    }

    public int getTrang() {
        return trang;
    }

    public void setTrang(int trang) {
        this.trang = trang;
    }

    public String getTenChuongSach() {
        return tenChuongSach;
    }

    public void setTenChuongSach(String tenChuongSach) {
        this.tenChuongSach = tenChuongSach;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }
}
