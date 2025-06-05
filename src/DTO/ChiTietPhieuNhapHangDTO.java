package DTO;

import java.util.Date;

public class ChiTietPhieuNhapHangDTO {
    private String maPNH;
    private String maSP;
    private Date hsd;
    private String soLo;
    private int soLuongNhap;
    private double giaNhap;

    // Constructors
    public ChiTietPhieuNhapHangDTO() {}

    public ChiTietPhieuNhapHangDTO(String maPNH, String maSP, Date hsd, String soLo, int soLuongNhap, double giaNhap) {
        this.maPNH = maPNH;
        this.maSP = maSP;
        this.hsd = hsd;
        this.soLo = soLo;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }

    // Getters and Setters
    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String maPNH) {
        this.maPNH = maPNH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Date getHsd() {
        return hsd;
    }

    public void setHsd(Date hsd) {
        this.hsd = hsd;
    }

    public String getSoLo() {
        return soLo;
    }

    public void setSoLo(String soLo) {
        this.soLo = soLo;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}