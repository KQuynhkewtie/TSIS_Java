package DTO;

public class SanPhamDTO {
	private String maSP;
	private String maHSX;
	private String maLSP;
	private String tenSP;
	private String quyCachDongGoi;
	private String soDangKy;
	private int soLuong;
	private double giaBan;
	private String trangThai;

	public SanPhamDTO() {}

	public SanPhamDTO(String maSP, String maHSX, String maLSP, String tenSP, String quyCachDongGoi,
				   String soDangKy, int soLuong, double giaBan) {
		this.maSP = maSP;
		this.maHSX = maHSX;
		this.maLSP = maLSP;
		this.tenSP = tenSP;
		this.quyCachDongGoi = quyCachDongGoi;
		this.soDangKy = soDangKy;
		this.soLuong = soLuong;
		this.giaBan = giaBan;
	}

	public SanPhamDTO(String maSP, String maHSX, String maLSP, String tenSP, String quyCachDongGoi,
				   String soDangKy, int soLuong, double giaBan, String trangThai) {
		this.maSP = maSP;
		this.maHSX = maHSX;
		this.maLSP = maLSP;
		this.tenSP = tenSP;
		this.quyCachDongGoi = quyCachDongGoi;
		this.soDangKy = soDangKy;
		this.soLuong = soLuong;
		this.giaBan = giaBan;
		this.trangThai = trangThai;
	}


	public String getMaSP() {
		return maSP;
	}
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getMaHSX() {
		return maHSX;
	}
	public void setMaHSX(String maHSX) {
		this.maHSX = maHSX;
	}

	public String getMaLSP() {
		return maLSP;
	}
	public void setMaLSP(String maLSP) {
		this.maLSP = maLSP;
	}

	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public String getQuyCachDongGoi() {
		return quyCachDongGoi;
	}
	public void setQuyCachDongGoi(String quyCachDongGoi) {
		this.quyCachDongGoi = quyCachDongGoi;
	}


	public String getSoDangKy() {
		return soDangKy;
	}
	public void setSoDangKy(String soDangKy) {
		this.soDangKy = soDangKy;
	}

	public int getsoluong() {
		return soLuong;
	}
	public void setsoluong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaBan() {
		return giaBan;
	}
	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

}

   

