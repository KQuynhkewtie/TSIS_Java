package DTO;

public class ChiTietHoaDonDTO {
    private String maHoaDon;
    private String maSanPham;
    private int soLuongNhap;
    private double giaNhap;

    public ChiTietHoaDonDTO() {}

    public ChiTietHoaDonDTO(String maHoaDon, String maSanPham,  int soLuongNhap, double giaNhap) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }

    // Getter và Setter
    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuongNhap; }
    public void setSoLuong(int soLuong) { this.soLuongNhap = soLuong; }

    public double getGia() { return giaNhap; }
    public void setGia(double gia) { this.giaNhap = gia; }

}