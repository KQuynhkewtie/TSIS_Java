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
import DAL.NhaCungUngDAL;
import DTO.*;
import com.toedter.calendar.JDateChooser;
import BLL.PhieuNhapHangBLL;
import DAL.SanPhamDAL;

public class CapNhatTT_PNH extends BasePanel {
    private DefaultTableModel tableModel;
    private JTextField txtMaNCU, txtMaNV, txtMaPNH;
    private JDateChooser dateChooserNgay;
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private NhaCungUngDAL ncuDAL = new NhaCungUngDAL();
    private String maPNH;
    private SanPhamDAL spDAL = new SanPhamDAL();
    private Runnable onUpdateSuccessCallback;
    private JTable table;

    public CapNhatTT_PNH(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    public void setMaPNH(String maPNH) {
        this.maPNH = maPNH;
        loadData();
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

        JLabel lblTTPNHLink = new JLabel("<html>>> <u>Thông tin phiếu nhập hàng</u></html>");
        lblTTPNHLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTPNHLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTPNHLink.setBounds(180, 20, 300, 30);
        add(lblTTPNHLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin phiếu nhập hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(460, 20, 900, 30);
        add(lblArrow);

        lblTTPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("ttctpnh");
            }
        });

        lblPNHLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("phieunhaphang");
            }
        });

        initInfoSection();

        initTableSection();

        initActionButtons();

        loadData();
    }

    private void initInfoSection() {
        JLabel lblMaPNHLabel = new JLabel("Mã phiếu nhập hàng:");
        lblMaPNHLabel.setBounds(20, 80, 150, 25);
        add(lblMaPNHLabel);
        txtMaPNH = new JTextField();
        txtMaPNH.setBounds(20, 110, 300, 30);
        txtMaPNH.setBackground(new Color(230, 230, 230));
        txtMaPNH.setEditable(false);
        txtMaPNH.setFocusable(false);
        add(txtMaPNH);

        JLabel lblMNV = new JLabel("Mã nhân viên:");
        lblMNV.setBounds(460, 80, 150, 25);
        add(lblMNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(460, 110, 300, 30);
        add(txtMaNV);

        JLabel lblMNCU = new JLabel("Nhà cung ứng:");
        lblMNCU.setBounds(20, 160, 150, 25);
        add(lblMNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(20, 190, 250, 30);
        add(txtMaNCU);

        JButton btnChonNCU = new JButton("Chọn");
        btnChonNCU.setBounds(280, 190, 80, 30);
        btnChonNCU.setBackground(Color.decode("#F0483E"));
        btnChonNCU.setForeground(Color.WHITE);
        btnChonNCU.addActionListener(e -> hienThiDanhSachNhaCungUng());
        add(btnChonNCU);

        JLabel lblngay = new JLabel("Ngày lập phiếu:");
        lblngay.setBounds(460, 160, 150, 25);
        add(lblngay);
        dateChooserNgay = new JDateChooser();
        dateChooserNgay.setBounds(460, 190, 300, 30);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");
        add(dateChooserNgay);
    }

    private void initTableSection() {
        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng nhập", "Giá nhập (VND)", "Hạn sử dụng", "Số lô"};
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

        DefaultTableCellRenderer numberRenderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Number) {
                    setText(String.format("%,.0f", value));
                } else {
                    super.setValue(value);
                }
            }
        };
        table.getColumnModel().getColumn(3).setCellRenderer(numberRenderer);

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

        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = dateFormat.format((Date) value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

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

        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                try {
                    String value = getCellEditorValue().toString().trim();
                    if (!value.isEmpty()) {
                        int col = table.getSelectedColumn();
                        if (col == 2) {
                            Integer.parseInt(value.replaceAll("[^\\d]", ""));
                        } else if (col == 3) {
                            Double.parseDouble(value.replaceAll("[^\\d.]", ""));
                        }
                    }
                    return super.stopCellEditing();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(table, "Giá trị số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 270, 800, 200);
        add(scrollPane);
    }

    private void initActionButtons() {
        JButton btnThemDong = new JButton("Thêm sản phẩm");
        btnThemDong.setBounds(600, 500, 130, 30);
        btnThemDong.setBackground(Color.decode("#F5A623"));
        btnThemDong.setForeground(Color.WHITE);
        btnThemDong.addActionListener(e -> hienThiDanhSachSanPham());
        add(btnThemDong);

        JButton btnXoaDong = new JButton("Xóa sản phẩm");
        btnXoaDong.setBounds(600, 540, 130, 30);
        btnXoaDong.setForeground(Color.WHITE);
        btnXoaDong.setBackground(Color.decode("#D0021B"));
        btnXoaDong.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnXoaDong);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(600, 580, 130, 30);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);

        btnLuu.addActionListener(e -> {
            if (!currentuser.coQuyen("Sửa phiếu nhập hàng")) {
                JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            luuPhieuNhapHang();
        });
        add(btnLuu);

        setDefaultButtonSafe(btnLuu);
    }

    private void hienThiDanhSachNhaCungUng() {
        JDialog dialog = new JDialog((JFrame)SwingUtilities.getWindowAncestor(this), "Chọn nhà cung ứng", true);
        dialog.setSize(700, 500);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        List<NhaCungUngDTO> ncuList = ncuDAL.getAllNCU();
        String[] columnNames = {"Mã NCU", "Tên NCU"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (NhaCungUngDTO ncu : ncuList) {
            model.addRow(new Object[]{ncu.getMaNCU().trim(), ncu.getTenNCU()});
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

    private void hienThiDanhSachSanPham() {
        JDialog dialog = new JDialog((JFrame)SwingUtilities.getWindowAncestor(this), "Chọn sản phẩm", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField txtSearch = new JTextField("Tìm kiếm sản phẩm");
        txtSearch.setForeground(Color.GRAY);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        dialog.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã SP", "Tên SP", "Mã Loại SP", "Mã Hãng sản xuất"};
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

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(maSP)) {
                        JOptionPane.showMessageDialog(dialog, "Sản phẩm này đã được thêm vào phiếu!",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                tableModel.addRow(new Object[]{maSP, tenSP, "1", ""});
                dialog.dispose();

                int newRow = tableModel.getRowCount() - 1;
                table.setRowSelectionInterval(newRow, newRow);
                table.setColumnSelectionInterval(2, 2);
                table.editCellAt(newRow, 2);
                table.getEditorComponent().requestFocusInWindow();
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
                    sp.getMaHSX()
            });
        }
    }

    private void loadData() {
        if (maPNH == null) return;

        PhieuNhapHangDTO pnh = pnhBLL.layPhieuNhapHangTheoMa(maPNH);
        if (pnh != null) {
            txtMaPNH.setText(maPNH);

            txtMaNV.setText(pnh.getMaNhanVien());
            txtMaNCU.setText(pnh.getMaNCU());
            dateChooserNgay.setDate(pnh.getNgayLapPhieu() != null ?
                    pnh.getNgayLapPhieu() :
                    new Date());

            List<ChiTietPhieuNhapHangDTO> danhSachChiTiet = pnhBLL.layChiTietPhieuNhapHang(maPNH);
            tableModel.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (ChiTietPhieuNhapHangDTO ct : danhSachChiTiet) {
                SanPhamDTO sp = spDAL.getSanPhamById(ct.getMaSP());
                String tenSP = sp != null ? sp.getTenSP() : "Không xác định";

                tableModel.addRow(new Object[]{
                        ct.getMaSP(),
                        tenSP,
                        ct.getSoLuongNhap(),
                        ct.getGiaNhap(),
                        ct.getHsd() != null ? dateFormat.format(ct.getHsd()) : "",
                        ct.getSoLo() != null ? ct.getSoLo() : ""
                });
            }
        }
    }

    private void luuPhieuNhapHang() {
        if (txtMaNCU.getText().trim().isEmpty() || txtMaNV.getText().trim().isEmpty() ||
                dateChooserNgay.getDate() == null) {  // Changed validation
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date ngayLap = dateChooserNgay.getDate();

        if (!pnhBLL.kiemTraNhanVienTonTai(txtMaNV.getText())) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pnhBLL.kiemTraNhaCungUngTonTai(txtMaNCU.getText())) {
            JOptionPane.showMessageDialog(this, "Mã nhà cung ứng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

                int soLuong = Integer.parseInt(slStr.replaceAll("[^\\d]", ""));
                double giaNhap = Double.parseDouble(giaStr.replaceAll("[^\\d.]", ""));
                Date hsd = dateFormat.parse(hsdStr);

                if (soLuong <= 0 || giaNhap <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Dòng " + (i+1) + ": Số lượng và giá nhập phải lớn hơn 0!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

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

        PhieuNhapHangDTO pnh = new PhieuNhapHangDTO(
                maPNH,
                txtMaNCU.getText(),
                txtMaNV.getText(),
                ngayLap,
                0
        );

        if (pnhBLL.capNhatPhieuNhapHang(pnh, danhSachChiTiet)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            onUpdateSuccess();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setOnUpdateSuccessCallback(Runnable callback) {
        this.onUpdateSuccessCallback = callback;
    }

    private void onUpdateSuccess() {
        if (onUpdateSuccessCallback != null) {
            onUpdateSuccessCallback.run();
        }
        mainFrame.showPage("ttctpnh");
    }
}
