package GUI;

import BLL.HoaDonBLL;
import BLL.NhanVienBLL;
import DTO.*;
import helper.PDFGeneratorHD;
import BLL.KhachHangBLL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TTCT_HD extends BasePanel {
    private HoaDonBLL hdBLL = new HoaDonBLL();
    private KhachHangBLL khBLL = new KhachHangBLL();
    private NhanVienBLL nvBLL = new NhanVienBLL();
    private String maHoaDon;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel txtMaHD, txtMaNV, txtMaKH, txtNgay, txtThanhTien;

    public TTCT_HD(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    public void loadHoaDonInfo(String maHoaDon) {
        this.maHoaDon = maHoaDon;
        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            loadHoaDonData();
        }
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hóa đơn");
    }

    protected void initUniqueComponents() {

        initHeaderSection();

        initInfoForm();

        initProductTable();

        initDeleteButton();

        initUpdateButton();

        if (maHoaDon != null && !maHoaDon.trim().isEmpty()) {
            loadHoaDonData();
        }

        addPDFButton();
    }

    private void initHeaderSection() {
        JLabel lblHoaDonLink = new JLabel("<html><u>Hóa đơn</u></html>");
        lblHoaDonLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHoaDonLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHoaDonLink.setBounds(20, 20, 100, 30);
        add(lblHoaDonLink);

        lblHoaDonLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("hoadon");

            }
        });

        JLabel lblArrow = new JLabel(" >> Thông tin hóa đơn");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(110, 20, 300, 30);
        add(lblArrow);
    }

    private void initInfoForm() {
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setBounds(20, 80, 150, 25);
        add(lblMaHD);
        txtMaHD = new JLabel();
        txtMaHD.setBounds(20, 110, 300, 30);
        txtMaHD.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaHD.setForeground(Color.decode("#641A1F"));
        add(txtMaHD);

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, 160, 150, 25);
        add(lblMaNV);
        txtMaNV = new JLabel();
        txtMaNV.setBounds(20, 190, 300, 30);
        txtMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNV.setForeground(Color.decode("#641A1F"));
        add(txtMaNV);

        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(20, 240, 150, 25);
        add(lblMaKH);
        txtMaKH = new JLabel();
        txtMaKH.setBounds(20, 270, 300, 30);
        txtMaKH.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaKH.setForeground(Color.decode("#641A1F"));
        add(txtMaKH);

        JLabel lblNgay = new JLabel("Ngày lập hóa đơn:");
        lblNgay.setBounds(460, 80, 150, 25);
        add(lblNgay);
        txtNgay = new JLabel();
        txtNgay.setBounds(460, 110, 300, 30);
        txtNgay.setFont(new Font("Arial", Font.BOLD, 20));
        txtNgay.setForeground(Color.decode("#641A1F"));
        add(txtNgay);

        JLabel lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setBounds(460, 160, 150, 25);
        add(lblThanhTien);
        txtThanhTien = new JLabel();
        txtThanhTien.setBounds(460, 190, 300, 30);
        txtThanhTien.setFont(new Font("Arial", Font.BOLD, 20));
        txtThanhTien.setForeground(Color.decode("#641A1F"));
        add(txtThanhTien);
    }

    private void initProductTable() {
        String[] columnNames = { "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 310, 800, 240);
        add(scrollPane);
    }

    private void initDeleteButton() {
        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBounds(460, 570, 100, 40);
        btnHuy.setBackground(Color.decode("#F0483E"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.addActionListener(e -> {
            if (!currentuser.coQuyen("Xóa hóa đơn")) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền hủy!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            huyHoaDon();
        });

        add(btnHuy);
    }

    private void initUpdateButton() {
        JButton btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBounds(660, 570, 100, 40);
        btnCapNhat.setBackground(Color.decode("#F0483E"));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.addActionListener(e -> {
            HoaDonDTO hd = hdBLL.layHoaDonTheoMa(maHoaDon);
            if (hd != null && "DA_HUY".equals(hd.getTrangThai())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể cập nhật hóa đơn đã hủy!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            CapNhatTT_HD capNhatFrame = mainFrame.getPage("capnhathd", CapNhatTT_HD.class);
            capNhatFrame.setMaHoaDon(maHoaDon);
            capNhatFrame.setOnUpdateSuccessCallback(() -> {
                reloadData(maHoaDon);
            });
            mainFrame.showPage("capnhathd");
        });
        add(btnCapNhat);
    }


    public void reloadData(String maHoaDon) {
        this.maHoaDon = maHoaDon;
        loadHoaDonData();
    }

    private void loadHoaDonData() {
        HoaDonDTO hd = hdBLL.layHoaDonTheoMa(maHoaDon);
        if (hd != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            txtMaHD.setText(hd.getMaHoaDon());
            txtMaNV.setText(hd.getMaNhanVien());
            txtMaKH.setText(hd.getMaKH() != null ? hd.getMaKH() : "Không có");
            txtNgay.setText(dateFormat.format(hd.getNgayBan()));
            txtThanhTien.setText(String.format("%,.0f VNĐ", hd.getThanhTien()));

            String trangThai = "Bình thường";
            if ("DA_HUY".equals(hd.getTrangThai())) {
                trangThai = "Đã hủy";
                txtMaHD.setForeground(Color.RED);
            } else {
                txtMaHD.setForeground(Color.decode("#641A1F"));
            }

            loadChiTietHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadChiTietHoaDon() {
        tableModel.setRowCount(0);
        List<ChiTietHoaDonDTO> chiTiet = hdBLL.layChiTietHoaDon(maHoaDon);

        List<String> danhSachMaSP = chiTiet.stream()
                .map(ChiTietHoaDonDTO::getMaSanPham)
                .collect(Collectors.toList());

        Map<String, String> tenSanPhamMap = hdBLL.layDanhSachTenSanPham(danhSachMaSP);

        for (ChiTietHoaDonDTO ct : chiTiet) {
            String tenSP = tenSanPhamMap.getOrDefault(ct.getMaSanPham(), "Không xác định");
            double thanhTien = ct.getSoLuong() * ct.getGia();

            Object[] row = {
                    ct.getMaSanPham(),
                    tenSP,
                    ct.getSoLuong(),
                    String.format("%,.0f VNĐ", ct.getGia()),
                    String.format("%,.0f VNĐ", thanhTien)
            };
            tableModel.addRow(row);
        }
    }

    private void huyHoaDon() {
        HoaDonDTO hd = hdBLL.layHoaDonTheoMa(maHoaDon);
        if (hd != null && "DA_HUY".equals(hd.getTrangThai())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hóa đơn này đã được hủy trước đó!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn hủy hóa đơn này?",
                "Xác nhận hủy",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = hdBLL.huyHoaDon(maHoaDon);
            if (result) {
                JOptionPane.showMessageDialog(
                        this,
                        "Hủy hóa đơn thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadHoaDonData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Hủy hóa đơn thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    protected void addPDFButton() {
        ImageIcon iconPDF = new ImageIcon(getClass().getClassLoader().getResource("image/pdf-icon.png"));
        Image imgPDF = iconPDF.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIconPDF = new ImageIcon(imgPDF);

        JButton btnExportPDF = new JButton(resizedIconPDF);
        btnExportPDF.setBounds(720, 23, 40, 40);

        btnExportPDF.setBackground(null);
        btnExportPDF.setBorderPainted(false);
        btnExportPDF.setFocusPainted(false);
        btnExportPDF.setContentAreaFilled(false);
        btnExportPDF.setOpaque(true);
        btnExportPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnExportPDF.addActionListener(e -> {
            if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có hóa đơn để xuất!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            HoaDonDTO hd = hdBLL.layHoaDonTheoMa(maHoaDon);
            if (hd == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<ChiTietHoaDonDTO> chiTiet = hdBLL.layChiTietHoaDon(maHoaDon);
            if (chiTiet == null || chiTiet.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Hóa đơn không có chi tiết!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> danhSachMaSP = chiTiet.stream()
                    .map(ChiTietHoaDonDTO::getMaSanPham)
                    .collect(Collectors.toList());
            Map<String, String> tenSanPhamMap = hdBLL.layDanhSachTenSanPham(danhSachMaSP);

            KhachHangDTO kh = khBLL.getKhachHangById(hd.getMaKH().trim());
            NhanVienDTO nv = nvBLL.getNhanVienByID(hd.getMaNhanVien().trim());

            new PDFGeneratorHD().exportHoaDonToPDF((JFrame)SwingUtilities.getWindowAncestor(this), hd, chiTiet, tenSanPhamMap, nv, kh);
        });

        add(btnExportPDF);
    }

}