package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.NhaCungUngBLL;
import DTO.NhaCungUngDTO;
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

public class NhaCungUng extends BasePanel {
    private NhaCungUngBLL bllncu = new NhaCungUngBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchncuField;
    public NhaCungUng(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void refreshData() {
        loadNCU();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhà cung ứng");
        refreshData();
    }
    protected void initUniqueComponents() {

        JLabel lblNCU = new JLabel("Nhà cung ứng");
        lblNCU.setFont(new Font("Arial", Font.BOLD, 20));
        lblNCU.setBounds(20, 20, 200, 30);
        add(lblNCU);


        searchncuField = new JTextField("Tìm kiếm nhà cung ứng");
        searchncuField.setBounds(20, 70, 300, 35);
        add(searchncuField);

        searchncuField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchncuField.getText().equals("Tìm kiếm nhà cung ứng")) {
                    searchncuField.setText("");
                    searchncuField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchncuField.getText().trim().isEmpty()) {
                    searchncuField.setText("Tìm kiếm nhà cung ứng");
                    searchncuField.setForeground(Color.GRAY);
                    loadNCU();
                }
            }
        });
        searchncuField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchncuField.getText().trim();
                if (keyword.isEmpty()) {
                    loadNCU();
                } else {
                    searchNCU();
                }
            }
        });


        JButton btnThemNCU = new JButton("+ Thêm nhà cung ứng");
        btnThemNCU.setBounds(620, 70, 180, 30);
        btnThemNCU.setBackground(Color.decode("#F0483E"));
        btnThemNCU.setForeground(Color.WHITE);
        add(btnThemNCU);

        String[] columnNames = { "Mã nhà cung ứng", "Tên nhà cung ứng", "Mã số thuế", "Địa chỉ", "Số điện thoại", "Email" };
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
        loadNCU();

        btnThemNCU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("themncu");
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String maNCU = table.getValueAt(row, 0).toString();
                        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);
                        if (ncu != null) {
                            TTCT_NCU ttctNCUPage = mainFrame.getPage("ttctncu", TTCT_NCU.class);
                            ttctNCUPage.setThongTin(ncu.getMaNCU(),ncu.getTenNCU(),  ncu.getMaSoThue(), ncu.getDiaChi(),
                                    ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());
                            mainFrame.showPage("ttctncu");
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung ứng!");
                        }
                    }
                }
            }
        });
    }
    private void loadNCU() {
        model.setRowCount(0);
        List<NhaCungUngDTO> list = bllncu.getAllNCU();
        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ ncu.getMaNCU(),ncu.getTenNCU(), ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
    }

    private void searchNCU() {
        String keyword = searchncuField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadNCU();
            return;
        }

        model.setRowCount(0);
        List<NhaCungUngDTO> list = bllncu.getNCU(keyword);

        for (NhaCungUngDTO ncu : list) {
            model.addRow(new Object[]{ncu.getMaNCU(),ncu.getTenNCU(),  ncu.getMaSoThue(), ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail()});
        }
    }
}