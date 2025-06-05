package BLL;


import DAL.NhaCungUngDAL;
import DTO.NhaCungUngDTO;
import java.util.ArrayList;
import java.util.List;

public class NhaCungUngBLL {
    private NhaCungUngDAL ncuDal = new NhaCungUngDAL();

 
    public ArrayList<NhaCungUngDTO> getAllNCU() {
        return ncuDal.getAllNCU();
    }

    public NhaCungUngDTO getNCUById(String maNCU) {
        return ncuDal.getNCUById(maNCU);
    }

   
    public boolean insertNCU(NhaCungUngDTO NCU) {
        return ncuDal.insertncu(NCU);
    }

  
    public List<NhaCungUngDTO> getNCU(String keyword) {
        return ncuDal.getncu(keyword);
    }

    public boolean updateNCU(NhaCungUngDTO NCU) {
        return ncuDal.updateNCU(NCU);
    }

    public boolean deleteNCUById(String maNCU) {
        return ncuDal.deleteNCUById(maNCU);
    }

}
