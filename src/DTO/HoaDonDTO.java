package DTO;

import java.util.Date;
import java.util.List;

public class HoaDonDTO {
	private String maHoaDon;
	private String maNhanVien;
	private String maKH;
	private Date ngayBan;
	private double thanhTien;
	private String trangThai; // mới thêm
	private List<ChiTietHoaDonDTO> chiTietHoaDon;

	public HoaDonDTO() {
	}

	public HoaDonDTO(String maHoaDon, String maNhanVien, String maKH, Date ngayBan, double thanhTien, String trangThai) {
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.maKH = maKH;
		this.ngayBan = ngayBan;
		this.thanhTien = thanhTien;
		this.trangThai = trangThai;
	}

	// Getter và Setter
	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public Date getNgayBan() {
		return ngayBan;
	}

	public void setNgayBan(Date ngayBan) {
		this.ngayBan = ngayBan;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	// Getter/Setter cho trangThai
	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public List<ChiTietHoaDonDTO> getChiTietHoaDon() {
		return chiTietHoaDon;
	}

	public void setChiTietHoaDon(List<ChiTietHoaDonDTO> chiTietHoaDon) {
		this.chiTietHoaDon = chiTietHoaDon;
	}

	// Constructor cần sửa lại
	public HoaDonDTO(String maHoaDon, String maNhanVien, String maKH, Date ngayBan) {
		this(maHoaDon, maNhanVien, maKH, ngayBan, 0, "BINH_THUONG");
	}

}
