package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Vaitro_QuyenDAL {
	public List<String> layDanhSachQuyenTheoVaiTro(String maVaiTro) throws SQLException {
		List<String> quyenList = new ArrayList<>();
		String sql = "SELECT q.TENQUYEN FROM VAITRO_QUYEN vtq JOIN QUYEN q ON vtq.MAQUYEN = q.MAQUYEN WHERE vtq.MAVT = ?";
		try (Connection conn = DatabaseHelper.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, maVaiTro);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					quyenList.add(rs.getString("TENQUYEN"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quyenList;
	}
}
