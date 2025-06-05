package GUI;

import javax.swing.*;

import BLL.NhaCungUngBLL;
import DTO.NhaCungUngDTO;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapNhatTT_NCU extends BasePanel {
    private NhaCungUngBLL bllncu = new NhaCungUngBLL();
    private JTextField txtTenNCU, txtMaNCU, txtMSTHUE, txtDC, txtSDT, txtEMAIL;
    private JRadioButton rbHT, rbNHT;
    private ButtonGroup groupTrangThai;
    public CapNhatTT_NCU(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhà cung ứng");
    }
    protected void initUniqueComponents() {

        JLabel lblncuLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblncuLink.setBounds(20, 20, 160, 30);
        add(lblncuLink);

        JLabel lblTTncuLink = new JLabel("<html>>> <u>Thông tin nhà cung ứng</u></html>");
        lblTTncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTncuLink.setBounds(160, 20, 280, 30);
        add(lblTTncuLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(420, 20, 900, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhacungung");
            }
        });

        lblTTncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maNCU =  txtMaNCU.getText().trim();
                NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);
                if (ncu != null) {
                    TTCT_NCU ttctPage = mainFrame.getPage("ttctncu", TTCT_NCU.class);
                    ttctPage.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(),
                            ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());
                    mainFrame.showPage("ttctncu");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung ứng!");
                }
            }
        });


        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(20, 80, 150, 25);
        add(lblMaNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(20, 110, 300, 30);
        txtMaNCU.setBackground(new Color(230, 230, 230));
        txtMaNCU.setEditable(false);
        txtMaNCU.setFocusable(false);
        add(txtMaNCU);

        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(20, 160, 150, 25);
        add(lblTen);
        txtTenNCU = new JTextField();
        txtTenNCU.setBounds(20, 190, 300, 30);
        add(txtTenNCU);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(20, 240, 150, 25);
        add(lblMST);
        txtMSTHUE = new JTextField();
        txtMSTHUE.setBounds(20, 270, 300, 30);
        add(txtMSTHUE);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(20, 320, 150, 25);
        add(lblDC);
        txtDC = new JTextField();
        txtDC.setBounds(20, 350, 300, 30);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(460, 80, 150, 25);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(460, 110, 300, 30);
        add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(460, 160, 150, 25);
        add(lblEmail);
        txtEMAIL = new JTextField();
        txtEMAIL.setBounds(460, 190, 300, 30);
        add(txtEMAIL);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(460, 240, 150, 25);
        add(lblTrangThai);

        rbHT = new JRadioButton("Đang hợp tác");
        rbHT.setBounds(460, 270, 150, 25);
        rbHT.setSelected(true);
        add(rbHT);

        rbNHT = new JRadioButton("Ngưng hợp tác");
        rbNHT.setBounds(460, 310, 150, 25);
        add(rbNHT);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbHT);
        groupTrangThai.add(rbNHT);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(460, 490, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa nhà cung ứng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maNCU = txtMaNCU.getText().trim();
                String tenNCU = txtTenNCU.getText().trim();
                String MST = txtMSTHUE.getText().trim();
                String DC = txtDC.getText().trim();
                String SDT = txtSDT.getText().trim();
                String EMAIL = txtEMAIL.getText().trim();
                if (maNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (EMAIL.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String trangthai = null;
                if (rbHT.isSelected()) {
                    trangthai = "ĐANG HỢP TÁC";
                } else if (rbNHT.isSelected()) {
                    trangthai = "NGƯNG HỢP TÁC";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    NhaCungUngDTO ncu = new NhaCungUngDTO();
                    ncu.setMaNCU(maNCU);
                    ncu.setTenNCU(tenNCU);
                    ncu.setMaSoThue(MST);
                    ncu.setDiaChi(DC);
                    ncu.setSdt(SDT);
                    ncu.setEmail(EMAIL);
                    ncu.setTrangThai(trangthai);

                    boolean success = bllncu.updateNCU(ncu);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhà cung ứng thành công!");
                        TTCT_NCU ttctPage = mainFrame.getPage("ttctncu", TTCT_NCU.class);
                        ttctPage.setThongTin(ncu.getTenNCU(), ncu.getMaNCU(), ncu.getMaSoThue(),
                                ncu.getDiaChi(), ncu.getSdt(), ncu.getEmail(), ncu.getTrangThai());
                        mainFrame.showPage("ttctncu");
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
    public void loadncuInfoForUpdate(String maNCU) {
        NhaCungUngDTO ncu = bllncu.getNCUById(maNCU);

        if (ncu != null) {
            txtTenNCU.setText(ncu.getTenNCU());
            txtMaNCU.setText(ncu.getMaNCU());
            txtMSTHUE.setText(ncu.getMaSoThue());
            txtDC.setText(ncu.getDiaChi());
            txtSDT.setText(ncu.getSdt());
            txtEMAIL.setText(ncu.getEmail());
            if ("ĐANG HỢP TÁC".equals(ncu.getTrangThai())) {
                rbHT.setSelected(true);
            } else if ("NGƯNG HỢP TÁC".equals(ncu.getTrangThai())) {
                rbNHT.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung ứng!");
        }
    }
}
