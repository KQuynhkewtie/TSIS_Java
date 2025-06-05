package DTO;

public class TaiKhoanDTO {
    private String username;
    private String matKhau;
    private String email;
    private String maNV;

    public TaiKhoanDTO() {}

    public TaiKhoanDTO( String username, String matKhau, String email, String maNhanVien) {

        this.username = username;
        this.matKhau = matKhau;
        this.email = email;
        this.maNV = maNhanVien;
    }

    public String getTenTK() { return username; }
    public void setTenTK(String username) { this.username = username; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMaNhanVien() { return maNV; }
    public void setMaNhanVien(String maNhanVien) { this.maNV = maNhanVien; }

}
