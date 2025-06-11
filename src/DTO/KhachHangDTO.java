package DTO;


public class KhachHangDTO {
    private String maKH;
    private String hoTen;
    private double diemTichLuy;
    private String loaiKhach;
    private String sdt;
    private String CCCD;

    public KhachHangDTO() {}

   public KhachHangDTO(String maKH, String hoTen, String sdt, String CCCD) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.CCCD = CCCD;
    }

    public KhachHangDTO(String maKH, String hoTen, double diemTichLuy, String loaiKhach, String sdt, String CCCD) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.diemTichLuy = diemTichLuy;
        this.loaiKhach = loaiKhach;
        this.sdt = sdt;
        this.CCCD = CCCD;
    }
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public double getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(double diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public String getLoaiKhach() {
        return loaiKhach;
    }

    public void setLoaiKhach(String loaiKhach) {
        this.loaiKhach = loaiKhach;
    }


    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
}
