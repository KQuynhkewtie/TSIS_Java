package BLL;

import DAL.Vaitro_QuyenDAL;
import java.util.List;
public class Vaitro_QuyenBLL {
    private Vaitro_QuyenDAL vtqDAL;

    public Vaitro_QuyenBLL() {
        vtqDAL = new Vaitro_QuyenDAL();
    }

    public List<String> layDanhSachQuyenTheoVaiTro(String maVaiTro) {
        try {
            return vtqDAL.layDanhSachQuyenTheoVaiTro(maVaiTro);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}