package BLL;

import DAL.DatabaseHelper;
import DAL.SanPhamDAL;
import DAL.HoaDonDAL;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HoaDonBLL {
    private HoaDonDAL hdDAL = new HoaDonDAL();
    private Connection connection;

    public HoaDonBLL() {
        try {
            this.connection = DatabaseHelper.getConnection();
            this.hdDAL.setConnection(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể kết nối database: " + e.getMessage());
        }
    }

    public List<HoaDonDTO> layDanhSachHoaDon() {
        return hdDAL.layDanhSachHoaDon();
    }

    public List<HoaDonDTO> timKiemHoaDon(String keyword, String fromDate, String toDate,
                                         Double minAmount, Double maxAmount, String trangThai) {
        return hdDAL.timKiemHoaDon(keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
    }

    public List<ChiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        return hdDAL.layChiTietHoaDon(maHoaDon);
    }

    public HoaDonDTO layHoaDonTheoMa(String maHoaDon) {
        return hdDAL.layHoaDonTheoMa(maHoaDon);
    }


    public Map<String, String> layDanhSachTenSanPham(List<String> danhSachMaSP) {
        return hdDAL.layDanhSachTenSanPham(danhSachMaSP);
    }

    public boolean themHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        try {
            connection.setAutoCommit(false);

            double tongTien = danhSachChiTiet.stream()
                    .mapToDouble(ct -> ct.getSoLuong() * ct.getGia())
                    .sum();
            hd.setThanhTien(tongTien);

            boolean result = hdDAL.themHoaDonVoiChiTiet(hd, danhSachChiTiet);

            if (result) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            String errorMsg = "Lỗi database: ";
            if (e.getErrorCode() == 547) { // Lỗi khóa ngoại
                errorMsg += "Mã sản phẩm không tồn tại";
            } else if (e.getErrorCode() == 515) { // Lỗi null
                errorMsg += "Thiếu thông tin bắt buộc";
            } else {
                errorMsg += e.getMessage();
            }

            System.err.println(errorMsg);
            JOptionPane.showMessageDialog(null, errorMsg, "Lỗi database", JOptionPane.ERROR_MESSAGE);

            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Lỗi khi set auto commit: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }


    public boolean kiemTraMaHDTonTai(String maHD) {
        return hdDAL.kiemTraMaHDTonTai(maHD);
    }

    public boolean kiemTraNhanVienTonTai(String maNV) {
        return hdDAL.kiemTraNhanVienTonTai(maNV);
    }

    public boolean kiemTraKhachHangTonTai(String maKH) {
        return hdDAL.kiemTraKhachHangTonTai(maKH);
    }

    public boolean capNhatHoaDonVoiChiTiet(HoaDonDTO hd, List<ChiTietHoaDonDTO> danhSachChiTiet) {
        // Validate input data
        if (hd == null || hd.getMaHoaDon() == null || hd.getMaHoaDon().trim().isEmpty()) {
            return false;
        }

        if (hd.getMaNhanVien() == null || hd.getMaNhanVien().trim().isEmpty()) {
            return false;
        }

        if (hd.getNgayBan() == null) {
            return false;
        }

        if (danhSachChiTiet == null || danhSachChiTiet.isEmpty()) {
            return false;
        }

        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            if (ct.getMaSanPham() == null || ct.getMaSanPham().trim().isEmpty()) {
                return false;
            }
            if (ct.getSoLuong() <= 0) {
                return false;
            }
            if (ct.getGia() <= 0) {
                return false;
            }
        }

        double tongTien = danhSachChiTiet.stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getGia())
                .sum();
        hd.setThanhTien(tongTien);

        if (!kiemTraMaHDTonTai(hd.getMaHoaDon())) {
            return false;
        }

        if (!kiemTraNhanVienTonTai(hd.getMaNhanVien())) {
            return false;
        }

        if (hd.getMaKH() != null && !hd.getMaKH().trim().isEmpty() &&
                !kiemTraKhachHangTonTai(hd.getMaKH())) {
            return false;
        }

        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            if (!kiemTraSanPhamTonTai(ct.getMaSanPham())) {
                return false;
            }
        }

        return hdDAL.capNhatHoaDonVoiChiTiet(hd, danhSachChiTiet);
    }

    private boolean kiemTraSanPhamTonTai(String maSP) {
        SanPhamDAL spDAL = new SanPhamDAL();
        return spDAL.getSanPhamById(maSP) != null;
    }

    public boolean huyHoaDon(String maHoaDon) {
        try {
            HoaDonDTO hd = hdDAL.layHoaDonTheoMa(maHoaDon);
            if (hd == null || "DA_HUY".equals(hd.getTrangThai())) {
                return false;
            }

            return hdDAL.huyHoaDon(maHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void commitTransaction() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public double getDoanhThunamhientai() {
        return hdDAL.getDoanhThunamhientai();
    }
}
