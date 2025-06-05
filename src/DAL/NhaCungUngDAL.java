package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.NhaCungUngDTO;

public class NhaCungUngDAL {
    public ArrayList<NhaCungUngDTO> getAllNCU() {
        ArrayList<NhaCungUngDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGUNG";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getString("TRANGTHAI")
                );
                ds.add(ncu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public NhaCungUngDTO getNCUById(String maNCU) {
        NhaCungUngDTO ncu = null;
        String query = "SELECT * FROM NHACUNGUNG WHERE TRIM(MANCU) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maNCU.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getString("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ncu;
    }

    public boolean insertncu(NhaCungUngDTO ncu) {
        String sql = "INSERT INTO NHACUNGUNG (MANCU, TENNCU, MASOTHUE, DIACHI, SDT, EMAIL) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ncu.getMaNCU());
            stmt.setString(2, ncu.getTenNCU());
            stmt.setString(3, ncu.getMaSoThue());
            stmt.setString(4, ncu.getDiaChi());
            stmt.setString(5, ncu.getSdt());
            stmt.setString(6, ncu.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhaCungUngDTO> getncu(String keyword) {
        List<NhaCungUngDTO> danhSachNCU = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGUNG WHERE LOWER(TENNCU) LIKE ? OR LOWER(MANCU) LIKE ? OR MASOTHUE LIKE ? OR LOWER(DIACHI) LIKE ? OR SDT LIKE ? OR LOWER(EMAIL) LIKE ? OR LOWER(TRANGTHAI) LIKE?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            stmt.setString(2, "%" + keyword.toLowerCase() + "%");
            stmt.setString(3, "%" + keyword + "%");
            stmt.setString(4, "%" + keyword.toLowerCase() + "%");
            stmt.setString(5, "%" + keyword + "%");
            stmt.setString(6, "%" + keyword.toLowerCase() + "%");
            stmt.setString(7, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NhaCungUngDTO ncu = new NhaCungUngDTO(
                        rs.getString("MANCU"),
                        rs.getString("TENNCU"),
                        rs.getString("MASOTHUE"),
                        rs.getString("DIACHI"),
                        rs.getString("SDT"),
                        rs.getString("EMAIL"),
                        rs.getString("TRANGTHAI")
                );
                danhSachNCU.add(ncu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCU;
    }


    public boolean updateNCU(NhaCungUngDTO ncu) {
        String sql = "UPDATE NHACUNGUNG SET TENNCU=?, MASOTHUE=?, DIACHI=?, SDT=?, EMAIL=?,TRANGTHAI = ?  WHERE TRIM(MANCU) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ncu.getTenNCU());
            stmt.setString(2, ncu.getMaSoThue());
            stmt.setString(3, ncu.getDiaChi());
            stmt.setString(4, ncu.getSdt());
            stmt.setString(5, ncu.getEmail());
            stmt.setString(6, ncu.getTrangThai());
            stmt.setString(7, ncu.getMaNCU().trim());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteNCUById(String maNCU) {
        String sql = "DELETE FROM NHACUNGUNG WHERE MANCU = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNCU);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}