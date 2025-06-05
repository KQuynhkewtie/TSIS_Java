package DAL;

import DTO.ThongKeHSDDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeHSDDAL {
    public List<ThongKeHSDDTO> layDanhSachNhapHang() {
        List<ThongKeHSDDTO> danhSach = new ArrayList<>();
        Connection connection = null;

        String sql = "SELECT sp.MASP, sp.TENSP, ct.SOLO, ct.HSD, ct.SLNHAP, " +
                "ncu.TENNCU, pnh.NGAYLAPPHIEU " +
                "FROM SANPHAM sp " +
                "JOIN CHITIETPHIEUNHAPHANG ct ON sp.MASP = ct.MASP " +
                "JOIN PHIEUNHAPHANG pnh ON ct.MAPNH = pnh.MAPNH " +
                "JOIN NHACUNGUNG ncu ON pnh.MANCU = ncu.MANCU " +
                "ORDER BY sp.MASP, ct.HSD ASC"; // Sắp xếp theo hạn sử dụng tăng dần

        try {
            connection = DatabaseHelper.getConnection();
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    ThongKeHSDDTO sp = new ThongKeHSDDTO(
                            rs.getString("MASP"),
                            rs.getString("TENSP"),
                            rs.getString("SOLO"),
                            rs.getDate("HSD").toLocalDate(),
                            rs.getInt("SLNHAP"), // Lưu số lượng nhập ban đầu
                            rs.getString("TENNCU"),
                            rs.getDate("NGAYLAPPHIEU").toLocalDate()
                    );
                    danhSach.add(sp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection);
        }

        return danhSach;
    }

    public Map<String, Integer> laySoLuongDaBan() {
        Map<String, Integer> soLuongDaBan = new HashMap<>();
        Connection connection = null;

        String sql = "SELECT MASP, SUM(SOLUONG) AS SL_BAN " +
                "FROM CHITIETHOADON " +
                "GROUP BY MASP";

        try {
            connection = DatabaseHelper.getConnection();
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    soLuongDaBan.put(rs.getString("MASP"), rs.getInt("SL_BAN"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection(connection);
        }

        return soLuongDaBan;
    }
}