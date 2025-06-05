package helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DTO.HoaDonDTO;
import DTO.ChiTietHoaDonDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFGeneratorHD {
    public void exportHoaDonToPDF(JFrame parentFrame,
                                  HoaDonDTO hd,
                                  List<ChiTietHoaDonDTO> chiTiet,
                                  Map<String, String> tenSanPhamMap) {
        if (hd == null || chiTiet == null) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Dữ liệu hóa đơn không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + hd.getMaHoaDon().trim() + ".pdf"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                generatePDFFromData(filePath, hd, chiTiet, tenSanPhamMap);
                JOptionPane.showMessageDialog(parentFrame,
                        "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void generatePDFFromData(String filePath,
                                     HoaDonDTO hd,
                                     List<ChiTietHoaDonDTO> chiTiet,
                                     Map<String, String> tenSanPhamMap) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Font Unicode
        BaseFont bf = BaseFont.createFont(
                "c:/windows/fonts/times.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        Font fontTitle = new Font(bf, 18, Font.BOLD);
        Font fontHeader = new Font(bf, 14, Font.BOLD);
        Font fontNormal = new Font(bf, 13, Font.NORMAL);
        Font fontBold = new Font(bf, 12, Font.BOLD);

        // Tiêu đề
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Thông tin hóa đơn (không dùng table)
        Paragraph maHoaDon = new Paragraph("Mã hóa đơn: " + hd.getMaHoaDon(), fontNormal);
        maHoaDon.setSpacingAfter(5f);
        document.add(maHoaDon);

        Paragraph ngayBan = new Paragraph("Ngày bán: " + new SimpleDateFormat("dd/MM/yyyy").format(hd.getNgayBan()), fontNormal);
        ngayBan.setSpacingAfter(5f);
        document.add(ngayBan);

        Paragraph nhanVien = new Paragraph("Nhân viên thanh toán: " + hd.getMaNhanVien(), fontNormal);
        nhanVien.setSpacingAfter(5f);
        document.add(nhanVien);

        Paragraph khachHang = new Paragraph("Khách hàng: " + (hd.getMaKH() != null ? hd.getMaKH() : ""), fontNormal);
        khachHang.setSpacingAfter(20f);
        document.add(khachHang);

        // Danh sách sản phẩm
        Paragraph productTitle = new Paragraph("DANH SÁCH SẢN PHẨM", fontHeader);
        productTitle.setAlignment(Element.ALIGN_CENTER);
        productTitle.setSpacingAfter(10f);
        document.add(productTitle);

        PdfPTable productTable = new PdfPTable(5);
        productTable.setWidthPercentage(100);
        productTable.setSpacingAfter(20f);

        // Header
        addCell(productTable, "Mã SP", fontBold);
        addCell(productTable, "Tên sản phẩm", fontBold);
        addCell(productTable, "Số lượng", fontBold);
        addCell(productTable, "Đơn giá", fontBold);
        addCell(productTable, "Thành tiền", fontBold);

        // Dữ liệu
        for (ChiTietHoaDonDTO ct : chiTiet) {
            String tenSP = tenSanPhamMap.getOrDefault(ct.getMaSanPham().trim(), "Không xác định");
            double thanhTien = ct.getSoLuong() * ct.getGia();

            addCell(productTable, ct.getMaSanPham(), fontNormal);
            addCell(productTable, tenSP, fontNormal);
            addCell(productTable, String.valueOf(ct.getSoLuong()), fontNormal);
            addCell(productTable, formatCurrency(ct.getGia()), fontNormal);
            addCell(productTable, formatCurrency(thanhTien), fontNormal);
        }

        document.add(productTable);

        double tongCong = chiTiet.stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getGia())
                .sum();

        // Thông tin thanh toán (không dùng table)
        Paragraph TongCong = new Paragraph("Tổng cộng: " + formatCurrency(tongCong), fontBold);
        TongCong.setSpacingAfter(5f);
        document.add(TongCong);

        Paragraph giamGia = new Paragraph("Giảm giá:.........................................................................................................................", fontNormal);
        giamGia.setSpacingAfter(5f);
        document.add(giamGia);

        Paragraph khachThanhToan = new Paragraph("Khách thanh toán:............................................................................................................", fontNormal);
        khachThanhToan.setSpacingAfter(20f);
        document.add(khachThanhToan);

        // Hình thức thanh toán
        Paragraph paymentMethod = new Paragraph("Hình thức thanh toán: □ Tiền mặt □ Chuyển khoản □ Momo/QR", fontNormal);
        paymentMethod.setAlignment(Element.ALIGN_LEFT);
        paymentMethod.setSpacingBefore(10f);
        document.add(paymentMethod);

        // Ghi chú
        Paragraph notes = new Paragraph("Ghi chú (nếu có):\n...............................................................................................................................................................", fontNormal);
        notes.setSpacingBefore(10f);
        document.add(notes);

        // Ký tên
        Paragraph signature = new Paragraph("\n\n\n\nNgười lập\n(Ký và ghi rõ họ tên)", fontNormal);
        signature.setAlignment(Element.ALIGN_RIGHT);
        document.add(signature);

        document.close();
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}