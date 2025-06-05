package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.HangSanXuatBLL;
import DTO.HangSanXuatDTO;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HangSanXuat extends BasePanel {
    private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchhsxField;

    public HangSanXuat(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    @Override
    public void refreshData() {
        loadHSX(); // Load lại dữ liệu
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hãng sản xuất");
        refreshData();
    }


    protected void initUniqueComponents() {

        JLabel lblSanPham = new JLabel("Hãng sản xuất");
        lblSanPham.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPham.setBounds(20, 20, 200, 30);
        add(lblSanPham);


        searchhsxField = new JTextField("Tìm kiếm hãng sản xuất");
        searchhsxField.setBounds(20, 70, 300, 35);
        add( searchhsxField);


        searchhsxField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchhsxField.getText().equals("Tìm kiếm hãng sản xuất")) {
                    searchhsxField.setText("");
                    searchhsxField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchhsxField.getText().trim().isEmpty()) {
                    searchhsxField.setText("Tìm kiếm hãng sản xuất");
                    searchhsxField.setForeground(Color.GRAY);
                    loadHSX();
                }
            }
        });
        searchhsxField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchhsxField.getText().trim();
                if (keyword.isEmpty()) {
                    loadHSX();
                } else {
                    searchHSX();
                }
            }
        });


        JButton btnThemHSX = new JButton("+ Thêm hãng sản xuất");
        btnThemHSX.setBounds(620, 70, 170, 30);
        btnThemHSX.setBackground(Color.decode("#F0483E"));
        btnThemHSX.setForeground(Color.WHITE);
        add(btnThemHSX);

        // Table
        String[] columnNames = { "Mã hãng sản xuất", "Tên hãng sản xuất", "Mã số thuế", "Địa chỉ", "Số điện thoại" };
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
        loadHSX();


        btnThemHSX.addActionListener(e -> {
            mainFrame.showPage("themhsx");
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maHSX = table.getValueAt(row, 0).toString();
                        HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);
                        if (hsx != null) {
                            TTCT_HSX cthsx = mainFrame.getPage("ttcthsx", TTCT_HSX.class);
                            cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt(), hsx.getTrangThai());
                            mainFrame.showPage("ttcthsx");
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy hãng sản xuất!");
                        }
                    }
                }
            }
        });

        setVisible(true);
    }
    private void loadHSX() {
        model.setRowCount(0);
        List<HangSanXuatDTO> list = bllhsx.getAllHangSanXuat();

        for (HangSanXuatDTO hsx : list) {

            model.addRow(new Object[]{hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt()});
        }
    }

    private void searchHSX() {
        String keyword = searchhsxField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadHSX();
            return;
        }

        model.setRowCount(0);
        List<HangSanXuatDTO> list = bllhsx.getHSX(keyword);

        for (HangSanXuatDTO hsx : list) {
            model.addRow(new Object[]{hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getDiaChi(), hsx.getSdt()});
        }
    }

}
