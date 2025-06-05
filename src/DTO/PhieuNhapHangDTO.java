package DTO;

import java.util.Date;
import java.util.List;

public class PhieuNhapHangDTO {
    private String maPNH;
    private String maNCU;
    private String maNhanVien;
    private Date ngayLapPhieu;
    private double thanhTien;
    private List<ChiTietPhieuNhapHangDTO> danhSachChiTiet;

    public PhieuNhapHangDTO(String maPNH, String maNCU, String maNhanVien, Date ngayLapPhieu, double thanhTien) {
        this.maPNH = maPNH;
        this.maNCU = maNCU;
        this.maNhanVien = maNhanVien;
        this.ngayLapPhieu = ngayLapPhieu;
        this.thanhTien = thanhTien;
    }

    // Getters and Setters
    public String getMaPNH() {
        return maPNH;
    }

    public void setMaPNH(String maPNH) {
        this.maPNH = maPNH;
    }

    public String getMaNCU() {
        return maNCU;
    }

    public void setMaNCU(String maNCU) {
        this.maNCU = maNCU;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayLapPhieu() {
        return ngayLapPhieu;
    }

    public void setNgayLapPhieu(Date ngayLapPhieu) {
        this.ngayLapPhieu = ngayLapPhieu;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public List<ChiTietPhieuNhapHangDTO> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public void setDanhSachChiTiet(List<ChiTietPhieuNhapHangDTO> danhSachChiTiet) {
        this.danhSachChiTiet = danhSachChiTiet;
    }
}