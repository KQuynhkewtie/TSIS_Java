package DTO;

public class vaitro_quyendto {
	private String maVT;
    private String maQuyen;

    public vaitro_quyendto(String maVT, String maQuyen) {
        this.maVT = maVT;
        this.maQuyen = maQuyen;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }
}
