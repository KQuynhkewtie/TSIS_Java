package helper;

import DTO.NhanVienDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DTO.HoaDonDTO;
import DTO.ChiTietHoaDonDTO;
import DTO.KhachHangDTO;
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
                                  Map<String, String> tenSanPhamMap,
                                  NhanVienDTO nhanVien,
                                  KhachHangDTO khachHang) {
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
                generatePDFFromData(filePath, hd, chiTiet, tenSanPhamMap, nhanVien, khachHang);
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
                                     Map<String, String> tenSanPhamMap,
                                     NhanVienDTO nhanVien,
                                     KhachHangDTO khachHang) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        BaseFont bf = BaseFont.createFont(
                "c:/windows/fonts/times.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        Font fontTitle = new Font(bf, 18, Font.BOLD);
        Font fontHeader = new Font(bf, 14, Font.BOLD);
        Font fontNormal = new Font(bf, 12, Font.NORMAL);
        Font fontBold = new Font(bf, 12, Font.BOLD);
        Font fontSmall = new Font(bf, 10, Font.NORMAL);

        Paragraph pharmacyHeader = new Paragraph("TSIS PHARMACY", fontTitle);
        pharmacyHeader.setAlignment(Element.ALIGN_LEFT);
        pharmacyHeader.setSpacingAfter(0f);
        document.add(pharmacyHeader);

        Paragraph taxCode = new Paragraph("Mã số thuế: 0312345678", fontNormal);
        taxCode.setAlignment(Element.ALIGN_LEFT);
        taxCode.setSpacingAfter(0f);
        document.add(taxCode);

        Paragraph address = new Paragraph("Địa chỉ: 123 Đường ABC, Phường XYZ, Quận 1, TP.HCM", fontNormal);
        address.setAlignment(Element.ALIGN_LEFT);
        address.setSpacingAfter(0f);
        document.add(address);

        Paragraph phone = new Paragraph("SĐT: 0987654321", fontNormal);
        phone.setAlignment(Element.ALIGN_LEFT);
        phone.setSpacingAfter(20f);
        document.add(phone);

        Paragraph invoiceTitle = new Paragraph("HÓA ĐƠN THANH TOÁN", fontTitle);
        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        invoiceTitle.setSpacingAfter(0f);
        document.add(invoiceTitle);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(hd.getNgayBan());
        String[] dateParts = currentDate.split("/");

        String signatureCode = "2K" + dateParts[2].substring(2) + "TYY";

        Paragraph dateLine = new Paragraph("Ngày " + dateParts[0] + " tháng " + dateParts[1] + " năm " + dateParts[2], fontNormal);
        dateLine.setAlignment(Element.ALIGN_CENTER);
        dateLine.setSpacingAfter(0f);
        document.add(dateLine);

        Paragraph signatureCodeLine = new Paragraph("Ký hiệu: " + signatureCode, fontNormal);
        signatureCodeLine.setAlignment(Element.ALIGN_RIGHT);
        signatureCodeLine.setSpacingAfter(0f);
        document.add(signatureCodeLine);

        Paragraph invoiceCode = new Paragraph("Mã phiếu: " + hd.getMaHoaDon(), fontNormal);
        invoiceCode.setAlignment(Element.ALIGN_RIGHT);
        invoiceCode.setSpacingAfter(5f);
        document.add(invoiceCode);

        String nhanVienInfo = hd.getMaNhanVien().trim() + "_" + nhanVien.getHoTen();
        Paragraph staffLine = new Paragraph("Nhân viên thanh toán: " + nhanVienInfo, fontNormal);
        staffLine.setAlignment(Element.ALIGN_LEFT);
        staffLine.setSpacingAfter(2.5f);
        document.add(staffLine);

        Paragraph customerTitle = new Paragraph("Thông tin khách hàng:", fontBold);
        customerTitle.setSpacingAfter(2.5f);
        document.add(customerTitle);

        String customerName = khachHang != null ? khachHang.getHoTen() : "";
        String customerCCCD = (khachHang != null && khachHang.getCCCD() != null) ? khachHang.getCCCD() : "";
        String customerPhone = khachHang != null ? khachHang.getSdt() : "";

        Paragraph customerNameLine = new Paragraph("Tên: " + customerName, fontNormal);
        customerNameLine.setSpacingAfter(2.5f);
        document.add(customerNameLine);

        Paragraph customerCCCDLine = new Paragraph("CCCD: " + customerCCCD, fontNormal);
        customerCCCDLine.setSpacingAfter(2.5f);
        document.add(customerCCCDLine);

        Paragraph customerPhoneLine = new Paragraph("Số điện thoại: " + customerPhone, fontNormal);
        customerPhoneLine.setSpacingAfter(10f);
        document.add(customerPhoneLine);

        Paragraph productTitle = new Paragraph("DANH SÁCH SẢN PHẨM", fontHeader);
        productTitle.setAlignment(Element.ALIGN_CENTER);
        productTitle.setSpacingAfter(10f);
        document.add(productTitle);

        PdfPTable productTable = new PdfPTable(5);
        productTable.setWidthPercentage(100);
        productTable.setSpacingAfter(10f);

        addCell(productTable, "Mã SP", fontBold);
        addCell(productTable, "Tên sản phẩm", fontBold);
        addCell(productTable, "Số lượng", fontBold);
        addCell(productTable, "Đơn giá", fontBold);
        addCell(productTable, "Thành tiền", fontBold);

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

        Paragraph totalLine = new Paragraph("Tổng cộng: " + formatCurrency(tongCong), fontBold);
        totalLine.setSpacingAfter(2.5f);
        document.add(totalLine);

        Paragraph discountLine = new Paragraph("Giảm giá:.........................................................................................................................", fontNormal);
        discountLine.setSpacingAfter(2.5f);
        document.add(discountLine);

        Paragraph paymentAmountLine = new Paragraph("Khách thanh toán:............................................................................................................", fontNormal);
        paymentAmountLine.setSpacingAfter(10f);
        document.add(paymentAmountLine);

        Paragraph paymentMethod = new Paragraph("Hình thức thanh toán: □ Tiền mặt □ Chuyển khoản □ Momo/QR", fontNormal);
        paymentMethod.setAlignment(Element.ALIGN_LEFT);
        paymentMethod.setSpacingBefore(5f);
        document.add(paymentMethod);

        Paragraph notes = new Paragraph("Ghi chú (nếu có):\n...............................................................................................................................................................", fontNormal);
        notes.setSpacingBefore(5f);
        document.add(notes);

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
