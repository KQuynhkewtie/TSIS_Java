package DAL;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import DTO.LoaiSPDTO;

public class LoaiSPDAL {
	public ArrayList<LoaiSPDTO> getAllLSP() {
		ArrayList<LoaiSPDTO> ds = new ArrayList<>();
		String query = "SELECT * FROM LOAISANPHAM";

		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {


			while (rs.next()) {
				LoaiSPDTO lsp = new LoaiSPDTO(
						rs.getString("MALSP"),
						rs.getString("TENLSP"),
						rs.getString("TRANGTHAI")
				);
				ds.add(lsp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public LoaiSPDTO getLSPById(String maLSP) {
		LoaiSPDTO lsp = null;
		String query = "SELECT * FROM LOAISANPHAM WHERE TRIM(MALSP) = ?";

		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, maLSP.trim());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				lsp = new LoaiSPDTO(
						rs.getString("MALSP"),
						rs.getString("TENLSP"),
						rs.getString("TRANGTHAI")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsp;
	}

	public boolean insertLSP(LoaiSPDTO lsp) {
		String sql = "INSERT INTO LOAISANPHAM (MALSP, TENLSP) VALUES (?, ?)";

		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, lsp.getMaLSP());
			stmt.setString(2, lsp.getTenLSP());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<LoaiSPDTO> getLSP(String keyword) {
		List<LoaiSPDTO> danhSachLSP = new ArrayList<>();
		String sql = "SELECT * FROM LOAISANPHAM WHERE LOWER(TENLSP) LIKE ? OR LOWER(MALSP) LIKE ? OR LOWER(TRANGTHAI) LIKE ?";

		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "%" + keyword.toLowerCase() + "%");
			stmt.setString(2, "%" + keyword.toLowerCase()+ "%");
			stmt.setString(3, "%" + keyword.toLowerCase() + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				LoaiSPDTO lsp = new LoaiSPDTO(
						rs.getString("MALSP"),
						rs.getString("TENLSP"),
						rs.getString("TRANGTHAI")
				);
				danhSachLSP.add(lsp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSachLSP;
	}


	public boolean updateLSP(LoaiSPDTO lsp) {
		String sql = "UPDATE LOAISANPHAM SET TENLSP=? ,TRANGTHAI = ?  WHERE TRIM(MALSP) = ?";

		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, lsp.getTenLSP());
			stmt.setString(2, lsp.getTrangThai());
			stmt.setString(3, lsp.getMaLSP().trim());


			int rows = stmt.executeUpdate();

			return rows > 0;

		} catch (SQLException e) {
			System.err.println("Lỗi cập nhật loại sản phẩm: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteLSPById(String maLSP) {
		String sql = "DELETE FROM LOAISANPHAM WHERE MALSP = ?";
		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLSP);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}