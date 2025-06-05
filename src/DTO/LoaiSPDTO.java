package DTO;

public class LoaiSPDTO {

    private String maLSP;
    private String tenLSP;
    private String trangThai;

    public LoaiSPDTO() {}

    public LoaiSPDTO(String maLSP, String tenLSP) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
    }

    public LoaiSPDTO(String maLSP, String tenLSP, String trangThai) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
        this.trangThai = trangThai;
    }

    public String getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }

    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}