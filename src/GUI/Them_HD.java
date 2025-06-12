package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import BLL.KhachHangBLL;
import BLL.NhanVienBLL;
import com.toedter.calendar.JDateChooser;
import BLL.HoaDonBLL;
import DTO.*;
import DAL.KhachHangDAL;
import DAL.SanPhamDAL;
import helper.PDFGeneratorHD;
import DTO.currentuser;

public class Them_HD extends BasePanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaHD, txtMaKH, txtMaNV;
    private JDateChooser dateChooserNgay;
    private HoaDonBLL hdBLL = new HoaDonBLL();
    private KhachHangDAL khDAL = new KhachHangDAL();
    private KhachHangBLL khBLL = new KhachHangBLL();
    private NhanVienBLL nvBLL = new NhanVienBLL();
    private SanPhamDAL spDAL = new SanPhamDAL();
    private JLabel txtThanhTien;

    public Them_HD(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hóa đơn");
        txtMaHD.setText("");
        txtMaKH.setText("");
        tableModel.setRowCount(0);
    }

    protected void initUniqueComponents() {

        JLabel lblHoaDonLink = new JLabel("<html><u>Hóa đơn</u></html>");
        lblHoaDonLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHoaDonLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHoaDonLink.setBounds(20, 20, 160, 30);
        add(lblHoaDonLink);

        JLabel lblArrow = new JLabel(" >> Thêm hóa đơn");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(110, 20, 300, 30);
        add(lblArrow);

        lblHoaDonLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("hoadon");
            }
        });

        initInfoForm();

        initDetailTable();

        initThanhTien();

        initActionButtons();
    }

    private void initInfoForm() {
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setBounds(20, 80, 150, 25);
        add(lblMaHD);

        txtMaHD = new JTextField();
        txtMaHD.setBounds(20, 110, 300, 30);
        add(txtMaHD);

        JLabel lblMNV = new JLabel("Nhân viên:");
        lblMNV.setBounds(460, 80, 150, 25);
        add(lblMNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(460, 110, 300, 30);
        txtMaNV.setText(currentuser.getMaNhanVien().trim());
        txtMaNV.setEditable(false);
        add(txtMaNV);

        JLabel lblMKH = new JLabel("Khách hàng (nếu có):");
        lblMKH.setBounds(20, 160, 150, 25);
        add(lblMKH);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(20, 190, 250, 30);
        add(txtMaKH);

        JButton btnChonKH = new JButton("Chọn");
        btnChonKH.setBounds(280, 190, 80, 30);
        btnChonKH.setBackground(Color.decode("#F0483E"));
        btnChonKH.setForeground(Color.WHITE);
        btnChonKH.addActionListener(e -> hienThiDanhSachKhachHang());
        add(btnChonKH);

        JLabel lblngay = new JLabel("Ngày lập hóa đơn:");
        lblngay.setBounds(460, 160, 150, 25);
        add(lblngay);

        dateChooserNgay = new JDateChooser();
        dateChooserNgay.setBounds(460, 190, 300, 30);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserNgay.setDate(new Date());
        add(dateChooserNgay);
    }

    private void initDetailTable() {
        JLabel lblChiTiet = new JLabel("Chi tiết hóa đơn:");
        lblChiTiet.setBounds(20, 230, 200, 30);
        add(lblChiTiet);

        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(240, 240, 240));
                return c;
            }
        });

        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2) {
                calculateThanhTien();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 270, 800, 200);
        add(scrollPane);

        JButton btnThemDong =  new JButton("Thêm sản phẩm");
        btnThemDong.setBounds(600, 500, 130, 30);
        btnThemDong.setBackground(Color.decode("#F5A623"));
        btnThemDong.setForeground(Color.WHITE);
        btnThemDong.addActionListener(e -> hienThiDanhSachSanPham());
        add(btnThemDong);

        JButton btnXoaDong = new JButton("Xóa sản phẩm");
        btnXoaDong.setBounds(600, 540, 130, 30);
        btnXoaDong.setBackground(Color.decode("#D0021B"));
        btnXoaDong.setForeground(Color.WHITE);
        btnXoaDong.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
                calculateThanhTien();
            } else {
                JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnXoaDong);

        setupKeyboardNavigation();
    }

    private void initThanhTien() {
        JLabel lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setBounds(20, 500, 200, 30);
        lblThanhTien.setFont(new Font("Arial", Font.BOLD, 25));
        add(lblThanhTien);

        txtThanhTien = new JLabel("0");
        txtThanhTien.setBounds(20, 540, 300, 30);
        txtThanhTien.setFont(new Font("Arial", Font.BOLD, 40));
        txtThanhTien.setForeground(Color.decode("#641A1F"));
        add(txtThanhTien);
    }

    private void calculateThanhTien() {
        double tongTien = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                int soLuong = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                tongTien += soLuong * donGia;
            } catch (NumberFormatException e) {
            }
        }
        txtThanhTien.setText(String.format("%,.0f", tongTien));
    }

    private void initActionButtons() {
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(600, 580, 130, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.addActionListener(e -> {

            if (!currentuser.coQuyen("Thêm hóa đơn")) {
                JOptionPane.showMessageDialog(null,
                        "Bạn không có quyền thêm!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            luuHoaDon();
        });

        add(btnLuu);
        setDefaultButtonSafe(btnLuu);
    }

    private void setupKeyboardNavigation() {
        JTextField[] textFields = {txtMaHD, txtMaNV, txtMaKH};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        } else {
                            table.requestFocus();
                            if (table.getRowCount() > 0) {
                                table.setRowSelectionInterval(0, 0);
                                table.setColumnSelectionInterval(0, 0);
                            }
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_ENTER) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        } else {
                            table.requestFocus();
                            if (table.getRowCount() > 0) {
                                table.setRowSelectionInterval(0, 0);
                                table.setColumnSelectionInterval(0, 0);
                            }
                        }
                    }
                }
            });
        }

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if (key == KeyEvent.VK_UP && row == 0) {
                    txtMaKH.requestFocus();
                } else if (key == KeyEvent.VK_DOWN && row == table.getRowCount() - 1) {
                    tableModel.addRow(new Object[]{"", "", "1", ""});
                    table.setRowSelectionInterval(row + 1, row + 1);
                    table.setColumnSelectionInterval(0, 0);
                } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
                    if (col < table.getColumnCount() - 1) {
                        table.setColumnSelectionInterval(col + 1, col + 1);
                    } else if (row < table.getRowCount() - 1) {
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    } else {
                        tableModel.addRow(new Object[]{"", "", "1", ""});
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    }
                }
            }
        });
    }

    private void hienThiDanhSachSanPham() {
        JDialog dialog = new JDialog((JFrame)SwingUtilities.getWindowAncestor(this), "Chọn sản phẩm", true);        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField txtSearch = new JTextField("Tìm kiếm sản phẩm");
        txtSearch.setForeground(Color.GRAY);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        dialog.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã SP", "Tên SP", "Mã Loại SP", "Mã Hãng sản xuất", "Giá bán"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnChon = new JButton("Chọn");
        JButton btnHuy = new JButton("Hủy");
        buttonPanel.add(btnChon);
        buttonPanel.add(btnHuy);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        loadSanPhamData(model, "");

        txtSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm sản phẩm")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Tìm kiếm sản phẩm");
                    txtSearch.setForeground(Color.GRAY);
                    loadSanPhamData(model, "");
                }
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                if (keyword.equals("Tìm kiếm sản phẩm") || keyword.isEmpty()) {
                    loadSanPhamData(model, "");
                } else {
                    loadSanPhamData(model, keyword);
                }
            }
        });

        btnChon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String maSP = model.getValueAt(selectedRow, 0).toString();
                String tenSP = model.getValueAt(selectedRow, 1).toString();
                String donGia = model.getValueAt(selectedRow, 4).toString();

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(maSP)) {
                        JOptionPane.showMessageDialog(dialog, "Sản phẩm này đã được thêm vào hóa đơn!",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                tableModel.addRow(new Object[]{maSP, tenSP, "1", donGia});
                dialog.dispose();

                int newRow = tableModel.getRowCount() - 1;
                table.setRowSelectionInterval(newRow, newRow);
                table.setColumnSelectionInterval(2, 2);
                table.editCellAt(newRow, 2);
                table.getEditorComponent().requestFocusInWindow();

                calculateThanhTien();
            } else {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một sản phẩm!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void loadSanPhamData(DefaultTableModel model, String keyword) {
        model.setRowCount(0);

        List<SanPhamDTO> spList;
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm sản phẩm")) {
            spList = spDAL.getAllSanPham();
        } else {
            spList = spDAL.getSanPham(keyword);
        }

        for (SanPhamDTO sp : spList) {
            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getMaLSP(),
                    sp.getMaHSX(),
                    sp.getGiaBan()
            });
        }
    }

    private void hienThiDanhSachKhachHang() {
        JDialog dialog = new JDialog((JFrame)SwingUtilities.getWindowAncestor(this), "Chọn khách hàng", true);        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<KhachHangDTO> khList = khDAL.getAllKhachHang();
        String[] columnNames = {"Mã KH", "Họ tên"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (KhachHangDTO kh : khList) {
            model.addRow(new Object[]{kh.getMaKH(), kh.getHoTen()});
        }

        JTable table = new JTable(model);
        table.getTableHeader().setPreferredSize(new Dimension(0, 25));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                txtMaKH.setText(model.getValueAt(selectedRow, 0).toString().trim());
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void luuHoaDon() {
        String maHD = txtMaHD.getText().trim();
        String maKH = txtMaKH.getText().trim();
        String maNV = txtMaNV.getText().trim();
        Date ngayLap = dateChooserNgay.getDate();

        if (maHD.isEmpty() || maNV.isEmpty() || ngayLap == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hdBLL.kiemTraMaHDTonTai(maHD)) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!hdBLL.kiemTraNhanVienTonTai(maNV)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!maKH.isEmpty() && !hdBLL.kiemTraKhachHangTonTai(maKH)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<ChiTietHoaDonDTO> danhSachChiTiet = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String maSP = tableModel.getValueAt(i, 0).toString().trim();
            String slStr = tableModel.getValueAt(i, 2).toString().trim();
            String giaStr = tableModel.getValueAt(i, 3).toString().trim();

            if (maSP.isEmpty() || slStr.isEmpty() || giaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin chi tiết!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int soLuong = Integer.parseInt(slStr);
                double donGia = Double.parseDouble(giaStr);

                if (soLuong <= 0 || donGia <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                danhSachChiTiet.add(new ChiTietHoaDonDTO(maHD, maSP, soLuong, donGia));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (danhSachChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất 1 sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HoaDonDTO hd = new HoaDonDTO(maHD, maNV, maKH.isEmpty() ? null : maKH, ngayLap);

        double tongTien = 0;
        for (ChiTietHoaDonDTO ct : danhSachChiTiet) {
            tongTien += ct.getSoLuong() * ct.getGia();
        }
        hd.setThanhTien(tongTien);


        if (hdBLL.themHoaDonVoiChiTiet(hd, danhSachChiTiet)) {

            hdBLL.commitTransaction();

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Thêm hóa đơn thành công! Bạn có muốn in hóa đơn không?",
                    "In hóa đơn",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                HoaDonDTO tempHD = new HoaDonDTO(
                        maHD,
                        maNV,
                        maKH.isEmpty() ? null : maKH,
                        ngayLap,
                        Double.parseDouble(txtThanhTien.getText().replace(",", "")),
                        "BINH_THUONG"
                );

                List<ChiTietHoaDonDTO> tempChiTiet = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String maSP = tableModel.getValueAt(i, 0).toString();
                    int soLuong = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                    double gia = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                    tempChiTiet.add(new ChiTietHoaDonDTO(maHD, maSP, soLuong, gia));
                }

                List<String> danhSachMaSP = tempChiTiet.stream()
                        .map(ChiTietHoaDonDTO::getMaSanPham)
                        .collect(Collectors.toList());
                Map<String, String> tenSanPhamMap = hdBLL.layDanhSachTenSanPham(danhSachMaSP);

                NhanVienDTO nhanVien = nvBLL.getNhanVienByID(maNV);

                KhachHangDTO khachHang = null;
                if (!maKH.isEmpty()) {
                    khachHang = khBLL.getKhachHangById(maKH);
                }

                try {
                    new PDFGeneratorHD().exportHoaDonToPDF(
                            (JFrame) SwingUtilities.getWindowAncestor(this),
                            tempHD,
                            tempChiTiet,
                            tenSanPhamMap,
                            nhanVien,
                            khachHang
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi xuất PDF: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            mainFrame.showPage("hoadon");
        }
    }

}