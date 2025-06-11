package DAL;

import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAL {

    public ArrayList<KhachHangDTO> getAllKhachHang() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getDouble("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT"),
                        rs.getString("CCCD")
                );
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public KhachHangDTO getKhachHangById(String maKH) {
        KhachHangDTO kh = null;
        String query = "SELECT * FROM KHACHHANG WHERE TRIM(MAKH) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maKH.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getDouble("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT"),
                        rs.getString("CCCD")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }

    public boolean insertKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO KHACHHANG (MAKH, HOTEN, SDT, CCCD) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getSdt());
            stmt.setString(4, kh.getCCCD());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHangDTO> getKhachHang(String keyword) {
        List<KhachHangDTO> danhSachKH = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG WHERE LOWER(HOTEN) LIKE ? OR LOWER(MAKH) LIKE ?  OR DIEMTICHLUY LIKE ? OR LOWER(LOAIKHACH) LIKE ? OR SDT LIKE ? OR CCCD LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            stmt.setString(2, "%" + keyword.toLowerCase() + "%");
            stmt.setString(3, "%" + keyword+ "%");
            stmt.setString(4, "%" + keyword.toLowerCase() + "%");
            stmt.setString(5, "%" + keyword+ "%");
            stmt.setString(6, "%" + keyword+ "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("MAKH"),
                        rs.getString("HOTEN"),
                        rs.getDouble("DIEMTICHLUY"),
                        rs.getString("LOAIKHACH"),
                        rs.getString("SDT"),
                        rs.getString("CCCD")
                );
                danhSachKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachKH;
    }

    public boolean updateKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE KHACHHANG SET HOTEN=?, DIEMTICHLUY=?, SDT=?, CCCD=? WHERE TRIM(MAKH) = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getHoTen());
            stmt.setDouble(2, kh.getDiemTichLuy());
            stmt.setString(3, kh.getSdt());
            stmt.setString(4, kh.getCCCD());
            stmt.setString(5, kh.getMaKH().trim());


            int rows = stmt.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteKhachHangById(String maKH) {
        String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int getTongKH() {
        String sql = "SELECT COUNT(*) FROM KHACHHANG";
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
