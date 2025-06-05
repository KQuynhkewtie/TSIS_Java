package GUI;

import BLL.PhieuNhapHangBLL;
import DTO.PhieuNhapHangDTO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class PhieuNhapHang extends BasePanel {
    private PhieuNhapHangBLL pnhBLL = new PhieuNhapHangBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private JDateChooser dateChooserFrom, dateChooserTo;
    private JTextField txtMinAmount, txtMaxAmount;
    private JComboBox<String> searchCriteriaComboBox;
    private Timer searchTimer;


    public PhieuNhapHang(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void refreshData() {
        loadDataToTable();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Phiếu nhập hàng");
        refreshData();
    }
    protected void initUniqueComponents() {
        JLabel lblTitle = new JLabel("Phiếu nhập hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(20, 20, 300, 30);
        add(lblTitle);

        initSearchComponents();
        initAdvancedFilters();

        initTable();

        JButton btnThem = new JButton("+ Thêm phiếu nhập");
        btnThem.setBounds(620, 70, 160, 35);
        btnThem.setBackground(Color.decode("#F0483E"));
        btnThem.setForeground(Color.WHITE);
        btnThem.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                mainFrame.showPage("thempnh");
            });
        });
        add(btnThem);

        loadDataToTable();


    }

    private void initSearchComponents() {
        searchField = new JTextField("Tìm kiếm phiếu nhập hàng");
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
                if (searchField.getText().equals("Tìm kiếm phiếu nhập hàng")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Tìm kiếm phiếu nhập hàng");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });


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
    }
    private void performSearchWithDelay() {
        if (searchTimer != null) {
            searchTimer.stop();
        }

        searchTimer = new Timer(500, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        searchTimer.setRepeats(false);
        searchTimer.start();
    }

    private void resetFiltersAndReload() {
        searchField.setText("Tìm kiếm phiếu nhập hàng");
        searchField.setForeground(Color.GRAY);

        dateChooserFrom.setDate(null);
        dateChooserTo.setDate(null);

        txtMinAmount.setText("");
        txtMaxAmount.setText("");

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
    }

    private void initTable() {
        String[] columnNames = {"Mã phiếu nhập", "Mã nhà cung ứng", "Mã nhân viên", "Ngày lập", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
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
                        String maPNH = model.getValueAt(row, 0).toString();
                        openChiTietPhieuNhapHang(maPNH);
                    }
                }
            }
        });

        addExceltButton(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 190, 800, 420);
        add(scrollPane);
    }

    private void openChiTietPhieuNhapHang(String maPNH) {
        SwingUtilities.invokeLater(() -> {
            TTCT_PNH ctForm = mainFrame.getPage("ttctpnh", TTCT_PNH.class);
            ctForm.loadPhieuNhapInfo(maPNH);
            mainFrame.showPage("ttctpnh");
        });
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.equals("Tìm kiếm phiếu nhập hàng")) {
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

        searchPhieuNhapHang(keyword, fromDate, toDate, minAmount, maxAmount);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<PhieuNhapHangDTO> danhSach = pnhBLL.layDanhSachPhieuNhapHang();
        displayData(danhSach);
    }

    private void searchPhieuNhapHang(String keyword, String fromDate, String toDate,
                                     Double minAmount, Double maxAmount) {
        model.setRowCount(0);
        List<PhieuNhapHangDTO> danhSach = pnhBLL.timKiemPhieuNhapHang(
                keyword, fromDate, toDate, minAmount, maxAmount);
        displayData(danhSach);
    }

    private void displayData(List<PhieuNhapHangDTO> danhSach) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (PhieuNhapHangDTO pnh : danhSach) {
            Object[] row = {
                    pnh.getMaPNH(),
                    pnh.getMaNCU(),
                    pnh.getMaNhanVien(),
                    pnh.getNgayLapPhieu() != null ? dateFormat.format(pnh.getNgayLapPhieu()) : "N/A",
                    String.format("%,.2f", pnh.getThanhTien())
            };
            model.addRow(row);
        }
    }
}