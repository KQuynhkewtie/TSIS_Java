package DAL;

import java.sql.*;

public class VaiTroDAL {
    public String getTenVaiTro(String maVT) {
        String tenVaiTro = null;
        String sql = "SELECT TENVAITRO FROM VAITRO WHERE MAVT = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maVT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tenVaiTro = rs.getString("TENVAITRO");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tenVaiTro;
    }
}
