package helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DTO.PhieuNhapHangDTO;
import DTO.ChiTietPhieuNhapHangDTO;
import DTO.NhaCungUngDTO;
import DTO.NhanVienDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFGeneratorPNH {
    public void exportPhieuNhapToPDF(JFrame parentFrame,
                                     PhieuNhapHangDTO pnh,
                                     List<ChiTietPhieuNhapHangDTO> chiTiet,
                                     Map<String, String> tenSanPhamMap,
                                     NhaCungUngDTO nhaCungUng,
                                     NhanVienDTO nhanVien) {
        if (pnh == null || chiTiet == null) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Dữ liệu phiếu nhập hàng không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new File("PhieuNhapHang_" + pnh.getMaPNH().trim() + ".pdf"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".pdf")) {
            filePath += ".pdf";
        }

        try {
            generatePDFFromData(filePath, pnh, chiTiet, tenSanPhamMap, nhaCungUng, nhanVien);
            JOptionPane.showMessageDialog(parentFrame,
                    "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void generatePDFFromData(String filePath,
                                     PhieuNhapHangDTO pnh,
                                     List<ChiTietPhieuNhapHangDTO> chiTiet,
                                     Map<String, String> tenSanPhamMap,
                                     NhaCungUngDTO nhaCungUng,
                                     NhanVienDTO nhanVien) throws Exception {
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

        Paragraph invoiceTitle = new Paragraph("PHIẾU NHẬP HÀNG", fontTitle);
        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        invoiceTitle.setSpacingAfter(0f);
        document.add(invoiceTitle);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(pnh.getNgayLapPhieu());
        String[] dateParts = currentDate.split("/");

        Paragraph dateLine = new Paragraph("Ngày " + dateParts[0] + " tháng " + dateParts[1] + " năm " + dateParts[2], fontNormal);
        dateLine.setAlignment(Element.ALIGN_CENTER);
        dateLine.setSpacingAfter(0f);
        document.add(dateLine);

        Paragraph invoiceCode = new Paragraph("Mã phiếu: " + pnh.getMaPNH(), fontNormal);
        invoiceCode.setAlignment(Element.ALIGN_RIGHT);
        invoiceCode.setSpacingAfter(5f);
        document.add(invoiceCode);

        String nhanVienInfo = pnh.getMaNhanVien().trim() + "_" + nhanVien.getHoTen();
        Paragraph staffLine = new Paragraph("Nhân viên: " + nhanVienInfo, fontNormal);
        staffLine.setAlignment(Element.ALIGN_LEFT);
        staffLine.setSpacingAfter(2.5f);
        document.add(staffLine);

        Paragraph nccTitle = new Paragraph("Thông tin nhà cung ứng:", fontBold);
        nccTitle.setSpacingAfter(2.5f);
        document.add(nccTitle);

        if (nhaCungUng != null) {
            Paragraph tenNCC = new Paragraph("Tên: " + nhaCungUng.getTenNCU(), fontNormal);
            tenNCC.setSpacingAfter(2.5f);
            document.add(tenNCC);

            Paragraph diaChi = new Paragraph("Địa chỉ: " + nhaCungUng.getDiaChi(), fontNormal);
            diaChi.setSpacingAfter(2.5f);
            document.add(diaChi);

            Paragraph maSoThue = new Paragraph("Mã số thuế: " + nhaCungUng.getMaSoThue(), fontNormal);
            maSoThue.setSpacingAfter(2.5f);
            document.add(maSoThue);

            Paragraph sdt = new Paragraph("Số điện thoại: " + nhaCungUng.getSdt(), fontNormal);
            sdt.setSpacingAfter(10f);
            document.add(sdt);
        } else {
            Paragraph nccKhongCo = new Paragraph("Không có thông tin nhà cung ứng", fontNormal);
            nccKhongCo.setSpacingAfter(10f);
            document.add(nccKhongCo);
        }

        Paragraph productTitle = new Paragraph("DANH SÁCH SẢN PHẨM NHẬP", fontHeader);
        productTitle.setAlignment(Element.ALIGN_CENTER);
        productTitle.setSpacingAfter(10f);
        document.add(productTitle);

        PdfPTable productTable = new PdfPTable(6);
        productTable.setWidthPercentage(100);
        productTable.setSpacingAfter(20f);

        addCell(productTable, "Mã SP", fontBold);
        addCell(productTable, "Tên sản phẩm", fontBold);
        addCell(productTable, "Số lượng", fontBold);
        addCell(productTable, "Đơn giá", fontBold);
        addCell(productTable, "Hạn sử dụng", fontBold);
        addCell(productTable, "Thành tiền", fontBold);

        for (ChiTietPhieuNhapHangDTO ct : chiTiet) {
            String tenSP = tenSanPhamMap.getOrDefault(ct.getMaSP().trim(), "Không xác định");
            double thanhTien = ct.getSoLuongNhap() * ct.getGiaNhap();

            addCell(productTable, ct.getMaSP(), fontNormal);
            addCell(productTable, tenSP, fontNormal);
            addCell(productTable, String.valueOf(ct.getSoLuongNhap()), fontNormal);
            addCell(productTable, formatCurrency(ct.getGiaNhap()), fontNormal);
            addCell(productTable, ct.getHsd() != null ? dateFormat.format(ct.getHsd()) : "N/A", fontNormal);
            addCell(productTable, formatCurrency(thanhTien), fontNormal);
        }

        document.add(productTable);

        Paragraph tongTien = new Paragraph("Tổng thành tiền: " + formatCurrency(pnh.getThanhTien()), fontBold);
        tongTien.setAlignment(Element.ALIGN_RIGHT);
        tongTien.setSpacingAfter(20f);
        document.add(tongTien);

        Paragraph notes = new Paragraph("Ghi chú:\n............................................................................................................................................................", fontNormal);
        notes.setSpacingBefore(10f);
        document.add(notes);


        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(30f);

        PdfPCell nccCell = new PdfPCell(new Phrase("Nhà cung ứng\n(Ký và ghi rõ họ tên)", fontNormal));
        nccCell.setBorder(Rectangle.NO_BORDER);
        nccCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        signatureTable.addCell(nccCell);

        PdfPCell nvCell = new PdfPCell(new Phrase("Người nhập hàng\n(Ký và ghi rõ họ tên)", fontNormal));
        nvCell.setBorder(Rectangle.NO_BORDER);
        nvCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        signatureTable.addCell(nvCell);

        document.add(signatureTable);

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
