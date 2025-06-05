package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.LoaiSPBLL;
import DTO.LoaiSPDTO;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoaiSP extends BasePanel {
    private LoaiSPBLL bllloaisp = new LoaiSPBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchlspField;
    public LoaiSP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    @Override
    public void refreshData() {
        loadLSP();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Loại sản phẩm");
        refreshData();
    }

    protected void initUniqueComponents() {
        JLabel lblLoaiSanPham = new JLabel("Loại sản phẩm", SwingConstants.LEFT);
        lblLoaiSanPham.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPham.setBounds(20, 20, 250, 30);
        add(lblLoaiSanPham);


        searchlspField = new JTextField("Tìm kiếm loại sản phẩm");
        searchlspField.setBounds(20, 70, 300, 35);
        add(searchlspField);


        searchlspField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchlspField.getText().equals("Tìm kiếm loại sản phẩm")) {
                    searchlspField.setText("");
                    searchlspField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchlspField.getText().trim().isEmpty()) {
                    searchlspField.setText("Tìm kiếm loại sản phẩm");
                    searchlspField.setForeground(Color.GRAY);
                    loadLSP();
                }
            }
        });

        searchlspField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchlspField.getText().trim();
                if (keyword.isEmpty()) {
                    loadLSP();
                } else {
                    searchLSP();
                }
            }
        });


        JButton btnThemLSP = new JButton("+ Thêm loại sản phẩm");
        btnThemLSP.setBounds(620, 70, 170, 30);
        btnThemLSP.setBackground(Color.decode("#F0483E"));
        btnThemLSP.setForeground(Color.WHITE);
        add(btnThemLSP);

        // Table
        String[] columnNames = {"Mã loại sản phẩm", "Tên loại sản phẩm"};
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
        loadLSP();


        btnThemLSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("themloaisp");
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maLSP = table.getValueAt(row, 0).toString();
                        LoaiSPDTO lsp = bllloaisp.getLSPById(maLSP.trim());
                        if (lsp != null) {
                            TTCT_LSP ctlsp = mainFrame.getPage("ttctloaisp", TTCT_LSP.class);
                            ctlsp.setThongTin( lsp.getMaLSP(),lsp.getTenLSP(), lsp.getTrangThai());
                            mainFrame.showPage("ttctloaisp");
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy loại sản phẩm!");
                        }
                    }
                }
            }
        });
        setVisible(true);
    }

    private void loadLSP() {
        model.setRowCount(0);
        List<LoaiSPDTO> list = bllloaisp.getAllLSP();
        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getMaLSP(),lsp.getTenLSP()});
        }
    }

    private void searchLSP() {
        String keyword = searchlspField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadLSP();
            return;
        }

        model.setRowCount(0);
        List<LoaiSPDTO> list = bllloaisp.getLSP(keyword);

        for (LoaiSPDTO lsp : list) {
            model.addRow(new Object[]{lsp.getMaLSP(),lsp.getTenLSP() });
        }

    }

}

