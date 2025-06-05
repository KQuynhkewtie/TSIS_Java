package BLL;

import DAL.ThongKeDAL;
import DTO.ThongKeDTO;

import java.util.Date;
import java.util.List;

public class ThongKeBLL {
    private ThongKeDAL thongKeDAL;

    public ThongKeBLL() {
        this.thongKeDAL = new ThongKeDAL();
    }


    // Lấy dữ liệu cho biểu đồ theo cụm ngày trong tháng
    public double[] getChartDataForMonth(String thangNam) {
        List<ThongKeDTO> data = thongKeDAL.getDoanhThuTheoCumNgayTrongThang(thangNam);
        double[] values = new double[6]; // 6 cụm ngày

        for (ThongKeDTO item : data) {
            String cumNgay = item.getCumNgay();
            if (cumNgay == null) continue; // Bỏ qua nếu cumNgay null

            int index = -1;
            if (cumNgay.equals("1-5")) index = 0;
            else if (cumNgay.equals("6-10")) index = 1;
            else if (cumNgay.equals("11-15")) index = 2;
            else if (cumNgay.equals("16-20")) index = 3;
            else if (cumNgay.equals("21-25")) index = 4;
            else if (cumNgay.equals("26-31")) index = 5;

            if (index != -1) {
                values[index] = item.getDoanhThu();
            }
        }

        return values;
    }

    // Lấy dữ liệu cho biểu đồ theo 12 tháng trong năm
    public double[] getChartDataForYear(String nam) {
        List<ThongKeDTO> data = thongKeDAL.getDoanhThu12Thang(nam);
        double[] values = new double[12];

        for (int i = 0; i < 12; i++) {
            values[i] = data.get(i).getDoanhThu();
        }

        return values;
    }

    // Lấy dữ liệu cho biểu đồ tổng 5 năm
    public double[] getChartDataForTotal(String currentYear) {
        List<ThongKeDTO> data = thongKeDAL.getDoanhThu5NamGanNhat(currentYear);
        double[] values = new double[5];

        for (int i = 0; i < 5; i++) {
            values[i] = data.get(i).getDoanhThu();
        }

        return values;
    }

    // Lấy dữ liệu cho bảng theo ngày
    public List<ThongKeDTO> getTableDataForDay(Date ngay) {
        return thongKeDAL.getDoanhThuTheoNgay(ngay);
    }

    // Lấy dữ liệu cho bảng theo tháng
    public List<ThongKeDTO> getTableDataForMonth(String thangNam) {
        return thongKeDAL.getDoanhThuTungNgayTrongThang(thangNam);
    }

    // Lấy dữ liệu cho bảng theo năm
    public List<ThongKeDTO> getTableDataForYear(String nam) {
        return thongKeDAL.getDoanhThu12Thang(nam);
    }

    // Lấy dữ liệu cho bảng tổng (5 năm)
    public List<ThongKeDTO> getTableDataForTotal(String currentYear) {
        return thongKeDAL.getDoanhThu5NamGanNhat(currentYear);
    }

    // Định dạng tiền tệ
    public String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }

}