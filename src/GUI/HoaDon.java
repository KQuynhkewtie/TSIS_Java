package GUI;

import BLL.HoaDonBLL;
import DTO.HoaDonDTO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class HoaDon extends BasePanel {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private JDateChooser dateChooserFrom, dateChooserTo;
    private JTextField txtMinAmount, txtMaxAmount;
    private HoaDonBLL hdBLL = new HoaDonBLL();
    private JComboBox<String> cboTrangThai;
    private Timer searchTimer;

    public HoaDon(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void refreshData() {
        loadDataToTable();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hóa đơn");
        refreshData();
    }

    protected void initUniqueComponents() {

        JLabel lblHD = new JLabel("Hóa đơn", SwingConstants.LEFT);
        lblHD.setFont(new Font("Arial", Font.BOLD, 20));
        lblHD.setBounds(20, 20, 200, 30);
        add(lblHD);

        initSearchComponents();
        initAdvancedFilters();

        initTable();

        JButton btnThemHd = new JButton("+ Thêm hóa đơn");
        btnThemHd.setBounds(620, 70, 160, 35);
        btnThemHd.setBackground(Color.decode("#F0483E"));
        btnThemHd.setForeground(Color.WHITE);

        btnThemHd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                mainFrame.showPage("themhd");

            });
        });
        add(btnThemHd);

        loadDataToTable();

        addExceltButton(table);
    }

    private void initSearchComponents() {
        searchField = new JTextField("Tìm kiếm hóa đơn");
        searchField.setBounds(20, 70, 400, 35);
        add(searchField);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBounds(440, 70, 100, 35);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBackground(Color.decode("#F5A623"));
        btnSearch.addActionListener(e -> performSearch());
        add(btnSearch);

        JButton btnReload = new JButton("Tải lại");
        btnReload.setBounds(620, 110, 100, 35);
        btnReload.setForeground(Color.WHITE);
        btnReload.setBackground(Color.decode("#4CAF50"));
        btnReload.addActionListener(e -> resetFiltersAndReload());
        add(btnReload);

        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm hóa đơn")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Tìm kiếm hóa đơn");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Thêm DocumentListener cho tìm kiếm real-time
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearchWithDelay();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearchWithDelay();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearchWithDelay();
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
    }

    private void performSearchWithDelay() {
        if (searchTimer != null) {
            searchTimer.stop();
        }

        searchTimer = new Timer(500, new ActionListener() { // 500ms delay
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchTimer.setRepeats(false);
        searchTimer.start();
    }

    private void resetFiltersAndReload() {
        searchField.setText("Tìm kiếm hóa đơn");
        searchField.setForeground(Color.GRAY);

        dateChooserFrom.setDate(null);
        dateChooserTo.setDate(null);

        txtMinAmount.setText("");
        txtMaxAmount.setText("");

        cboTrangThai.setSelectedIndex(0);

        loadDataToTable();
    }

    private void initAdvancedFilters() {
        int yPos = 120;

        JLabel lblFromDate = new JLabel("Từ ngày:");
        lblFromDate.setBounds(20, yPos, 70, 25);
        add(lblFromDate);

        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setBounds(70, yPos, 150, 25);
        dateChooserFrom.setDateFormatString("dd-MM-yyyy");
        add(dateChooserFrom);

        JLabel lblToDate = new JLabel("Đến ngày:");
        lblToDate.setBounds(240, yPos, 70, 25);
        add(lblToDate);

        dateChooserTo = new JDateChooser();
        dateChooserTo.setBounds(330, yPos, 150, 25);
        dateChooserTo.setDateFormatString("dd-MM-yyyy");
        add(dateChooserTo);

        yPos += 35;

        JLabel lblMinAmount = new JLabel("Từ tiền:");
        lblMinAmount.setBounds(20, yPos, 60, 25);
        add(lblMinAmount);

        txtMinAmount = new JTextField();
        txtMinAmount.setBounds(70, yPos, 150, 25);
        add(txtMinAmount);

        JLabel lblMaxAmount = new JLabel("Đến tiền:");
        lblMaxAmount.setBounds(240, yPos, 70, 25);
        add(lblMaxAmount);

        txtMaxAmount = new JTextField();
        txtMaxAmount.setBounds(330, yPos, 150, 25);
        add(txtMaxAmount);

        yPos += 35;
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(20, yPos, 70, 25);
        add(lblTrangThai);

        cboTrangThai = new JComboBox<>(new String[] { "Tất cả", "Bình thường", "Đã hủy" });
        cboTrangThai.setBounds(90, yPos, 150, 25);
        add(cboTrangThai);
    }

    private void initTable() {
        String[] columnNames = { "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày bán", "Thành tiền",
                "Trạng thái" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                String trangThai = (String) model.getValueAt(row, model.getColumnCount() - 1);

                if ("Đã hủy".equals(trangThai)) {
                    c.setBackground(Color.LIGHT_GRAY);
                    c.setForeground(Color.DARK_GRAY);
                } else {
                    c.setBackground(getBackground());
                    c.setForeground(getForeground());
                }
                return c;
            }
        };
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maHoaDon = model.getValueAt(row, 0).toString();
                        openChiTietHoaDon(maHoaDon);
                    }
                }
            }
        });

        addExceltButton(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 220, 800, 390);
        add(scrollPane);
    }

    private void openChiTietHoaDon(String maHoaDon) {
        SwingUtilities.invokeLater(() -> {
            TTCT_HD ctForm = mainFrame.getPage("ttcthd", TTCT_HD.class);
            ctForm.loadHoaDonInfo(maHoaDon);
            mainFrame.showPage("ttcthd");
        });
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.equals("Tìm kiếm hóa đơn")) {
            keyword = "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fromDate = "";
        String toDate = "";

        try {
            if (dateChooserFrom.getDate() != null) {
                fromDate = sdf.format(dateChooserFrom.getDate());
            }
            if (dateChooserTo.getDate() != null) {
                toDate = sdf.format(dateChooserTo.getDate());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!");
            return;
        }

        Double minAmount = null;
        Double maxAmount = null;

        String trangThai = cboTrangThai.getSelectedItem().toString();

        try {
            if (!txtMinAmount.getText().isEmpty()) {
                minAmount = Double.parseDouble(txtMinAmount.getText());
            }
            if (!txtMaxAmount.getText().isEmpty()) {
                maxAmount = Double.parseDouble(txtMaxAmount.getText());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!");
            return;
        }

        searchHoaDon(keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<HoaDonDTO> danhSach = hdBLL.layDanhSachHoaDon();
        displayData(danhSach);
    }

    private void searchHoaDon(String keyword, String fromDate, String toDate,
            Double minAmount, Double maxAmount, String trangThai) {
        model.setRowCount(0);

        // Chuyển đổi giá trị trạng thái từ combobox sang giá trị DB
        String dbTrangThai = null;
        if ("Bình thường".equals(trangThai)) {
            dbTrangThai = "BINH_THUONG";
        } else if ("Đã hủy".equals(trangThai)) {
            dbTrangThai = "DA_HUY";
        }

        List<HoaDonDTO> danhSach = hdBLL.timKiemHoaDon(
                keyword, fromDate, toDate, minAmount, maxAmount, trangThai);
        displayData(danhSach);
    }

    private void displayData(List<HoaDonDTO> danhSach) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (HoaDonDTO hd : danhSach) {
            String trangThai = "Bình thường";
            if ("DA_HUY".equals(hd.getTrangThai())) {
                trangThai = "Đã hủy";
            }

            Object[] row = {
                    hd.getMaHoaDon(),
                    hd.getMaNhanVien(),
                    hd.getMaKH(),
                    hd.getNgayBan() != null ? dateFormat.format(hd.getNgayBan()) : "N/A",
                    String.format("%,.0f VNĐ", hd.getThanhTien()),
                    trangThai
            };
            model.addRow(row);
        }
    }
}