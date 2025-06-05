package DAL;

import DTO.SanPhamDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {
    // Lấy danh sách tất cả sản phẩm
    public List<SanPhamDTO> getAllSanPham() {
        List<SanPhamDTO> danhSachSP = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN"),
                        rs.getString("TRANGTHAI")
                );
                danhSachSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }

    public SanPhamDTO getSanPhamById(String maSP) {
        SanPhamDTO sp = null;
        String query = "SELECT * FROM SANPHAM WHERE TRIM(MASP) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maSP.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN"),
                        rs.getString("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }


    public boolean insertSanPham(SanPhamDTO sp) {
        String sql = "INSERT INTO SANPHAM (MASP, MAHSX, MALSP, TENSP, QUYCACHDONGGOI, SODANGKY, SOLUONG, GIABAN) VALUES (?, ?, ?, ?, ?, ?, ?, ? )";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getMaHSX());
            stmt.setString(3, sp.getMaLSP());
            stmt.setString(4, sp.getTenSP());
            stmt.setString(5, sp.getQuyCachDongGoi());
            stmt.setString(6, sp.getSoDangKy());
            stmt.setInt(7, sp.getsoluong());
            stmt.setDouble(8, sp.getGiaBan());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Tìm kiếm sản phẩm
    public List<SanPhamDTO> getSanPham(String keyword) {
        List<SanPhamDTO> danhSachSP = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM WHERE LOWER(TENSP) LIKE ? OR LOWER(MASP) LIKE ? OR LOWER(MALSP) LIKE ? OR LOWER(TRANGTHAI) LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            stmt.setString(2, "%" + keyword.toLowerCase()+ "%");
            stmt.setString(3, "%" + keyword.toLowerCase()+ "%");
            stmt.setString(4, "%" + keyword.toLowerCase()+ "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getString("MASP"),
                        rs.getString("MAHSX"),
                        rs.getString("MALSP"),
                        rs.getString("TENSP"),
                        rs.getString("QUYCACHDONGGOI"),
                        rs.getString("SODANGKY"),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIABAN"),
                        rs.getString("TRANGTHAI")
                );
                danhSachSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }



    public boolean updateSanPham(SanPhamDTO sp) {
        String sql = "UPDATE SANPHAM SET MALSP=?, MAHSX=?, TENSP=?, QUYCACHDONGGOI=?,  SODANGKY=?,  SOLUONG=?, GIABAN=?, TRANGTHAI = ? WHERE TRIM(MASP) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, sp.getMaLSP());
            stmt.setString(2, sp.getMaHSX());
            stmt.setString(3, sp.getTenSP());
            stmt.setString(4, sp.getQuyCachDongGoi());
            stmt.setString(5, sp.getSoDangKy());
            stmt.setInt(6, sp.getsoluong());
            stmt.setDouble(7, sp.getGiaBan());
            stmt.setString(8, sp.getTrangThai());
            stmt.setString(9, sp.getMaSP().trim());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa sản phẩm theo mã
    public boolean deleteSanPhamById(String maSP) {
        String sql = "DELETE FROM SANPHAM WHERE MASP = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getTongsp() {
        String sql = "SELECT COUNT(*) FROM SANPHAM";
        try (
                Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}