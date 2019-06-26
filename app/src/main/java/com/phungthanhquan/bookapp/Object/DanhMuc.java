package com.phungthanhquan.bookapp.Object;

public class DanhMuc {
    private String idDanhMuc;
    private String backGround;
    private String tenDanhMuc;

    public DanhMuc(String idDanhMuc, String backGround, String tenDanhMuc) {
        this.idDanhMuc = idDanhMuc;
        this.backGround = backGround;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
