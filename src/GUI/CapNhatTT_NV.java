package GUI;

import javax.swing.*;

import BLL.NhanVienBLL;
import DTO.NhanVienDTO;
import DTO.currentuser;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapNhatTT_NV extends BasePanel {
    private NhanVienBLL bllnv = new NhanVienBLL();
    private JTextField   txtTenNV, txtMaNV, txtCCCD, txtSDT,  txtMST;
    private JRadioButton rbBanHang, rbQuanLy,rbDangLam, rbĐanghi;
    private ButtonGroup groupVTCV,groupTrangThai;

    public CapNhatTT_NV(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhân viên");
    }
    protected void initUniqueComponents() {

        JLabel lblnvLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnvLink.setBounds(20, 20, 100, 30);
        add(lblnvLink);

        JLabel lblTTnvLink = new JLabel("<html>>> <u>Thông tin nhân viên</u></html>");
        ;
        lblTTnvLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTnvLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTnvLink.setBounds(120, 20, 240, 30);
        add(lblTTnvLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(340, 20, 900, 30);
        add(lblArrow);

        lblnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhanvien");
            }
        });


        lblTTnvLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maNV =  txtMaNV.getText().trim();
                NhanVienDTO nv = bllnv.getNhanVienByID(maNV);
                if (nv != null) {
                    TTCT_NV ttctPage = mainFrame.getPage("ttctnv", TTCT_NV.class);
                    ttctPage.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getSdt(),
                            nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
                    mainFrame.showPage("ttctnv");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!");
                }
            }
        });

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, 80, 150, 25);
        add(lblMaNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(20, 110, 300, 30);
        txtMaNV.setBackground(new Color(230, 230, 230));
        txtMaNV.setEditable(false);
        txtMaNV.setFocusable(false);
        add(txtMaNV);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(20, 160, 150, 25);
        add(lblHoTen);
        txtTenNV = new JTextField();
        txtTenNV.setBounds(20, 190, 300, 30);
        add(txtTenNV);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(20, 240, 150, 25);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(20, 270, 300, 30);
        add(txtSDT);

        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(20, 320, 150, 25);
        add(lblCCCD);
        txtCCCD = new JTextField();
        txtCCCD.setBounds(20, 350, 300, 30);
        add(txtCCCD);


        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(460, 80, 150, 25);
        add(lblVTCV);

        rbBanHang = new JRadioButton("Nhân viên bán hàng");
        rbBanHang.setBounds(460, 110, 200, 25);
        add(rbBanHang);

        rbQuanLy = new JRadioButton("Nhân viên quản lý");
        rbQuanLy.setBounds(460, 140, 200, 25);
        add(rbQuanLy);


        groupVTCV = new ButtonGroup();
        groupVTCV.add(rbBanHang);
        groupVTCV.add(rbQuanLy);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(460, 160, 150, 25);
        add(lblMST);
        txtMST = new JTextField();
        txtMST.setBounds(460, 190, 300, 30);
        add(txtMST);


        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(460, 240, 150, 25);
        add(lblTrangThai);

        rbDangLam = new JRadioButton("Đang làm");
        rbDangLam.setBounds(460, 270, 150, 25);
        rbDangLam.setSelected(true);
        add(rbDangLam);

        rbĐanghi = new JRadioButton("Đã nghỉ");
        rbĐanghi.setBounds(460, 300, 150, 25);
        add(rbĐanghi);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangLam);
        groupTrangThai.add(rbĐanghi);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(460, 350, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);

        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa nhân viên")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                String CCCD = txtCCCD.getText().trim();
                String SDT = txtSDT.getText().trim();
                String MST = txtMST.getText().trim();
                String maVT = null;

                System.out.println("MaVT cần cập nhật: " + maVT);


                if (maNV.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenNV.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (CCCD.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập căn cước công dân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (SDT.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (MST.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã số thuế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (rbBanHang.isSelected()) {
                    maVT = "VT003";
                } else if (rbQuanLy.isSelected()) {
                    maVT = "VT002";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String trangthai = null;
                if (rbDangLam.isSelected()) {
                    trangthai = "ĐANG LÀM";
                } else if (rbĐanghi.isSelected()) {
                    trangthai = "ĐÃ NGHỈ";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNhanVien(maNV);
                    nv.setHoTen(tenNV);
                    nv.setCccd(CCCD);
                    nv.setSdt(SDT);
                    nv.setViTriCongViec(maVT);
                    nv.setMaSoThue(MST);
                    nv.setTrangThai(trangthai);

                    boolean success = bllnv.updateNhanVien(nv);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!");
                        TTCT_NV ttctPage = mainFrame.getPage("ttctnv", TTCT_NV.class);
                        ttctPage.setThongTin(nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(),
                                nv.getSdt(), nv.getViTriCongViec(), nv.getMaSoThue(), nv.getTrangThai());
                        mainFrame.showPage("ttctnv");

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
    public void loadnvInfoForUpdate(String maNV) {
        NhanVienDTO nv = bllnv.getNhanVienByID(maNV);

        if (nv != null) {
            txtMaNV.setText(nv.getMaNhanVien());
            txtTenNV.setText(nv.getHoTen());
            txtCCCD.setText(nv.getCccd());
            txtSDT.setText(nv.getSdt());
            txtMST.setText(nv.getMaSoThue());
            if ("VT003".equals(nv.getViTriCongViec())) {
                rbBanHang.setSelected(true);
            } else if ("VT002".equals(nv.getViTriCongViec())) {
                rbQuanLy.setSelected(true);
            } else {
                groupVTCV.clearSelection();
            }

            if ("ĐANG LÀM".equals(nv.getTrangThai())) {
                rbDangLam.setSelected(true);
            } else if ("ĐÃ NGHỈ".equals(nv.getTrangThai())) {
                rbĐanghi.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        }
    }

}
