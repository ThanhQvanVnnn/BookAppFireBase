package com.phungthanhquan.bookapp.Object;

public class BinhLuan {
    private String idNguoiBinhLuan ;
    private String anhDaiDien;
    private String tenNguoiBinhLuan;
    private int numStart;
    private String ngayBinhLuan;
    private String noiDung;

    public BinhLuan(String idNguoiBinhLuan, String anhDaiDien, String tenNguoiBinhLuan, int numStart, String ngayBinhLuan, String noiDung) {
        this.idNguoiBinhLuan = idNguoiBinhLuan;
        this.anhDaiDien = anhDaiDien;
        this.tenNguoiBinhLuan = tenNguoiBinhLuan;
        this.numStart = numStart;
        this.ngayBinhLuan = ngayBinhLuan;
        this.noiDung = noiDung;
    }

    public String getTenNguoiBinhLuan() {
        return tenNguoiBinhLuan;
    }

    public void setTenNguoiBinhLuan(String tenNguoiBinhLuan) {
        this.tenNguoiBinhLuan = tenNguoiBinhLuan;
    }

    public String getIdNguoiBinhLuan() {
        return idNguoiBinhLuan;
    }

    public void setIdNguoiBinhLuan(String idNguoiBinhLuan) {
        this.idNguoiBinhLuan = idNguoiBinhLuan;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public int getNumStart() {
        return numStart;
    }

    public void setNumStart(int numStart) {
        this.numStart = numStart;
    }

    public String getNgayBinhLuan() {
        return ngayBinhLuan;
    }

    public void setNgayBinhLuan(String ngayBinhLuan) {
        this.ngayBinhLuan = ngayBinhLuan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
