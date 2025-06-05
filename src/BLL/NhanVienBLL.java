package BLL;


import DAL.NhanVienDAL;
import DTO.NhanVienDTO;

import java.util.List;

public class NhanVienBLL {
    private NhanVienDAL nhanVienDAL= new NhanVienDAL();
    public List<NhanVienDTO> getAllNhanVien() {
        return nhanVienDAL.getAllNhanVien();
    }

    public boolean insertNhanVien(NhanVienDTO nv) {
        return nhanVienDAL.insertNhanVien(nv);
    }

    public boolean updateNhanVien(NhanVienDTO nv) {
        return nhanVienDAL.updateNhanVien(nv);
    }

    public boolean deleteNhanVien(String maNhanVien) {
       
        return nhanVienDAL.deleteNhanVien(maNhanVien);
    }

   
    public NhanVienDTO getNhanVienByID(String maNhanVien) {
        return nhanVienDAL.getNhanVienByID(maNhanVien);
    }
    public List<NhanVienDTO> getNhanVien(String keyword) {
        return nhanVienDAL.getNhanVien(keyword);
    }


    public int countNhanVien() {
        return nhanVienDAL.getTongnv();
    }
}

