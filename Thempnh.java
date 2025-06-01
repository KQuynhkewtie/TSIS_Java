package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import bll.PhieuNhapHangBLL;
import dto.PhieuNhapHangDTO;
import dto.ChiTietPhieuNhapHangDTO;
import dal.NhanViendal;
import dal.ncudal;
import dto.NhanVienDTO;
import dto.NhaCungUngDTO;
import dal.SanPhamDAL;
import dto.SanPhamDTO;

public class Thempnh extends BaseFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaPNH, txtMaNCU, txtMaNV;
    private JDateChooser dateChooserNgay;
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private NhanViendal nvDAL = new NhanViendal();
    private ncudal ncuDAL = new ncudal();

    public Thempnh() {
        super("Thêm Phiếu Nhập Hàng");
        initialize();
    }

    @Override
    protected void initUniqueComponents() {
        // Highlight menu button
        for (JButton btn : menuButtons) {
            if (btn.getText().equals("Phiếu nhập hàng")) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }

        // Tiêu đề
        JLabel lblpnhLink = new JLabel("<html><u>Phiếu nhập hàng</u></html>");
        lblpnhLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblpnhLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblpnhLink.setBounds(270, 70, 180, 30);
        add(lblpnhLink);

        JLabel lblArrow = new JLabel(" >> Thêm phiếu nhập hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(430, 70, 400, 30);
        add(lblArrow);

        lblpnhLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PhieuNhapHang().setVisible(true);
            }
        });

        // Form thông tin
        initInfoForm();

        // Bảng chi tiết
        initDetailTable();

        // Nút thao tác
        initActionButtons();

        addPDFButton();
    }

    private void initInfoForm() {
        JLabel lblMaPNH = new JLabel("Mã phiếu nhập hàng:");
        lblMaPNH.setBounds(270, 120, 150, 30);
        add(lblMaPNH);

        txtMaPNH = new JTextField();
        txtMaPNH.setBounds(270, 150, 300, 30);
        add(txtMaPNH);

        JLabel lblMNV = new JLabel("Nhân viên:");
        lblMNV.setBounds(700, 120, 150, 25);
        add(lblMNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(700, 150, 250, 30);
        add(txtMaNV);

        JButton btnChonNV = createActionButton("Chọn", 960, 150, 80, 30);
        btnChonNV.addActionListener(e -> hienThiDanhSachNhanVien());
        add(btnChonNV);

        JLabel lblMNCU = new JLabel("Nhà cung ứng:");
        lblMNCU.setBounds(270, 200, 150, 25);
        add(lblMNCU);

        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(270, 230, 250, 30);
        add(txtMaNCU);

        JButton btnChonNCU = createActionButton("Chọn", 530, 230, 80, 30);
        btnChonNCU.addActionListener(e -> hienThiDanhSachNhaCungUng());
        add(btnChonNCU);

        JLabel lblngay = new JLabel("Ngày lập phiếu:");
        lblngay.setBounds(700, 200, 150, 25);
        add(lblngay);

        dateChooserNgay = new JDateChooser();
        dateChooserNgay.setBounds(700, 230, 300, 30);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserNgay.setDate(new Date());
        add(dateChooserNgay);
    }

    private SanPhamDAL spDAL = new SanPhamDAL();

    private void initDetailTable() {
        JLabel lblChiTiet = new JLabel("Chi tiết phiếu nhập:");
        lblChiTiet.setBounds(270, 280, 200, 30);
        add(lblChiTiet);

        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng nhập", "Giá nhập", "Hạn sử dụng", "Số lô"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 2 && column <= 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Date.class;
                return Object.class;
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set up date picker for HSD column
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()) {
            private JDateChooser dateChooser = new JDateChooser();
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                dateChooser.setDateFormatString("dd/MM/yyyy");
                if (value instanceof Date) {
                    dateChooser.setDate((Date) value);
                } else if (value != null && !value.toString().isEmpty()) {
                    try {
                        dateChooser.setDate(dateFormat.parse(value.toString()));
                    } catch (Exception e) {
                        dateChooser.setDate(new Date());
                    }
                } else {
                    dateChooser.setDate(new Date());
                }
                return dateChooser;
            }

            @Override
            public Object getCellEditorValue() {
                return dateChooser.getDate() != null ? dateFormat.format(dateChooser.getDate()) : "";
            }
        });

        // Set up renderers
        DefaultTableCellRenderer nonEditableRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column < 2) {
                    c.setBackground(new Color(240, 240, 240));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(nonEditableRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(270, 310, 800, 200);
        add(scrollPane);

        // Nút thêm/xóa dòng
        JButton btnThemDong = createActionButton("Thêm dòng", 800, 520, 100, 30);
        btnThemDong.setBackground(Color.decode("#F5A623"));
        btnThemDong.addActionListener(e -> hienThiDanhSachSanPham());
        add(btnThemDong);

        JButton btnXoaDong = createActionButton("Xóa dòng", 800, 560, 100, 30);
        btnXoaDong.setBackground(Color.decode("#D0021B"));
        btnXoaDong.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnXoaDong);

        // Thiết lập navigation bằng phím
        setupKeyboardNavigation();
    }

    private void initActionButtons() {
        JButton btnLuu = createActionButton("Lưu", 800, 600, 100, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.addActionListener(e -> luuPhieuNhapHang());
        add(btnLuu);

        getRootPane().setDefaultButton(btnLuu);
    }

    private void setupKeyboardNavigation() {
        JTextField[] textFields = {txtMaPNH, txtMaNV, txtMaNCU};

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
                    txtMaNCU.requestFocus();
                } else if (key == KeyEvent.VK_DOWN && row == table.getRowCount() - 1) {
                    tableModel.addRow(new Object[]{"", "", ""});
                    table.setRowSelectionInterval(row + 1, row + 1);
                    table.setColumnSelectionInterval(0, 0);
                } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
                    if (col < table.getColumnCount() - 1) {
                        table.setColumnSelectionInterval(col + 1, col + 1);
                    } else if (row < table.getRowCount() - 1) {
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    } else {
                        tableModel.addRow(new Object[]{"", "", ""});
                        table.setRowSelectionInterval(row + 1, row + 1);
                        table.setColumnSelectionInterval(0, 0);
                    }
                }
            }
        });
    }

    // Thêm phương thức hiển thị danh sách sản phẩm
    private void hienThiDanhSachSanPham() {
        JDialog dialog = new JDialog(this, "Chọn Sản Phẩm", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField txtSearch = new JTextField("Tìm kiếm sản phẩm");
        txtSearch.setPreferredSize(new Dimension(0, 30));
        txtSearch.setForeground(Color.GRAY);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        dialog.add(searchPanel, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] columnNames = {"Mã SP", "Tên SP", "Mã Loại SP", "Mã Hãng sản xuất"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.getTableHeader().setPreferredSize(new Dimension(0, 25));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Panel nút chọn/hủy
        JPanel buttonPanel = new JPanel();
        JButton btnChon = new JButton("Chọn");
        JButton btnHuy = new JButton("Hủy");
        buttonPanel.add(btnChon);
        buttonPanel.add(btnHuy);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadSanPhamData(model, "");

        // Xử lý sự kiện focus cho ô tìm kiếm
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

        // Xử lý tìm kiếm khi nhập liệu
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

        // Sự kiện chọn sản phẩm
        btnChon.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String maSP = model.getValueAt(selectedRow, 0).toString();
                String tenSP = model.getValueAt(selectedRow, 1).toString();

                // Check if product already exists
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(maSP)) {
                        JOptionPane.showMessageDialog(dialog, "Sản phẩm này đã được thêm vào phiếu!",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Add new row with product info (including empty HSD and SoLo fields)
                tableModel.addRow(new Object[]{maSP, tenSP, "1", "", "", ""});
                dialog.dispose();

                // Focus on quantity field
                JTable mainTable = this.table;
                int newRow = tableModel.getRowCount() - 1;
                mainTable.setRowSelectionInterval(newRow, newRow);
                mainTable.setColumnSelectionInterval(2, 2);
                mainTable.editCellAt(newRow, 2);
                mainTable.getEditorComponent().requestFocusInWindow();
            }
        });

        // Sự kiện hủy
        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void loadSanPhamData(DefaultTableModel model, String keyword) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        List<SanPhamDTO> spList;
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm sản phẩm")) {
            spList = spDAL.getAllSanPham(); // Lấy tất cả nếu không có từ khóa
        } else {
            spList = spDAL.getSanPham(keyword); // Tìm kiếm theo từ khóa
        }

        for (SanPhamDTO sp : spList) {
            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getMaLSP(),
                    sp.getMaHSX()
            });
        }
    }

    private void hienThiDanhSachNhanVien() {
        JDialog dialog = new JDialog(this, "Chọn Nhân Viên", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<NhanVienDTO> nvList = nvDAL.getDanhSachNhanVien();
        String[] columnNames = {"Mã NV", "Họ tên"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (NhanVienDTO nv : nvList) {
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen()});
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
                txtMaNV.setText(model.getValueAt(selectedRow, 0).toString());
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void hienThiDanhSachNhaCungUng() {
        JDialog dialog = new JDialog(this, "Chọn Nhà Cung Ứng", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<NhaCungUngDTO> ncuList = ncuDAL.getAllNCU();
        String[] columnNames = {"Mã NCU", "Tên NCU"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (NhaCungUngDTO ncu : ncuList) {
            model.addRow(new Object[]{ncu.getMaNCU(), ncu.getTenNCU()});
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
                txtMaNCU.setText(model.getValueAt(selectedRow, 0).toString());
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void luuPhieuNhapHang() {
        // Validate dữ liệu cơ bản
        String maPNH = txtMaPNH.getText().trim();
        String maNCU = txtMaNCU.getText().trim();
        String maNV = txtMaNV.getText().trim();
        Date ngayLap = dateChooserNgay.getDate();

        if (maPNH.isEmpty() || maNCU.isEmpty() || maNV.isEmpty() || ngayLap == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pnhBLL.kiemTraMaPNHTonTai(maPNH)) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pnhBLL.kiemTraNhanVienTonTai(maNV)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pnhBLL.kiemTraNhaCungUngTonTai(maNCU)) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung ứng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy danh sách chi tiết
        List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                String maSP = tableModel.getValueAt(i, 0).toString().trim();
                String slStr = tableModel.getValueAt(i, 2).toString().trim();
                String giaStr = tableModel.getValueAt(i, 3).toString().trim();
                String hsdStr = tableModel.getValueAt(i, 4).toString().trim();
                String soLo = tableModel.getValueAt(i, 5).toString().trim();

                if (maSP.isEmpty() || slStr.isEmpty() || giaStr.isEmpty() || hsdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Dòng " + (i+1) + ": Vui lòng nhập đầy đủ thông tin chi tiết!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int soLuong = Integer.parseInt(slStr);
                double giaNhap = Double.parseDouble(giaStr);
                Date hsd = dateFormat.parse(hsdStr);

                if (soLuong <= 0 || giaNhap <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Dòng " + (i+1) + ": Số lượng và giá nhập phải lớn hơn 0!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create detail with all fields including HSD and SoLo
                ChiTietPhieuNhapHangDTO ct = new ChiTietPhieuNhapHangDTO(
                        maPNH, maSP, hsd, soLo, soLuong, giaNhap
                );
                danhSachChiTiet.add(ct);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Dòng " + (i+1) + ": Dữ liệu không hợp lệ! " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (danhSachChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất 1 sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo DTO và lưu vào DB
        PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(maPNH, maNCU, maNV, ngayLap, 0);
        pnh.setDanhSachChiTiet(danhSachChiTiet);

        if (pnhBLL.themPhieuNhapHang(pnh, danhSachChiTiet)) {
            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new PhieuNhapHang().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void addPDFButton() {
        super.addPDFButton();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Thempnh().setVisible(true);
        });
    }
}