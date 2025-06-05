package DAL;

import DTO.ThongKeDTO;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ThongKeDAL {
    private Connection connection;

    public ThongKeDAL() {
        try {
            this.connection = DatabaseHelper.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy doanh thu theo ngày
    public List<ThongKeDTO> getDoanhThuTheoNgay(Date ngay) {
        List<ThongKeDTO> result = new ArrayList<>();
        String sql = "SELECT TRUNC(NGAYBAN) as ngay, SUM(THANHTIEN) as doanhThu, COUNT(*) as soHoaDon " +
                "FROM HOADON " +
                "WHERE TRUNC(NGAYBAN) = TRUNC(?) AND TRANGTHAI != 'DA_HUY' " +
                "GROUP BY TRUNC(NGAYBAN)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(ngay.getTime()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new ThongKeDTO(
                        rs.getDate("ngay"),
                        rs.getDouble("doanhThu"),
                        rs.getInt("soHoaDon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy doanh thu các ngày trong tháng
    public List<ThongKeDTO> getDoanhThuTungNgayTrongThang(String thangNam) {
        List<ThongKeDTO> result = new ArrayList<>();
        String[] parts = thangNam.split("/");
        int thang = Integer.parseInt(parts[0]);
        int nam = Integer.parseInt(parts[1]);

        String sql = "SELECT EXTRACT(DAY FROM NGAYBAN) as ngay, SUM(THANHTIEN) as doanhThu, COUNT(*) as soHoaDon " +
                "FROM HOADON " +
                "WHERE EXTRACT(MONTH FROM NGAYBAN) = ? AND EXTRACT(YEAR FROM NGAYBAN) = ? " +
                "AND TRANGTHAI != 'DA_HUY' " +
                "GROUP BY EXTRACT(DAY FROM NGAYBAN) " +
                "ORDER BY EXTRACT(DAY FROM NGAYBAN)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Calendar cal = Calendar.getInstance();
                cal.set(nam, thang-1, rs.getInt("ngay"));
                result.add(new ThongKeDTO(
                        cal.getTime(),
                        rs.getDouble("doanhThu"),
                        rs.getInt("soHoaDon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy doanh thu 12 tháng trong năm
    public List<ThongKeDTO> getDoanhThu12Thang(String nam) {
        List<ThongKeDTO> result = new ArrayList<>();
        int year = Integer.parseInt(nam);

        // Tạo danh sách 12 tháng với doanh thu = 0
        for (int i = 1; i <= 12; i++) {
            result.add(new ThongKeDTO(
                    String.format("%02d/%d", i, year),
                    0,
                    0
            ));
        }

        // Truy vấn dữ liệu từ database
        String sql = "SELECT EXTRACT(MONTH FROM NGAYBAN) as thang, SUM(THANHTIEN) as doanhThu, COUNT(*) as soHoaDon " +
                "FROM HOADON " +
                "WHERE EXTRACT(YEAR FROM NGAYBAN) = ? AND TRANGTHAI != 'DA_HUY' " +
                "GROUP BY EXTRACT(MONTH FROM NGAYBAN)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();

            // Cập nhật dữ liệu từ database vào danh sách
            while (rs.next()) {
                int month = rs.getInt("thang");
                result.set(month - 1, new ThongKeDTO(
                        String.format("%02d/%d", month, year),
                        rs.getDouble("doanhThu"),
                        rs.getInt("soHoaDon"),
                        true
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Lấy doanh thu 5 năm gần nhất
    public List<ThongKeDTO> getDoanhThu5NamGanNhat(String namHienTai) {
        List<ThongKeDTO> result = new ArrayList<>();
        int currentYear = Integer.parseInt(namHienTai);

        // Tạo danh sách 5 năm với doanh thu = 0
        for (int i = currentYear - 4; i <= currentYear; i++) {
            result.add(new ThongKeDTO(
                    String.valueOf(i),
                    0,
                    0,
                    true
            ));
        }

        // Truy vấn dữ liệu từ database
        String sql = "SELECT EXTRACT(YEAR FROM NGAYBAN) as nam, SUM(THANHTIEN) as doanhThu, COUNT(*) as soHoaDon " +
                "FROM HOADON " +
                "WHERE EXTRACT(YEAR FROM NGAYBAN) BETWEEN ? AND ? " +
                "AND TRANGTHAI != 'DA_HUY' " +
                "GROUP BY EXTRACT(YEAR FROM NGAYBAN)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, currentYear - 4);
            pstmt.setInt(2, currentYear);
            ResultSet rs = pstmt.executeQuery();

            // Cập nhật dữ liệu từ database vào danh sách
            while (rs.next()) {
                int year = rs.getInt("nam");
                int index = year - (currentYear - 4);
                if (index >= 0 && index < 5) {
                    result.set(index, new ThongKeDTO(
                            String.valueOf(year),
                            rs.getDouble("doanhThu"),
                            rs.getInt("soHoaDon"),
                            true
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Trong ThongKeDAL.java
    public List<ThongKeDTO> getDoanhThuTheoCumNgayTrongThang(String thangNam) {
        List<ThongKeDTO> result = new ArrayList<>();
        String[] parts = thangNam.split("/");
        int thang = Integer.parseInt(parts[0]);
        int nam = Integer.parseInt(parts[1]);

        // Tạo danh sách 6 cụm ngày với doanh thu = 0
        String[] cumNgayArr = {"1-5", "6-10", "11-15", "16-20", "21-25", "26-31"};
        for (String cumNgay : cumNgayArr) {
            result.add(new ThongKeDTO(cumNgay, 0, 0));
        }

        // Truy vấn dữ liệu từ database
        String sql = "SELECT " +
                "CASE " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 1 AND 5 THEN '1-5' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 6 AND 10 THEN '6-10' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 11 AND 15 THEN '11-15' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 16 AND 20 THEN '16-20' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 21 AND 25 THEN '21-25' " +
                "   ELSE '26-31' " +
                "END as cumNgay, " +
                "SUM(THANHTIEN) as doanhThu, COUNT(*) as soHoaDon " +
                "FROM HOADON " +
                "WHERE EXTRACT(MONTH FROM NGAYBAN) = ? AND EXTRACT(YEAR FROM NGAYBAN) = ? " +
                "AND TRANGTHAI != 'DA_HUY' " +
                "GROUP BY " +
                "CASE " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 1 AND 5 THEN '1-5' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 6 AND 10 THEN '6-10' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 11 AND 15 THEN '11-15' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 16 AND 20 THEN '16-20' " +
                "   WHEN EXTRACT(DAY FROM NGAYBAN) BETWEEN 21 AND 25 THEN '21-25' " +
                "   ELSE '26-31' " +
                "END";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, thang);
            pstmt.setInt(2, nam);
            ResultSet rs = pstmt.executeQuery();

            // Cập nhật dữ liệu từ database vào danh sách
            while (rs.next()) {
                String cumNgay = rs.getString("cumNgay");
                for (int i = 0; i < cumNgayArr.length; i++) {
                    if (cumNgayArr[i].equals(cumNgay)) {
                        result.set(i, new ThongKeDTO(
                                cumNgay,
                                rs.getDouble("doanhThu"),
                                rs.getInt("soHoaDon"),
                                false
                        ));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}