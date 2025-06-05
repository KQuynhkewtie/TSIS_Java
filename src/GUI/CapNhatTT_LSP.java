package GUI;

import BLL.LoaiSPBLL;
import DTO.LoaiSPDTO;
import DTO.currentuser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapNhatTT_LSP extends BasePanel {
    private LoaiSPBLL blllsp = new LoaiSPBLL();
    private JTextField txtTenLSP, txtMaLSP;
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;

    public CapNhatTT_LSP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Loại sản phẩm");
    }

    protected void initUniqueComponents() {

        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(20, 30, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblTTLSPLink = new JLabel("<html>>> <u>Thông tin loại sản phẩm</u></html>");
        lblTTLSPLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTLSPLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTLSPLink.setBounds(170, 30, 300, 30);
        add(lblTTLSPLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(430, 30, 900, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("loaisp");

            }
        });

        lblTTLSPLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maLSP = txtMaLSP.getText().trim();
                LoaiSPDTO lsp = blllsp.getLSPById(maLSP);
                if (lsp != null) {
                    TTCT_LSP ctlsp = mainFrame.getPage("ttctloaisp", TTCT_LSP.class);
                    ctlsp.setThongTin(lsp.getMaLSP(), lsp.getTenLSP(), lsp.getTrangThai());
                    mainFrame.showPage("ttctloaisp");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy loại sản phẩm!");
                }
            }
        });

        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(20, 80, 150, 30);
        add(lblMaSP);
        txtMaLSP = new JTextField();
        txtMaLSP.setBounds(20, 110, 200, 30);
        txtMaLSP.setBackground(new Color(230, 230, 230));
        txtMaLSP.setEditable(false);
        txtMaLSP.setFocusable(false);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(460, 80, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextField();
        txtTenLSP.setBounds(460, 110, 300, 30);
        add(txtTenLSP);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(20, 160, 150, 30);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(20, 190, 150, 30);
        rbSD.setSelected(true);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(20, 220, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);

        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(460, 200, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }              
                String maLSP = txtMaLSP.getText().trim();
                String tenLSP = txtTenLSP.getText().trim();
                if (maLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã loại sản phẩm!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên loại sản phẩm!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String trangthai = null;
                if (rbSD.isSelected()) {
                    trangthai = "ĐANG SỬ DỤNG";
                } else if (rbNSD.isSelected()) {
                    trangthai = "NGƯNG SỬ DỤNG";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    LoaiSPDTO lsp = new LoaiSPDTO();
                    lsp.setMaLSP(maLSP);
                    lsp.setTenLSP(tenLSP);
                    lsp.setTrangThai(trangthai);
                    System.out.println("MALSP cần cập nhật: " + maLSP);

                    boolean success = blllsp.updateLSP(lsp);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật loại sản phẩm thành công!");
                        TTCT_LSP ctlsp = mainFrame.getPage("ttctloaisp", TTCT_LSP.class);
                        ctlsp.setThongTin(lsp.getMaLSP(), lsp.getTenLSP(), lsp.getTrangThai());
                        mainFrame.showPage("ttctloaisp");

                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu: " + ex.getMessage());
                }
            }
        });
        setDefaultButtonSafe(btnLuusua);

    }

    public void loadlspInfoForUpdate(String maLSP) {
        LoaiSPDTO lsp = blllsp.getLSPById(maLSP);

        if (lsp != null) {
            txtTenLSP.setText(lsp.getTenLSP());
            txtMaLSP.setText(lsp.getMaLSP());
            if ("ĐANG SỬ DỤNG".equals(lsp.getTrangThai())) {
                rbSD.setSelected(true);
            } else if ("NGƯNG SỬ DỤNG".equals(lsp.getTrangThai())) {
                rbNSD.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy loại sản phẩm!");
        }
    }
}

