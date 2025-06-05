package DTO;

public class vaiTro{
    private String maVT;
    private String tenVaiTro;

    public vaiTro() {}

    public vaiTro(String maVT, String tenVaiTro) {
        this.maVT = maVT;
        this.tenVaiTro = tenVaiTro;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
}
