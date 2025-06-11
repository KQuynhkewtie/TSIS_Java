package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import BLL.NhanVienBLL;
import BLL.NhaCungUngBLL;
import BLL.PhieuNhapHangBLL;
import DTO.*;
import helper.PDFGeneratorPNH;

public class TTCT_PNH extends BasePanel {
    private DefaultTableModel tableModel;
    private JLabel lblMaPNH, lblMaNV, lblMaNCU, lblNgay, lblTongTien;
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private NhanVienBLL nvBLL = new NhanVienBLL();
    private NhaCungUngBLL ncuBLL = new NhaCungUngBLL();
    private String maPNH;
    private JTable table;

    public TTCT_PNH(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    public void loadPhieuNhapInfo(String maPNH) {
        this.maPNH = maPNH;
        if (maPNH != null && !maPNH.trim().isEmpty()) {
            loadData();
        }
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Phiếu nhập hàng");
    }
    protected void initUniqueComponents() {

        JLabel lblPNHLink = new JLabel("<html><u>Phiếu nhập hàng</u></html>");
        lblPNHLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblPNHLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblPNHLink.setBounds(20, 20, 160, 30);
        add(lblPNHLink);

        JLabel lblArrow = new JLabel(" >> Thông tin phiếu nhập hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(190, 20, 300, 30);
        add(lblArrow);

        lblPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("phieunhaphang");
            }
        });

        initInfoSection();
        initTableSection();
        initActionButtons();

        if (maPNH != null && !maPNH.trim().isEmpty()) {
            loadData();
        }

        addPDFButton();
    }

    private void initInfoSection() {
        JLabel lblMaPNHLabel = new JLabel("Mã phiếu nhập hàng:");
        lblMaPNHLabel.setBounds(20, 80, 150, 25);
        add(lblMaPNHLabel);

        lblMaPNH = new JLabel();
        lblMaPNH.setBounds(20, 110, 300, 30);
        lblMaPNH.setForeground(Color.decode("#641A1F"));
        lblMaPNH.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaPNH);

        JLabel lblMNVLabel = new JLabel("Mã nhân viên:");
        lblMNVLabel.setBounds(460, 80, 150, 25);
        add(lblMNVLabel);

        lblMaNV = new JLabel();
        lblMaNV.setBounds(460, 110, 300, 30);
        lblMaNV.setForeground(Color.decode("#641A1F"));
        lblMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaNV);

        JLabel lblMNCULabel = new JLabel("Mã nhà cung ứng:");
        lblMNCULabel.setBounds(20, 160, 150, 25);
        add(lblMNCULabel);

        lblMaNCU = new JLabel();
        lblMaNCU.setBounds(20, 190, 300, 30);
        lblMaNCU.setForeground(Color.decode("#641A1F"));
        lblMaNCU.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMaNCU);

        JLabel lblngayLabel = new JLabel("Ngày lập phiếu:");
        lblngayLabel.setBounds(460, 160, 150, 25);
        add(lblngayLabel);

        lblNgay = new JLabel();
        lblNgay.setBounds(460, 190, 300, 30);
        lblNgay.setForeground(Color.decode("#641A1F"));
        lblNgay.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblNgay);

        JLabel lbltienLabel = new JLabel("Thành tiền:");
        lbltienLabel.setBounds(20, 240, 150, 25);
        add(lbltienLabel);

        lblTongTien = new JLabel();
        lblTongTien.setBounds(20, 270, 300, 40);
        lblTongTien.setForeground(Color.decode("#F5CB21"));
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTongTien);
    }

    private void initTableSection() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã sản phẩm");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Giá nhập");
        tableModel.addColumn("Hạn sử dụng");
        tableModel.addColumn("Số lô");
        tableModel.addColumn("Thành tiền");

        table = new JTable(tableModel);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 310, 800, 240);
        add(scrollPane);
    }

    private void initActionButtons() {
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(460, 570, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.addActionListener(e -> {
            if (!currentuser.coQuyen("Xóa phiếu nhập hàng")) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền hủy!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa phiếu nhập hàng này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (pnhBLL.xoaPhieuNhapHang(lblMaPNH.getText())) {
                    JOptionPane.showMessageDialog(this,
                            "Xóa phiếu nhập hàng thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.showPage("phieunhaphang");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa phiếu nhập hàng thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnXoa);

        JButton btnCapnhat =  new JButton("Cập nhật");
        btnCapnhat.setBounds(660, 570, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        btnCapnhat.addActionListener(e -> {
            CapNhatTT_PNH capNhatFrame = mainFrame.getPage("capnhatpnh", CapNhatTT_PNH.class);

            capNhatFrame.setMaPNH(maPNH);
            capNhatFrame.setOnUpdateSuccessCallback(() -> {
                reloadData(maPNH);
            });
            mainFrame.showPage("capnhatpnh");
        });
        add(btnCapnhat);
    }

    public void reloadData(String maPNH) {
        this.maPNH = maPNH;
        loadData();
    }

    private void loadData() {
        try {
            PhieuNhapHangDTO pnh = pnhBLL.layPhieuNhapHangTheoMa(maPNH);
            if (pnh != null) {
                lblMaPNH.setText(pnh.getMaPNH());
                lblMaNV.setText(pnh.getMaNhanVien());
                lblMaNCU.setText(pnh.getMaNCU());
                lblNgay.setText(new SimpleDateFormat("dd/MM/yyyy").format(pnh.getNgayLapPhieu()));
                lblTongTien.setText(String.format("%,.0f VND", pnh.getThanhTien()));

                List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = pnhBLL.layChiTietPhieuNhapHang(maPNH);
                tableModel.setRowCount(0);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
                    double thanhTien = ct.getSoLuongNhap() * ct.getGiaNhap();
                    tableModel.addRow(new Object[]{
                            ct.getMaSP(),
                            ct.getSoLuongNhap(),
                            String.format("%,.0f VND", ct.getGiaNhap()),
                            ct.getHsd() != null ? dateFormat.format(ct.getHsd()) : "N/A",
                            ct.getSoLo() != null ? ct.getSoLo() : "N/A",
                            String.format("%,.0f VND", thanhTien)
                    });
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            mainFrame.showPage("phieunhaphang");
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
            if (maPNH == null || maPNH.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có phiếu nhập hàng để xuất!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PhieuNhapHangDTO pnh = pnhBLL.layPhieuNhapHangTheoMa(maPNH);
            if (pnh == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy phiếu nhập hàng!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<ChiTietPhieuNhapHangDTO> chiTiet = pnhBLL.layChiTietPhieuNhapHang(maPNH);
            if (chiTiet == null || chiTiet.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Phiếu nhập hàng không có chi tiết!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> danhSachMaSP = chiTiet.stream()
                    .map(ChiTietPhieuNhapHangDTO::getMaSP)
                    .collect(Collectors.toList());
            Map<String, String> tenSanPhamMap = pnhBLL.layDanhSachTenSanPham(danhSachMaSP);

            NhaCungUngDTO nhaCungUng = ncuBLL.getNCUById(pnh.getMaNCU().trim());
            NhanVienDTO nhanVien = nvBLL.getNhanVienByID(pnh.getMaNhanVien().trim());

            new PDFGeneratorPNH().exportPhieuNhapToPDF(
                    (JFrame)SwingUtilities.getWindowAncestor(this),
                    pnh,
                    chiTiet,
                    tenSanPhamMap,
                    nhaCungUng,
                    nhanVien
            );
        });

        add(btnExportPDF);
    }
}
