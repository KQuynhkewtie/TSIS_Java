package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.NhanVienBLL;
import DTO.NhanVienDTO;
import BLL.VaiTroBLL;
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
import java.util.Map;

public class NhanVien extends BasePanel {
    private VaiTroBLL bllvt = new VaiTroBLL();
    private NhanVienBLL bllnv = new NhanVienBLL();
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchnvField;
    private Map<String, String> viTriMap;
    public NhanVien(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void refreshData() {
        loadNhanVien();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhân viên");
        refreshData();
    }

    protected void initUniqueComponents() {

        JLabel lblNhanVien = new JLabel("Nhân viên", SwingConstants.LEFT);
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 20));
        lblNhanVien.setBounds(20, 20, 200, 30);
        add(lblNhanVien);


        searchnvField = new JTextField("Tìm kiếm nhân viên");
        searchnvField.setBounds(20, 70, 300, 35);
        add(searchnvField);
        searchnvField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchnvField.getText().equals("Tìm kiếm nhân viên")) {
                    searchnvField.setText("");
                    searchnvField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchnvField.getText().trim().isEmpty()) {
                    searchnvField.setText("Tìm kiếm nhân viên");
                    searchnvField.setForeground(Color.GRAY);
                    loadNhanVien();
                }
            }
        });
        searchnvField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchnvField.getText().trim();
                if (keyword.isEmpty()) {
                    loadNhanVien();
                } else {
                    searchNhanVien();
                }
            }
        });
        JButton btnThemNV = new JButton("+ Thêm nhân viên");
        btnThemNV.setBounds(620, 70, 140, 30);
        btnThemNV.setBackground(Color.decode("#F0483E"));
        btnThemNV.setForeground(Color.WHITE);
        add(btnThemNV);


        String[] columnNames = { "Mã", "Họ tên","CCCD", "SĐT", "Vị trí công việc","Mã số thuế"};
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

        loadNhanVien();

        btnThemNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("themnv");
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String manv = table.getValueAt(row, 0).toString();
                        NhanVienDTO nv = bllnv.getNhanVienByID(manv);
                        if (nv != null) {
                            TTCT_NV ttctNVPage = mainFrame.getPage("ttctnv", TTCT_NV.class);
                            ttctNVPage.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(),
                                    nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
                            mainFrame.showPage("ttctnv");
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!");
                        }
                    }
                }
            }
        });

    }
    private void loadNhanVien() {
        model.setRowCount(0);
        List<NhanVienDTO> list = bllnv.getAllNhanVien();
        for (NhanVienDTO nv : list) {

            String tenViTri = bllvt.getTenVaiTro(nv.getViTriCongViec());
            if (tenViTri == null) tenViTri = "Chưa xác định";

            model.addRow(new Object[]{ nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(), tenViTri, nv.getMaSoThue(), nv.getTrangThai()});
        }
    }
    private void searchNhanVien() {
        String keyword = searchnvField.getText().trim();
        if (keyword.equals("Tìm") || keyword.isEmpty()) {
            loadNhanVien();
            return;
        }

        model.setRowCount(0);
        List<NhanVienDTO> list = bllnv.getNhanVien(keyword);

        for (NhanVienDTO nv : list) {
            String tenViTri = nv.getTenVaiTro();
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen(),  nv.getCccd(), nv.getSdt(), tenViTri, nv.getMaSoThue(), nv.getTrangThai()});
        }
    }
}