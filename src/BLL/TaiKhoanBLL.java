package BLL;

import java.sql.SQLException;

import DAL.TaiKhoanDAL;

public class TaiKhoanBLL {
    private TaiKhoanDAL taiKhoanDAL = new TaiKhoanDAL();

    public String login(String email, String matKhau) throws SQLException {
        return taiKhoanDAL.kiemTraDangNhap(email, matKhau);
    }

    public int signup(String tenTK, String email, String matKhau, String manv) {
        return taiKhoanDAL.dangKyTaiKhoan(tenTK, email, matKhau, manv);
    }

    public String getusername(String email) {
        return taiKhoanDAL.getUsername(email);
    }

    public String getmaVaiTro(String maNhanVien) {
        return taiKhoanDAL.getVaiTro(maNhanVien);
    }

    public boolean  updatetmatkhau(String email, String matKhauMoi) {
        return taiKhoanDAL.updateMatKhau(email, matKhauMoi);
    }

}