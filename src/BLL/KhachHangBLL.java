package BLL;

import DAL.KhachHangDAL;
import DTO.KhachHangDTO;
import java.util.ArrayList;
import java.util.List;

public class KhachHangBLL {
    private KhachHangDAL khDal = new KhachHangDAL();

    // Lấy danh sách khách hàng
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        return khDal.getAllKhachHang();
    }

    // Lấy khách hàng theo mã
    public KhachHangDTO getKhachHangById(String maKH) {
        return khDal.getKhachHangById(maKH);
    }

    // Thêm khách hàng
    public boolean insertKhachHang(KhachHangDTO kh) {
        return khDal.insertKhachHang(kh);
    }

    // Tìm kiếm khách hàng 
    public List<KhachHangDTO> getKhachHang(String keyword) {
        return khDal.getKhachHang(keyword);
    }

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHangDTO kh) {
        return khDal.updateKhachHang(kh);
    }

    // Xóa khách hàng theo mã
    public boolean deleteKhachHangById(String maKH) {
        return khDal.deleteKhachHangById(maKH);
    }

    public int countKhachHang() {
        return khDal.getTongKH();
    }
}
