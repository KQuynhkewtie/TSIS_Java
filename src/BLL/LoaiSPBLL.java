package BLL;

import DAL.LoaiSPDAL;
import DTO.LoaiSPDTO;
import java.util.ArrayList;
import java.util.List;
public class LoaiSPBLL {
	private LoaiSPDAL lspDal = new LoaiSPDAL();

    // Lấy danh sách loại sản phẩm
    public ArrayList<LoaiSPDTO> getAllLSP() {
        return lspDal.getAllLSP();
    }

    // Lấy loại sản phẩm theo mã
    public LoaiSPDTO getLSPById(String maKH) {
        return lspDal.getLSPById(maKH);
    }

    // Thêm loại sản phẩm
    public boolean insertLSP(LoaiSPDTO kh) {
        return lspDal.insertLSP(kh);
    }

    // Tìm kiếm loại sản phẩm 
    public List<LoaiSPDTO> getLSP(String keyword) {
        return lspDal.getLSP(keyword);
    }

    // Cập nhật thông tin loại sản phẩm
    public boolean updateLSP(LoaiSPDTO kh) {
        return lspDal.updateLSP(kh);
    }

    // Xóa loại sản phẩm theo mã
    public boolean deleteLSPById(String maKH) {
        return lspDal.deleteLSPById(maKH);
    }

}
