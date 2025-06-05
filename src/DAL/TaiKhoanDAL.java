package DAL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.*;



public class TaiKhoanDAL {
    public class MaHoaMatKhau {

        public static String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(password.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }

                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Lỗi thuật toán băm SHA-256", e);
            }
        }
    }

    public String kiemTraDangNhap(String email, String matKhau) {

        String sql = "SELECT MANV FROM TAIKHOAN WHERE LOWER(EMAIL) = ? AND MATKHAU = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim().toLowerCase());
            stmt.setString(2, MaHoaMatKhau.hashPassword(matKhau.trim()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String manv = rs.getString("MANV");
                return manv;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername(String email) {
        String sql = "SELECT username FROM taikhoan WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int dangKyTaiKhoan(String tenTK, String email, String matKhau, String manv) {
        String checkNV = "SELECT MANV FROM NHANVIEN WHERE TRIM(MANV) = ?";
        String checkEmail = "SELECT EMAIL FROM TAIKHOAN WHERE TRIM(EMAIL) = ?";
        String checkMaNVInTaiKhoan = "SELECT MANV FROM TAIKHOAN WHERE TRIM(MANV) = ?";
        String insertSQL = "INSERT INTO TAIKHOAN (username, EMAIL, MATKHAU, maNV) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmtCheckNV = conn.prepareStatement(checkNV);
             PreparedStatement stmtCheckEmail = conn.prepareStatement(checkEmail);
             PreparedStatement stmtCheckMaNVInTK = conn.prepareStatement(checkMaNVInTaiKhoan);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false);
            stmtCheckNV.setString(1, manv.trim());
            try (ResultSet rsNV = stmtCheckNV.executeQuery()) {
                if (!rsNV.next()) {
                    return -1;
                }
            }
            stmtCheckEmail.setString(1, email.trim().toLowerCase());
            try (ResultSet rsEmail = stmtCheckEmail.executeQuery()) {
                if (rsEmail.next()) {
                    return -2;
                }
            }

            stmtCheckMaNVInTK.setString(1, manv.trim());
            try (ResultSet rsMaNV = stmtCheckMaNVInTK.executeQuery()) {
                if (rsMaNV.next()) {
                    return -3;
                }
            }

            insertStmt.setString(1, tenTK.trim().toLowerCase());
            insertStmt.setString(2, email.trim().toLowerCase());
            insertStmt.setString(3, MaHoaMatKhau.hashPassword(matKhau));
            insertStmt.setString(4, manv.trim());

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                conn.commit();
                return 1;
            } else {
                conn.rollback();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



    public String getVaiTro(String maNhanVien) {
        String maVaiTro = null;
        String sql = "SELECT MAVT FROM NHANVIEN WHERE MANV = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maVaiTro = rs.getString("MAVT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maVaiTro;
    }

    public boolean updateMatKhau(String email, String matKhauMoi) {
        String sql = "UPDATE TAIKHOAN SET MATKHAU = ? WHERE LOWER(EMAIL) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            String matKhauMaHoa = MaHoaMatKhau.hashPassword(matKhauMoi.trim());

            stmt.setString(1, matKhauMaHoa);
            stmt.setString(2, email.trim().toLowerCase());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

