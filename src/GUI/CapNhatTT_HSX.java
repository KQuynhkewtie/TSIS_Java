package GUI;

import javax.swing.*;
import BLL.HangSanXuatBLL;
import DTO.HangSanXuatDTO;
import DTO.currentuser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapNhatTT_HSX extends BasePanel {
    private HangSanXuatBLL bllhsx = new HangSanXuatBLL();
    private JTextField txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC;
    private JRadioButton rbSD, rbNSD;
    private ButtonGroup groupTrangThai;

    public CapNhatTT_HSX(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    protected void initUniqueComponents() {

        highlightMenuButton("Hãng sản xuất");

        JLabel lblHSXLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblHSXLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHSXLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHSXLink.setBounds(20, 20, 160, 30);
        add(lblHSXLink);

        JLabel lblTTHSXLink = new JLabel("<html>>> <u>Thông tin hãng sản xuất</u></html>");
        lblTTHSXLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTHSXLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTHSXLink.setBounds(160, 20, 280, 30);
        add(lblTTHSXLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(420, 20, 900, 30);
        add(lblArrow);

        lblHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("hangsx");
            }
        });

        lblTTHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maHSX = txtMaHSX.getText().trim();
                HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);
                if (hsx != null) {
                    TTCT_HSX cthsx = mainFrame.getPage("ttcthsx", TTCT_HSX.class);
                    cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getSdt(), hsx.getDiaChi(),
                            hsx.getTrangThai());
                    mainFrame.showPage("ttcthsx");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy hãng sản xuất!");
                }
            }
        });

        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(20, 80, 150, 25);
        add(lblMaHSX);
        txtMaHSX = new JTextField();
        txtMaHSX.setBounds(20, 110, 300, 30);
        txtMaHSX.setBackground(new Color(230, 230, 230));
        txtMaHSX.setEditable(false);
        txtMaHSX.setFocusable(false);
        add(txtMaHSX);

        JLabel lbltenHSX = new JLabel("Tên hãng sản xuất:");
        lbltenHSX.setBounds(460, 80, 150, 25);
        add(lbltenHSX);
        txtTenHSX = new JTextField();
        txtTenHSX.setBounds(460, 110, 300, 30);
        add(txtTenHSX);

        JLabel lblMaSoThue = new JLabel("Mã số thuế:");
        lblMaSoThue.setBounds(460, 160, 150, 25);
        add(lblMaSoThue);
        txtMST = new JTextField();
        txtMST.setBounds(460, 190, 300, 30);
        add(txtMST);

        JLabel lbldiachi = new JLabel("Địa chỉ:");
        lbldiachi.setBounds(20, 160, 150, 25);
        add(lbldiachi);
        txtDC = new JTextField();
        txtDC.setBounds(20, 190, 300, 30);
        add(txtDC);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(460, 240, 150, 25);
        add(lblsdt);
        txtSDT = new JTextField();
        txtSDT.setBounds(460, 270, 300, 30);
        add(txtSDT);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(20, 240, 150, 25);
        add(lblTrangThai);

        rbSD = new JRadioButton("Còn sử dụng");
        rbSD.setBounds(20, 270, 150, 25);
        rbSD.setSelected(true);
        add(rbSD);

        rbNSD = new JRadioButton("Ngưng sử dụng");
        rbNSD.setBounds(20, 310, 150, 25);
        add(rbNSD);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbSD);
        groupTrangThai.add(rbNSD);

        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(460, 490, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String maHSX = txtMaHSX.getText().trim();
                String tenHSX = txtTenHSX.getText().trim();
                String MST = txtMST.getText().trim();
                String SDT = txtSDT.getText().trim();
                String DC = txtDC.getText().trim();
                if (maHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hãng sản xuất!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hãng sản xuất!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (MST.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (DC.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (SDT.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi",
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
                    HangSanXuatDTO hsx = new HangSanXuatDTO();
                    hsx.setMaHSX(maHSX);
                    hsx.setTenHSX(tenHSX);
                    hsx.setMaSoThue(MST);
                    hsx.setSdt(SDT);
                    hsx.setDiaChi(DC);
                    hsx.setTrangThai(trangthai);

                    System.out.println("MAHSX cần cập nhật: " + maHSX);
                    boolean success = bllhsx.updateHangSanXuat(hsx);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật hãng sản xuất thành công!");
                        TTCT_HSX cthsx = mainFrame.getPage("ttcthsx", TTCT_HSX.class);
                        cthsx.setThongTin(hsx.getMaHSX(), hsx.getTenHSX(), hsx.getMaSoThue(), hsx.getSdt(),
                                hsx.getDiaChi(), hsx.getTrangThai());
                        mainFrame.showPage("ttcthsx");
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

    public void loadhsxInfoForUpdate(String maHSX) {
        HangSanXuatDTO hsx = bllhsx.getHSXbyID(maHSX);

        if (hsx != null) {

            txtMaHSX.setText(hsx.getMaHSX());
            txtTenHSX.setText(hsx.getTenHSX());
            txtMST.setText(hsx.getMaSoThue());
            txtSDT.setText(hsx.getSdt());
            txtDC.setText(hsx.getDiaChi());
            if ("ĐANG SỬ DỤNG".equals(hsx.getTrangThai())) {
                rbSD.setSelected(true);
            } else if ("NGƯNG SỬ DỤNG".equals(hsx.getTrangThai())) {
                rbNSD.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hãng sản xuất!");
        }
    }

}