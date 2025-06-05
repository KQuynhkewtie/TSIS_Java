package DAL;

import DTO.HangSanXuatDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HangSanXuatDAL {
    // Lấy danh sách tất cả hãng sản xuất
    public List<HangSanXuatDTO> getAllHangSanXuat() {
        List<HangSanXuatDTO> danhSachHSX = new ArrayList<>();
        String sql = "SELECT * FROM HANGSANXUAT";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HangSanXuatDTO hsx = new HangSanXuatDTO(
                        rs.getString("MAHSX"),
                        rs.getString("TENHSX"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("TRANGTHAI")
                );
                danhSachHSX.add(hsx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHSX;
    }

    public HangSanXuatDTO getHSXById(String maHSX) {

        String query = "SELECT * FROM HANGSANXUAT WHERE TRIM(MAHSX) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maHSX.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new HangSanXuatDTO(
                            rs.getString("MAHSX"),
                            rs.getString("TENHSX"),
                            rs.getString("MASOTHUE"),
                            rs.getString("DIACHI"),
                            rs.getString("SDT"),
                            rs.getString("TRANGTHAI")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Thêm hãng sản xuất
    public boolean insertHangSanXuat(HangSanXuatDTO hsx) {
        String sql = "INSERT INTO HANGSANXUAT (MAHSX, TENHSX, MASOTHUE, DIACHI, SDT) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hsx.getMaHSX());
            stmt.setString(2, hsx.getTenHSX());
            stmt.setString(3, hsx.getMaSoThue());
            stmt.setString(4, hsx.getDiaChi());
            stmt.setString(5, hsx.getSdt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin hãng sản xuất
    public boolean updateHangSanXuat(HangSanXuatDTO hsx) {
        String sql = "UPDATE HANGSANXUAT SET TENHSX=?, MASOTHUE=?, DIACHI=?, SDT=?, TRANGTHAI = ? WHERE TRIM(MAHSX)=?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hsx.getTenHSX());
            stmt.setString(2, hsx.getMaSoThue());
            stmt.setString(3, hsx.getDiaChi());
            stmt.setString(4, hsx.getSdt());
            stmt.setString(5, hsx.getTrangThai());
            stmt.setString(6, hsx.getMaHSX());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa hãng sản xuất
    public  boolean deleteHangSanXuat(String maHSX) {
        String sql = "DELETE FROM HANGSANXUAT WHERE MAHSX = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHSX);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<HangSanXuatDTO> getHSX(String keyword) {
        List<HangSanXuatDTO> danhSachHSX = new ArrayList<>();
        String sql = "SELECT * FROM HANGSANXUAT WHERE LOWER(TENHSX) LIKE ? OR LOWER(MAHSX) LIKE ? OR MASOTHUE LIKE ? OR LOWER(DIACHI) LIKE ? OR SDT LIKE ? OR LOWER(TRANGTHAI) LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            stmt.setString(2, "%" + keyword.toLowerCase() + "%");
            stmt.setString(3, "%" + keyword + "%");
            stmt.setString(4, "%" + keyword.toLowerCase() + "%");
            stmt.setString(5, "%" + keyword+ "%");
            stmt.setString(6, "%" + keyword.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HangSanXuatDTO hsx = new HangSanXuatDTO(
                        rs.getString("MAHSX"),
                        rs.getString("TENHSX"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("TRANGTHAI")
                );
                danhSachHSX.add(hsx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHSX;
    }
}