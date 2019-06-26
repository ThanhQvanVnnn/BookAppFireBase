package com.phungthanhquan.bookapp.Object;

public class Book {
    private int id_sach;
    private String hinhanh_sach;
    private String ten_sach;
    private String ten_tacgia;
    private String NXB;
    private int so_trang;
    private String ngayphathanh;
    private int giatien_sach;
    private int sosao_danhgia;
    private String noidung_sach;
    private int soluong_danhgia;

    public Book() {
    }

    public Book(int id_sach, String hinhanh_sach, String ten_sach, String ten_tacgia, String NXB, int so_trang, String ngayphathanh, int giatien_sach, int sosao_danhgia, String noidung_sach, int soluong_danhgia) {
        this.id_sach = id_sach;
        this.hinhanh_sach = hinhanh_sach;
        this.ten_sach = ten_sach;
        this.ten_tacgia = ten_tacgia;
        this.NXB = NXB;
        this.so_trang = so_trang;
        this.ngayphathanh = ngayphathanh;
        this.giatien_sach = giatien_sach;
        this.sosao_danhgia = sosao_danhgia;
        this.noidung_sach = noidung_sach;
        this.soluong_danhgia = soluong_danhgia;
    }

    public String getTen_tacgia() {
        return ten_tacgia;
    }

    public void setTen_tacgia(String ten_tacgia) {
        this.ten_tacgia = ten_tacgia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public int getSo_trang() {
        return so_trang;
    }

    public void setSo_trang(int so_trang) {
        this.so_trang = so_trang;
    }

    public String getNgayphathanh() {
        return ngayphathanh;
    }

    public void setNgayphathanh(String ngayphathanh) {
        this.ngayphathanh = ngayphathanh;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public String getHinhanh_sach() {
        return hinhanh_sach;
    }

    public void setHinhanh_sach(String hinhanh_sach) {
        this.hinhanh_sach = hinhanh_sach;
    }

    public String getTen_sach() {
        return ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        this.ten_sach = ten_sach;
    }

    public int getGiatien_sach() {
        return giatien_sach;
    }

    public void setGiatien_sach(int giatien_sach) {
        this.giatien_sach = giatien_sach;
    }

    public int getSosao_danhgia() {
        return sosao_danhgia;
    }

    public void setSosao_danhgia(int sosao_danhgia) {
        this.sosao_danhgia = sosao_danhgia;
    }

    public String getNoidung_sach() {
        return noidung_sach;
    }

    public void setNoidung_sach(String noidung_sach) {
        this.noidung_sach = noidung_sach;
    }

    public int getSoluong_danhgia() {
        return soluong_danhgia;
    }

    public void setSoluong_danhgia(int soluong_danhgia) {
        this.soluong_danhgia = soluong_danhgia;
    }
}
