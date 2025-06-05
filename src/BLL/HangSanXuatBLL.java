package BLL;

import DAL.HangSanXuatDAL;
import DTO.HangSanXuatDTO;
import java.util.List;

public class HangSanXuatBLL {
    private HangSanXuatDAL hangsanxuatDAL = new HangSanXuatDAL();

    // Lấy danh sách hãng sản xuất
    public List<HangSanXuatDTO> getAllHangSanXuat() {
        return hangsanxuatDAL.getAllHangSanXuat();
    }

    // Thêm hãng sản xuất
    public boolean insertHangSanXuat(HangSanXuatDTO hsx) {
        return hangsanxuatDAL.insertHangSanXuat(hsx);
    }

    // Sửa thông tin hãng sản xuất
    public boolean updateHangSanXuat(HangSanXuatDTO hsx) {
        return hangsanxuatDAL.updateHangSanXuat(hsx);
    }

 // Xóa hãng sản xuất 
    public boolean deleteHangSanXuat(String maHSX) {
       return hangsanxuatDAL.deleteHangSanXuat(maHSX);
    }
    public HangSanXuatDTO getHSXbyID(String maHSX) {
        return hangsanxuatDAL.getHSXById(maHSX);
    }
    public List<HangSanXuatDTO> getHSX(String keyword) {
        return hangsanxuatDAL.getHSX(keyword);
    }

}
