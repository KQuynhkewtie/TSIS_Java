package DTO;


public class NhaCungUngDTO {
    private String maNCU;
    private String tenNCU;
    private String maSoThue;
    private String diaChi;
    private String sdt;
    private String email;
    private String trangThai;


    public NhaCungUngDTO() {}


    public NhaCungUngDTO(String maNCU, String tenNCU, String maSoThue, String diaChi, String sdt, String email, String trangThai) {
        this.maNCU = maNCU;
        this.tenNCU = tenNCU;
        this.maSoThue = maSoThue;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.trangThai = trangThai;
    }

    public NhaCungUngDTO(String maNCU, String tenNCU, String maSoThue, String diaChi, String sdt, String email) {
        this.maNCU = maNCU;
        this.tenNCU = tenNCU;
        this.maSoThue = maSoThue;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }


    public String getMaNCU() {
        return maNCU;
    }

    public void setMaNCU(String maNCU) {
        this.maNCU = maNCU;
    }

    public String getTenNCU() {
        return tenNCU;
    }

    public void setTenNCU(String tenNCU) {
        this.tenNCU = tenNCU;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
