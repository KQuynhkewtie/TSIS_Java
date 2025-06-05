package BLL;

import DAL.VaiTroDAL;

public class VaiTroBLL {
    private VaiTroDAL vaitroDAL;
    public VaiTroBLL() {
        vaitroDAL = new VaiTroDAL();
    }

    public String getTenVaiTro(String maVT) {
        return vaitroDAL.getTenVaiTro(maVT);
    }
}
