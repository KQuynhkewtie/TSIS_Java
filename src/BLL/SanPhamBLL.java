package BLL;

import DAL.SanPhamDAL;
import DTO.SanPhamDTO;
import java.util.List;

public class SanPhamBLL {
    private SanPhamDAL spDAL;

    public SanPhamBLL() {
        spDAL = new SanPhamDAL();
    }

    public List<SanPhamDTO> getAllSanPham() {
        return spDAL.getAllSanPham();
    }

    public boolean insertSanPham(SanPhamDTO sp) {
        return spDAL.insertSanPham(sp);
    }

    public boolean updateSanPham(SanPhamDTO sp) {
        return spDAL.updateSanPham(sp);
    }

    public List<SanPhamDTO> getSanPham(String keyword) {
        return spDAL.getSanPham(keyword);
    }
    
    public SanPhamDTO getSanPhamById(String maSP) {
        return spDAL.getSanPhamById(maSP);
    }

    public boolean deleteSanPhamById(String maSP) {
        return spDAL.deleteSanPhamById(maSP);
    }

    public int countSanPham() {
        return spDAL.getTongsp();
    }
}
