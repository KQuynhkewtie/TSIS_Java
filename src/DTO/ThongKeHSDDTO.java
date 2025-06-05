package DTO;
import java.time.LocalDate;

public class ThongKeHSDDTO {
    private String maSP;
    private String tenSP;
    private String soLo;
    private LocalDate hanSuDung;
    private int soLuongCon;
    private String nhaCungUng;
    private LocalDate ngayNhapKho;

    public ThongKeHSDDTO(String maSP, String tenSP, String soLo, LocalDate hanSuDung,
                         int soLuongCon, String nhaCungUng, LocalDate ngayNhapKho) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLo = soLo;
        this.hanSuDung = hanSuDung;
        this.soLuongCon = soLuongCon;
        this.nhaCungUng = nhaCungUng;
        this.ngayNhapKho = ngayNhapKho;
    }

    // Getters and Setters
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getSoLo() { return soLo; }
    public void setSoLo(String soLo) { this.soLo = soLo; }

    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { this.hanSuDung = hanSuDung; }

    public int getSoLuongCon() { return soLuongCon; }
    public void setSoLuongCon(int soLuongCon) { this.soLuongCon = soLuongCon; }

    public String getNhaCungUng() { return nhaCungUng; }
    public void setNhaCungUng(String nhaCungUng) { this.nhaCungUng = nhaCungUng; }

    public LocalDate getNgayNhapKho() { return ngayNhapKho; }
    public void setNgayNhapKho(LocalDate ngayNhapKho) { this.ngayNhapKho = ngayNhapKho; }

}
