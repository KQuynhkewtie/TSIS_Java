package GUI;

import javax.swing.*;

import BLL.NhanVienBLL;
import DTO.NhanVienDTO;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Them_NV extends BasePanel {
    private NhanVienBLL nvBLL = new NhanVienBLL();
    public Them_NV(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhân viên");
    }
    protected void initUniqueComponents() {

        JLabel lblnhanvienLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblnhanvienLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblnhanvienLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblnhanvienLink.setBounds(20, 20, 100, 30);
        add(lblnhanvienLink);

        JLabel lblArrow = new JLabel(" >> Thêm nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(120, 20, 400, 30);
        add(lblArrow);

        lblnhanvienLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhanvien");
            }
        });

        //form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, 80, 150, 25);
        add(lblMaNV);
        JTextField txtMaNV = new JTextField();
        txtMaNV.setBounds(20, 110, 300, 30);
        add(txtMaNV);
        //họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(20, 160, 150, 25);
        add(lblHoTen);
        JTextField txtHoTen = new JTextField();
        txtHoTen.setBounds(20, 190, 300, 30);
        add(txtHoTen);
        //số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(20, 240, 150, 25);
        add(lblSDT);
        JTextField txtSDT = new JTextField();
        txtSDT.setBounds(20, 270, 300, 30);
        add(txtSDT);
        //cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(20, 320, 150, 25);
        add(lblCCCD);
        JTextField txtCCCD = new JTextField();
        txtCCCD.setBounds(20, 350, 300, 30);
        add(txtCCCD);
        //vị trí công việc

        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(460, 80, 300, 30);
        add(lblVTCV);

        JRadioButton rbBanHang = new JRadioButton("Nhân viên bán hàng");
        rbBanHang.setBounds(460, 110, 200, 25);
        add(rbBanHang);

        JRadioButton rbQuanLy = new JRadioButton("Nhân viên quản lý");
        rbQuanLy.setBounds(460, 140, 200, 25);
        add(rbQuanLy);


        ButtonGroup groupVTCV = new ButtonGroup();
        groupVTCV.add(rbBanHang);
        groupVTCV.add(rbQuanLy);
        //mã số thuế
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(460, 160, 150, 25);
        add(lblMST);
        JTextField txtMST = new JTextField();
        txtMST.setBounds(460, 190, 300, 30);
        add(txtMST);

        //nút lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(460, 490, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm nhân viên")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maNV = txtMaNV.getText().trim();
                String tenNV = txtHoTen.getText().trim();
                String CCCD = txtCCCD.getText().trim();
                String SDT = txtSDT.getText().trim();
                String MST = txtMST.getText().trim();
                String vitri = null;
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
                    vitri = "VT003";
                } else if (rbQuanLy.isSelected()) {
                    vitri = "VT002";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí công việc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    NhanVienDTO nv = new NhanVienDTO(maNV, tenNV, CCCD, SDT, vitri, MST);
                    boolean result = nvBLL.insertNhanVien(nv);


                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("nhanvien", NhanVien.class).refreshData();
                        mainFrame.showPage("nhanvien");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultButtonSafe(btnLuu);

        JTextField[] textFields = { txtMaNV, txtHoTen,txtSDT, txtCCCD, txtMST};

        for (int i = 0; i < textFields.length; i++) {
            final int currentIndex = i;
            textFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_DOWN) {
                        if (currentIndex < textFields.length - 1) {
                            textFields[currentIndex + 1].requestFocus();
                        }
                    } else if (key == KeyEvent.VK_UP) {
                        if (currentIndex > 0) {
                            textFields[currentIndex - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }
}
