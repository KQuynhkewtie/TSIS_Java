package helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DTO.PhieuNhapHangDTO;
import DTO.ChiTietPhieuNhapHangDTO;
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
                                     Map<String, String> tenSanPhamMap) {
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
            generatePDFFromData(filePath, pnh, chiTiet, tenSanPhamMap);
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
        Paragraph title = new Paragraph("PHIẾU NHẬP HÀNG", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Thông tin phiếu nhập
        Paragraph maPNH = new Paragraph("Mã phiếu nhập: " + pnh.getMaPNH(), fontNormal);
        maPNH.setSpacingAfter(5f);
        document.add(maPNH);

        Paragraph ngayNhap = new Paragraph("Ngày nhập: " + new SimpleDateFormat("dd/MM/yyyy").format(pnh.getNgayLapPhieu()), fontNormal);
        ngayNhap.setSpacingAfter(5f);
        document.add(ngayNhap);

        Paragraph nhanVien = new Paragraph("Nhân viên nhập: " + pnh.getMaNhanVien(), fontNormal);
        nhanVien.setSpacingAfter(5f);
        document.add(nhanVien);

        Paragraph nhaCungUng = new Paragraph("Nhà cung ứng: " + pnh.getMaNCU(), fontNormal);
        nhaCungUng.setSpacingAfter(20f);
        document.add(nhaCungUng);

        // Danh sách sản phẩm
        Paragraph productTitle = new Paragraph("DANH SÁCH SẢN PHẨM NHẬP", fontHeader);
        productTitle.setAlignment(Element.ALIGN_CENTER);
        productTitle.setSpacingAfter(10f);
        document.add(productTitle);

        PdfPTable productTable = new PdfPTable(6);
        productTable.setWidthPercentage(100);
        productTable.setSpacingAfter(20f);

        // Header
        addCell(productTable, "Mã SP", fontBold);
        addCell(productTable, "Tên sản phẩm", fontBold);
        addCell(productTable, "Số lượng", fontBold);
        addCell(productTable, "Đơn giá", fontBold);
        addCell(productTable, "Hạn sử dụng", fontBold);
        addCell(productTable, "Thành tiền", fontBold);

        // Dữ liệu
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

        // Thông tin tổng tiền
        Paragraph tongTien = new Paragraph("Tổng thành tiền: " + formatCurrency(pnh.getThanhTien()), fontBold);
        tongTien.setAlignment(Element.ALIGN_RIGHT);
        tongTien.setSpacingAfter(20f);
        document.add(tongTien);

        // Ghi chú
        Paragraph notes = new Paragraph("Ghi chú:\n............................................................................................................................................................", fontNormal);
        notes.setSpacingBefore(10f);
        document.add(notes);

        // Tạo một bảng với 2 cột để sắp xếp chữ ký ngang hàng
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(30f);

        // Chữ ký nhà cung ứng (bên trái)
        PdfPCell nccCell = new PdfPCell(new Phrase("Nhà cung ứng\n(Ký và ghi rõ họ tên)", fontNormal));
        nccCell.setBorder(Rectangle.NO_BORDER);
        nccCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        signatureTable.addCell(nccCell);

        // Chữ ký người lập (bên phải)
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