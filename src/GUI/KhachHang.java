package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import DTO.KhachHangDTO;
import BLL.KhachHangBLL;

public class KhachHang extends BasePanel {
    private KhachHangBLL bllkhachhang = new KhachHangBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchcustomerField;

    public KhachHang(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    @Override
    public void refreshData() {
        loadKhachHang();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Khách hàng");
        refreshData();
    }

    protected void initUniqueComponents() {

        JLabel lblKH = new JLabel("Khách hàng", SwingConstants.LEFT);
        lblKH.setFont(new Font("Arial", Font.BOLD, 22));
        lblKH.setBounds(20, 20, 200, 30);
        add(lblKH);

        searchcustomerField = new JTextField("Tìm kiếm khách hàng");
        searchcustomerField.setBounds(20, 70, 300, 35);
        add(searchcustomerField);

        searchcustomerField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchcustomerField.getText().equals("Tìm kiếm khách hàng")) {
                    searchcustomerField.setText("");
                    searchcustomerField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchcustomerField.getText().trim().isEmpty()) {
                    searchcustomerField.setText("Tìm kiếm khách hàng");
                    searchcustomerField.setForeground(Color.GRAY);
                    loadKhachHang();
                }
            }
        });
        searchcustomerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchcustomerField.getText().trim();
                if (keyword.isEmpty()) {
                    loadKhachHang();
                } else {
                    searchKhachHang();
                }
            }
        });


        JButton btnThemKH = new JButton("+ Thêm Khách hàng");
        btnThemKH.setBounds(620, 70, 150, 30);
        btnThemKH.setBackground(Color.decode("#F0483E"));
        btnThemKH.setForeground(Color.WHITE);
        add(btnThemKH);


        String[] columnNames = {"Mã khách hàng", "Tên khách hàng", "Điểm tích lũy", "Loại khách hàng", "SĐT"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 110, 800, 500);
        add(scrollPane);

        addExceltButton(table);

        loadKhachHang();

        btnThemKH.addActionListener(e -> {
            mainFrame.showPage("themkh");
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maKhachHang = table.getValueAt(row, 0).toString();
                        KhachHangDTO kh = bllkhachhang.getKhachHangById(maKhachHang);
                        if (kh != null) {
                            TTCT_KH ttctKhPage = mainFrame.getPage("ttctkh", TTCT_KH.class);
                            ttctKhPage.setThongTin( kh.getMaKH(), kh.getHoTen(),
                                    kh.getDiemTichLuy(), kh.getLoaiKhach(),kh.getSdt(), kh.getCCCD());
                            mainFrame.showPage("ttctkh");
                        }
                    }
                }
            }
        });
    }

    private void loadKhachHang() {
        model.setRowCount(0);
        List<KhachHangDTO> list = bllkhachhang.getAllKhachHang();
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{ kh.getMaKH(), kh.getHoTen(),kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSdt(), kh.getCCCD()});
        }
    }

    private void searchKhachHang() {
        String keyword = searchcustomerField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadKhachHang();
            return;
        }

        model.setRowCount(0);
        List<KhachHangDTO> list = bllkhachhang.getKhachHang(keyword);

        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{ kh.getMaKH(),kh.getHoTen(), kh.getDiemTichLuy(), kh.getLoaiKhach(),kh.getSdt(), kh.getCCCD()});
        }
    }
}