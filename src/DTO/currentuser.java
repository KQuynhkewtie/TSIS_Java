package DTO;

import java.util.List;
public class currentuser {
    private static String email;
    private static String username;
    private static String maNhanVien;
    private static String maVaiTro;
    private static List<String> danhSachQuyen;

    public static void setUser(String user, String manv, String mavt, List<String> quyenList) {
        currentuser.username = user;
        currentuser.maNhanVien = manv;
        currentuser.maVaiTro = mavt;
        currentuser.danhSachQuyen = quyenList;
    }
    public static void setUser(String email,String user, String manv, String mavt, List<String> quyenList) {
        currentuser.email = email;
        currentuser.username = user;
        currentuser.maNhanVien = manv;
        currentuser.maVaiTro = mavt;
        currentuser.danhSachQuyen = quyenList;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        currentuser.email = email;
    }

    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        currentuser.username = username;
    }

    public static String getMaNhanVien() {
        return maNhanVien;
    }

    public static String getMaVaiTro() {
        return maVaiTro;
    }

    public static List<String> getDanhSachQuyen() {
        return danhSachQuyen;
    }

    public static boolean coQuyen(String tenQuyen) {
        if (danhSachQuyen == null || tenQuyen == null) return false;
        for (String quyen : danhSachQuyen) {
            if (quyen != null && quyen.trim().equalsIgnoreCase(tenQuyen.trim())) {
                return true;
            }
        }
        return false;
    }


    public static void clear() {
        username = null;
        maNhanVien = null;
        maVaiTro = null;
        danhSachQuyen = null;
    }
}
