package DAL;

import DTO.PhieuNhapHangDTO;
import DTO.ChiTietPhieuNhapHangDTO;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PhieuNhapHangDAL {

    public List<ChiTietPhieuNhapHangDTO> layChiTietPhieuNhapHang(String maPNH) {
        return layChiTietPhieuNhapHang(maPNH, null);
    }

    // Lấy danh sách phiếu nhập hàng
    public List<PhieuNhapHangDTO> layDanhSachPhieuNhapHang() {
        List<PhieuNhapHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUNHAPHANG ORDER BY NGAYLAPPHIEU DESC";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                        rs.getString("MAPNH"),
                        rs.getString("MANCU"),
                        rs.getString("MANV"),
                        rs.getDate("NGAYLAPPHIEU"),
                        rs.getDouble("THANHTIEN")
                );
                danhSach.add(pnh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public PhieuNhapHangDTO layPhieuNhapHangTheoMa(String maPNH) {
        String sql = "SELECT * FROM PHIEUNHAPHANG WHERE MAPNH = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPNH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuNhapHangDTO(
                            rs.getString("MAPNH"),
                            rs.getString("MANCU"),
                            rs.getString("MANV"),
                            rs.getDate("NGAYLAPPHIEU"),
                            rs.getDouble("THANHTIEN")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm phiếu nhập hàng
    public boolean themPhieuNhapHang(PhieuNhapHangDTO pnh, Connection conn) {
        String sql = "INSERT INTO PHIEUNHAPHANG (MAPNH, MANCU, MANV, NGAYLAPPHIEU, THANHTIEN) VALUES (?, ?, ?, ?, ?)";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, pnh.getMaPNH());
            ps.setString(2, pnh.getMaNCU());
            ps.setString(3, pnh.getMaNhanVien());
            ps.setDate(4, new java.sql.Date(pnh.getNgayLapPhieu().getTime()));
            ps.setDouble(5, pnh.getThanhTien());

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    // Xóa phiếu nhập hàng
    public boolean xoaPhieuNhapHang(String maPNH, Connection conn) throws SQLException {
        String sql = "DELETE FROM PHIEUNHAPHANG WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPNH);
            return ps.executeUpdate() > 0;
        }
    }

    // Thêm các phương thức overload có tham số Connection
    public boolean capNhatPhieuNhapHang(PhieuNhapHangDTO pnh, Connection conn) throws SQLException {
        String sql = "UPDATE PHIEUNHAPHANG SET MANCU = ?, MANV = ?, NGAYLAPPHIEU = ?, THANHTIEN = ? WHERE TRIM(MAPNH) = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pnh.getMaNCU());
            ps.setString(2, pnh.getMaNhanVien());
            ps.setDate(3, new java.sql.Date(pnh.getNgayLapPhieu().getTime()));
            ps.setDouble(4, pnh.getThanhTien());
            ps.setString(5, pnh.getMaPNH().trim());
            return ps.executeUpdate() > 0;
        }
    }

    public List<ChiTietPhieuNhapHangDTO> layChiTietPhieuNhapHang(String maPNH, Connection conn) {
        List<ChiTietPhieuNhapHangDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maPNH);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                danhSach.add(new ChiTietPhieuNhapHangDTO(
                        rs.getString("MAPNH"),
                        rs.getString("MASP"),
                        rs.getDate("HSD"),
                        rs.getString("SOLO"),
                        rs.getInt("SLNHAP"),
                        rs.getDouble("GIANHAP")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
        return danhSach;
    }

    public boolean xoaChiTietPhieuNhapHang(String maPNH, String maSP, Connection conn) {
        String sql = "DELETE FROM CHITIETPHIEUNHAPHANG WHERE MAPNH = ? AND MASP = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maPNH);
            ps.setString(2, maSP);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    public boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct, Connection conn) {
        String sql = "UPDATE CHITIETPHIEUNHAPHANG SET SLNHAP = ?, GIANHAP = ?, HSD = ?, SOLO = ? WHERE MAPNH = ? AND MASP = ?";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getSoLuongNhap());
            ps.setDouble(2, ct.getGiaNhap());
            ps.setDate(3, new java.sql.Date(ct.getHsd().getTime()));
            ps.setString(4, ct.getSoLo());
            ps.setString(5, ct.getMaPNH());
            ps.setString(6, ct.getMaSP());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    public boolean themChiTietPhieuNhapHang(ChiTietPhieuNhapHangDTO ct, Connection conn) {
        String sql = "INSERT INTO CHITIETPHIEUNHAPHANG (MAPNH, MASP, HSD, SOLO, SLNHAP, GIANHAP) VALUES (?, ?, ?, ?, ?, ?)";
        boolean shouldClose = conn == null;

        try {
            if (conn == null) conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, ct.getMaPNH());
            ps.setString(2, ct.getMaSP());
            ps.setDate(3, new java.sql.Date(ct.getHsd().getTime()));
            ps.setString(4, ct.getSoLo());
            ps.setInt(5, ct.getSoLuongNhap());
            ps.setDouble(6, ct.getGiaNhap());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (shouldClose && conn != null) DatabaseHelper.closeConnection(conn);
        }
    }

    public boolean capNhatThanhTien(String maPNH, double thanhTien, Connection conn) throws SQLException {
        String sql = "UPDATE PHIEUNHAPHANG SET THANHTIEN = ? WHERE MAPNH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, thanhTien);
            ps.setString(2, maPNH);
            return ps.executeUpdate() > 0;
        }
    }

    public double tinhTongTienPhieuNhap(String maPNH, Connection conn) throws SQLException {
        String sql = "SELECT SUM(SLNHAP * GIANHAP) AS TONGTIEN FROM CHITIETPHIEUNHAPHANG WHERE TRIM(MAPNH) = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPNH.trim());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("TONGTIEN") : 0;
        }
    }

    public List<PhieuNhapHangDTO> timKiemPhieuNhapHang(String keyword, String fromDate, String toDate,
                                                       Double minAmount, Double maxAmount) {
        List<PhieuNhapHangDTO> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM PHIEUNHAPHANG WHERE 1=1");

        // Thêm điều kiện tìm kiếm theo mã (tự động nhận diện loại mã)
        if (keyword != null && !keyword.isEmpty()) {
            if (keyword.toUpperCase().startsWith("PNH")) {
                sql.append(" AND MAPNH LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NCU")) {
                sql.append(" AND MANCU LIKE ?");
                keyword = "%" + keyword + "%";
            } else if (keyword.toUpperCase().startsWith("NV")) {
                sql.append(" AND MANV LIKE ?");
                keyword = "%" + keyword + "%";
            } else {
                // Tìm kiếm trên tất cả các trường mã nếu không có prefix
                sql.append(" AND (MAPNH LIKE ? OR MANCU LIKE ? OR MANV LIKE ?)");
                keyword = "%" + keyword + "%";
            }
        }

        // Thêm điều kiện ngày
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND NGAYLAPPHIEU >= TO_DATE(?, 'DD-MM-YYYY')");
        }

        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND NGAYLAPPHIEU <= TO_DATE(?, 'DD-MM-YYYY')");
        }

        // Thêm điều kiện tiền
        if (minAmount != null) {
            sql.append(" AND THANHTIEN >= ?");
        }

        if (maxAmount != null) {
            sql.append(" AND THANHTIEN <= ?");
        }

        sql.append(" ORDER BY NGAYLAPPHIEU DESC");

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            // Xử lý tham số keyword
            if (keyword != null && !keyword.isEmpty()) {
                if (keyword.toUpperCase().startsWith("%PNH") ||
                        keyword.toUpperCase().startsWith("%NCU") ||
                        keyword.toUpperCase().startsWith("%NV")) {
                    ps.setString(paramIndex++, keyword);
                } else {
                    ps.setString(paramIndex++, keyword);
                    ps.setString(paramIndex++, keyword);
                    ps.setString(paramIndex++, keyword);
                }
            }

            // Xử lý tham số ngày
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(paramIndex++, fromDate);
            }

            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(paramIndex++, toDate);
            }

            // Xử lý tham số tiền
            if (minAmount != null) {
                ps.setDouble(paramIndex++, minAmount);
            }

            if (maxAmount != null) {
                ps.setDouble(paramIndex++, maxAmount);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                            rs.getString("MAPNH"),
                            rs.getString("MANCU"),
                            rs.getString("MANV"),
                            rs.getDate("NGAYLAPPHIEU"),
                            rs.getDouble("THANHTIEN")
                    );
                    danhSach.add(pnh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
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

        Connection conn = null;
        try {
            conn = DatabaseHelper.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            // Truyền tham số đã chuẩn hóa
            for (int i = 0; i < danhSachMaSPChuanHoa.size(); i++) {
                pst.setString(i + 1, danhSachMaSPChuanHoa.get(i));
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("MASP").trim(), rs.getString("TENSP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(conn);
        }
        return result;
    }

    public boolean kiemTraNhaCungUngTonTai(String maNCU) {
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM NHACUNGUNG WHERE TRIM(MANCU) = ?")) {
            ps.setString(1, maNCU.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}