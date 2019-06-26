package com.phungthanhquan.bookapp.Object;

public class User {
    private String idUser;
    private String tenUser;
    private String anhDaiDien;
    private float tongSoTien;
    private int tongSoSach;
    private int soNguoiTheoDoi;
    private int soNguoiDangTheoDoi;
    private int soSachYeuThich;
    private int soSachDaDoc;

    public User(String idUser, String tenUser, String anhDaiDien, float tongSoTien, int tongSoSach
            , int soNguoiTheoDoi, int soNguoiDangTheoDoi, int soSachYeuThich, int soSachDaDoc) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.anhDaiDien = anhDaiDien;
        this.tongSoTien = tongSoTien;
        this.tongSoSach = tongSoSach;
        this.soNguoiTheoDoi = soNguoiTheoDoi;
        this.soNguoiDangTheoDoi = soNguoiDangTheoDoi;
        this.soSachYeuThich = soSachYeuThich;
        this.soSachDaDoc = soSachDaDoc;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public float getTongSoTien() {
        return tongSoTien;
    }

    public void setTongSoTien(float tongSoTien) {
        this.tongSoTien = tongSoTien;
    }

    public int getTongSoSach() {
        return tongSoSach;
    }

    public void setTongSoSach(int tongSoSach) {
        this.tongSoSach = tongSoSach;
    }

    public int getSoNguoiTheoDoi() {
        return soNguoiTheoDoi;
    }

    public void setSoNguoiTheoDoi(int soNguoiTheoDoi) {
        this.soNguoiTheoDoi = soNguoiTheoDoi;
    }

    public int getSoNguoiDangTheoDoi() {
        return soNguoiDangTheoDoi;
    }

    public void setSoNguoiDangTheoDoi(int soNguoiDangTheoDoi) {
        this.soNguoiDangTheoDoi = soNguoiDangTheoDoi;
    }

    public int getSoSachYeuThich() {
        return soSachYeuThich;
    }

    public void setSoSachYeuThich(int soSachYeuThich) {
        this.soSachYeuThich = soSachYeuThich;
    }

    public int getSoSachDaDoc() {
        return soSachDaDoc;
    }

    public void setSoSachDaDoc(int soSachDaDoc) {
        this.soSachDaDoc = soSachDaDoc;
    }
}
