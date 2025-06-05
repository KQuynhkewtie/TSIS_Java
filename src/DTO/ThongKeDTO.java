package DTO;

import java.util.Date;

public class ThongKeDTO {
    private Date ngay;
    private String thang;
    private String nam;
    private String cumNgay;
    private double doanhThu;
    private int soHoaDon; // Thêm trường số hóa đơn
    private boolean isYearly;

    public ThongKeDTO() {
    }

    // Constructor cho thống kê theo ngày
    public ThongKeDTO(Date ngay, double doanhThu, int soHoaDon) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
        this.soHoaDon = soHoaDon;
    }

    // Constructor cho thống kê theo tháng (thêm flag để phân biệt)
    public ThongKeDTO(String thang, double doanhThu, int soHoaDon, boolean isMonth) {
        if (isMonth) {
            this.thang = thang;
        } else {
            this.cumNgay = thang; // Nếu không phải tháng thì coi là cụm ngày
        }
        this.doanhThu = doanhThu;
        this.soHoaDon = soHoaDon;
    }

    // Constructor cho thống kê theo năm
    public ThongKeDTO(String nam, double doanhThu, int soHoaDon) {
        this.nam = nam;
        this.doanhThu = doanhThu;
        this.soHoaDon = soHoaDon;
    }

//    public ThongKeDTO(String cumNgay, double doanhThu, int soHoaDon) {
//        this.cumNgay = cumNgay;
//        this.doanhThu = doanhThu;
//        this.soHoaDon = soHoaDon;
//    }

    // Getters và Setters
    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getCumNgay() {
        return cumNgay;
    }

    public void setCumNgay(String cumNgay) {
        this.cumNgay = cumNgay;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(int soHoaDon) {
        this.soHoaDon = soHoaDon;
    }
}