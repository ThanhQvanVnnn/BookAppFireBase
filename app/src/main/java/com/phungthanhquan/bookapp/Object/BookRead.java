package com.phungthanhquan.bookapp.Object;

import java.util.List;

public class BookRead {
    private int trangDangDoc;
    private String tenSach;
    private int id_sach;
    private List<ChuongSach> chuongSachList;

    public BookRead(int trangDangDoc, String tenSach, int id_sach, List<ChuongSach> chuongSachList) {
        this.trangDangDoc = trangDangDoc;
        this.tenSach = tenSach;
        this.id_sach = id_sach;
        this.chuongSachList = chuongSachList;
    }

    public int getTrangDangDoc() {
        return trangDangDoc;
    }

    public void setTrangDangDoc(int trangDangDoc) {
        this.trangDangDoc = trangDangDoc;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public List<ChuongSach> getChuongSachList() {
        return chuongSachList;
    }

    public void setChuongSachList(List<ChuongSach> chuongSachList) {
        this.chuongSachList = chuongSachList;
    }
}
