package DAL;

import DTO.HoaDonDTO;
import DTO.ChiTietHoaDonDTO;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;


public class HoaDonDAL {
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;
    private DatabaseHelper dtb;

    // Kết nối database
    public void setConnection(Connection connection) {
        this.conn = connection;
    }

    // Lấy danh sách hóa đơn
    public List<HoaDonDTO> layDanhSachHoaDon() {
        List<HoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM HOADON";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MAHD").trim(),
                        rs.getString("MANV").trim(),
                        rs.getString("MAKH") != null ? rs.getString("MAKH").trim() : null,
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }

    // Tìm kiếm hóa đơn
    public List<HoaDonDTO> timKiemHoaDon(String keyword, String fromDate, String toDate,
                                         Double minAmount, Double maxAmount, String trangThai) {
        List<HoaDonDTO> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM HOADON WHERE 1=1");

        // Thêm điều kiện tìm kiếm theo mã (tự động nhận diện loại mã)
        if (keyword != null && !keyword.isEmpty()) {
            if (keyword.toUpperCase().startsWith("HD")) {
                sql.append(" AND MAHD LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NV")) {
                sql.append(" AND MANV LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("KH")) {
                sql.append(" AND MAKH LIKE ?");
                keyword = "%" + keyword + "%";
            } else {
                // Tìm kiếm trên tất cả các trường mã nếu không có prefix
                sql.append(" AND (MAHD LIKE ? OR MANV LIKE ? OR MAKH LIKE ?)");
                keyword = "%" + keyword + "%";
            }
        }

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND NGAYBAN >= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND NGAYBAN <= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (minAmount != null) {
            sql.append(" AND THANHTIEN >= ?");
        }

        if (maxAmount != null) {
            sql.append(" AND THANHTIEN <= ?");
        }

        // Thêm điều kiện lọc theo trạng thái
        if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equals(trangThai)) {
            String dbTrangThai = "BINH_THUONG";
            if ("Đã hủy".equals(trangThai)) {
                dbTrangThai = "DA_HUY";
            }
            sql.append(" AND TRANGTHAI = ?");
        }

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql.toString());
            // Xử lý tham số
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                if (keyword.toUpperCase().startsWith("%HD") ||
                        keyword.toUpperCase().startsWith("%NV") ||
                        keyword.toUpperCase().startsWith("%KH")) {
                    pst.setString(paramIndex++, keyword);
                } else {
                    pst.setString(paramIndex++, keyword);
                    pst.setString(paramIndex++, keyword);
                    pst.setString(paramIndex++, keyword);
                }
            }

            if (fromDate != null && !fromDate.isEmpty()) {
                pst.setString(paramIndex++, fromDate);
            }

            if (toDate != null && !toDate.isEmpty()) {
                pst.setString(paramIndex++, toDate);
            }

            if (minAmount != null) {
                pst.setDouble(paramIndex++, minAmount);
            }

            if (maxAmount != null) {
                pst.setDouble(paramIndex++, maxAmount);
            }

            // Thêm tham số trạng thái nếu có
            if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equals(trangThai)) {
                String dbTrangThai = "BINH_THUONG";
                if ("Đã hủy".equals(trangThai)) {
                    dbTrangThai = "DA_HUY";
                }
                pst.setString(paramIndex++, dbTrangThai);
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                        rs.getString("MAHD"),
                        rs.getString("MANV"),
                        rs.getString("MAKH"),
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }

    // Lấy chi tiết hóa đơn
    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        List<ChiTietHoaDonDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETHOADON WHERE TRIM(MAHD) = ?";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon.trim());
            rs = pst.executeQuery();

            while (rs.next()) {
                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(
                        rs.getString("MAHD").trim(),
                        rs.getString("MASP").trim(),
                        rs.getInt("SOLUONG"),
                        rs.getDouble("GIA")
                );
                danhSach.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return danhSach;
    }
    public HoaDonDTO layHoaDonTheoMa(String maHoaDon) {
        String sql = "SELECT * FROM HOADON WHERE TRIM(MAHD) = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHoaDon.trim());
            rs = pst.executeQuery();

            if (rs.next()) {
                return new HoaDonDTO(
                        rs.getString("MAHD"),
                        rs.getString("MANV"),
                        rs.getString("MAKH"),
                        rs.getDate("NGAYBAN"),
                        rs.getDouble("THANHTIEN"),
                        rs.getString("TRANGTHAI")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public Map<String, String> layDanhSachTenSanPham(List<String> danhSachMaSP) {
        Map<String, String> result = new HashMap<>();
        if (danhSachMaSP == null || danhSachMaSP.isEmpty()) {
            return result;
        }

        // Chuẩn hóa danh sách mã SP: trim() + uppercase
        List<String> danhSachMaSPChuanHoa = danhSachMaSP.stream()
                .map(maSP -> maSP.trim().toUpperCase())
                .collect(Collectors.toList());

        String placeholders = String.join(",", Collections.nCopies(danhSachMaSPChuanHoa.size(), "?"));
        String sql = "SELECT MASP, TENSP FROM SANPHAM WHERE UPPER(TRIM(MASP)) IN (" + placeholders + ")";

        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);

            // Truyền tham số đã chuẩn hóa
            for (int i = 0; i < danhSachMaSPChuanHoa.size(); i++) {
                pst.setString(i + 1, danhSachMaSPChuanHoa.get(i));
            }

            rs = pst.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("MASP").trim(), rs.getString("TENSP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public boolean kiemTraMaHDTonTai(String maHD) {
        String sql = "SELECT 1 FROM HOADON WHERE TRIM(MAHD) = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maHD.trim());
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean kiemTraNhanVienTonTai(String maNV) {
        String sql = "SELECT 1 FROM NHANVIEN WHERE TRIM(MANV) = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, maNV.trim());

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraKhachHangTonTai(String maKH) {
        String sql = "SELECT 1 FROM KHACHHANG WHERE TRIM(MAKH) = ?";
        try {
            conn = DatabaseHelper.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maKH.trim());
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean themHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // Kiểm tra trùng mã hóa đơn
            String checkHDSql = "SELECT 1 FROM HOADON WHERE TRIM(MAHD) = ? FOR UPDATE NOWAIT";
            try (PreparedStatement pst = conn.prepareStatement(checkHDSql)) {
                pst.setString(1, hd.getMaHoaDon().trim());
                if (pst.executeQuery().next()) {
                    throw new SQLException("Mã hóa đơn đã tồn tại");
                }
            }

            // Thêm hóa đơn chính
            String sqlHD = "INSERT INTO HOADON (MAHD, MANV, MAKH, NGAYBAN, THANHTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstHD = conn.prepareStatement(sqlHD)) {
                pstHD.setString(1, hd.getMaHoaDon().trim());
                pstHD.setString(2, hd.getMaNhanVien().trim());
                pstHD.setString(3, hd.getMaKH() != null ? hd.getMaKH().trim() : null);
                pstHD.setDate(4, new java.sql.Date(hd.getNgayBan().getTime()));
                pstHD.setDouble(5, hd.getThanhTien());
                pstHD.setString(6, hd.getTrangThai());
                pstHD.executeUpdate();
            }

            // Thêm chi tiết hóa đơn
            String sqlCT = "INSERT INTO CHITIETHOADON (MAHD, MASP, SOLUONG, GIA) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstCT = conn.prepareStatement(sqlCT)) {
                for (ChiTietHoaDonDTO ct : danhSachChiTiet) {

                    pstCT.setString(1, ct.getMaHoaDon().trim());
                    pstCT.setString(2, ct.getMaSanPham().trim());
                    pstCT.setInt(3, ct.getSoLuong());
                    pstCT.setDouble(4, ct.getGia());
                    pstCT.addBatch();
                }
                pstCT.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Lỗi khi rollback: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }

    public boolean capNhatHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        Connection connection = null;
        try {
            connection = DatabaseHelper.getConnection();
            connection.setAutoCommit(false);

            // CHUẨN HÓA TẤT CẢ MÃ (trim cả dữ liệu đầu vào và dữ liệu database)
            String maHD = hd.getMaHoaDon().trim();
            String maNV = hd.getMaNhanVien().trim();
            String maKH = hd.getMaKH() != null ? hd.getMaKH().trim() : null;


            // 1. Cập nhật hóa đơn - Sử dụng RTRIM để xử lý khoảng trắng trong database
            String sqlHD = "UPDATE HOADON SET MANV = RTRIM(?), MAKH = RTRIM(?), NGAYBAN = ?, THANHTIEN = ? WHERE RTRIM(MAHD) = ?";
            try (PreparedStatement pstHD = connection.prepareStatement(sqlHD)) {
                pstHD.setString(1, maNV);
                if (maKH != null && !maKH.isEmpty()) {
                    pstHD.setString(2, maKH);
                } else {
                    pstHD.setNull(2, Types.VARCHAR);
                }
                pstHD.setDate(3, new java.sql.Date(hd.getNgayBan().getTime()));
                pstHD.setDouble(4, hd.getThanhTien());
                pstHD.setString(5, maHD);

                int updatedRows = pstHD.executeUpdate();
                if (updatedRows == 0) {
                    throw new SQLException("Không tìm thấy hóa đơn với mã: " + maHD);
                }
            }

            // 2. Xóa chi tiết cũ - Sử dụng RTRIM
            String sqlDelete = "DELETE FROM CHITIETHOADON WHERE RTRIM(MAHD) = ?";
            try (PreparedStatement pstDel = connection.prepareStatement(sqlDelete)) {
                pstDel.setString(1, maHD);
                pstDel.executeUpdate();
            }

            // 3. Thêm chi tiết mới
            String sqlInsert = "INSERT INTO CHITIETHOADON (MAHD, MASP, SOLUONG, GIA) VALUES (RTRIM(?), RTRIM(?), ?, ?)";
            try (PreparedStatement pstIns = connection.prepareStatement(sqlInsert)) {
                for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
                    pstIns.setString(1, maHD);
                    pstIns.setString(2, ct.getMaSanPham().trim());
                    pstIns.setInt(3, ct.getSoLuong());
                    pstIns.setDouble(4, ct.getGia());
                    pstIns.addBatch();
                }
                pstIns.executeBatch();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Hiển thị thông báo lỗi chi tiết hơn
            String errorMsg = "Lỗi SQL: " + e.getMessage() + "\n";
            errorMsg += "Mã lỗi: " + e.getErrorCode() + "\n";
            errorMsg += "Trạng thái SQL: " + e.getSQLState();

            JOptionPane.showMessageDialog(null, errorMsg, "Lỗi Database Chi Tiết", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean huyHoaDon(String maHoaDon) {
        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // 3. Cập nhật trạng thái hóa đơn
            String sqlUpdate = "UPDATE HOADON SET TRANGTHAI = 'DA_HUY' WHERE TRIM(MAHD) = ?";
            try (PreparedStatement pst = conn.prepareStatement(sqlUpdate)) {
                pst.setString(1, maHoaDon.trim());
                pst.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    //    // Đóng kết nối
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double getDoanhThunamhientai() {
        String sql = "SELECT SUM(THANHTIEN) FROM HOADON WHERE EXTRACT(YEAR FROM NGAYBAN) = EXTRACT(YEAR FROM SYSDATE)";
        try (
                Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}