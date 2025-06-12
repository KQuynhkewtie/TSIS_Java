package GUI;


import javax.swing.*;

import DTO.KhachHangDTO;
import BLL.KhachHangBLL;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Them_KH extends BasePanel {
    private JTextField txtMaKH, txtHoTen, txtsdt, txtDiemTL, txtLoaiKH, txtcccd;
    private KhachHangBLL khBLL= new KhachHangBLL();
    public Them_KH(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    @Override
    public void onPageShown() {
        highlightMenuButton("Khách hàng");
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtsdt.setText("");
        txtDiemTL.setText("0.0");
        txtLoaiKH.setText("Bình thường");
        txtcccd.setText((""));
    }

    protected void initUniqueComponents() {
        JLabel lblSanPhamLink = new JLabel("<html><u>Khách Hàng</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(20, 20, 400, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm khách hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(140, 20, 400, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("khachhang");
            }
        });

        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(20, 80, 150, 30);
        add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(200, 80, 200, 30);
        add(txtMaKH);
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(20, 130, 150, 30);
        add(lblHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(200, 130, 200, 30);
        add(txtHoTen);
        JLabel lblsdt = new JLabel("Số diện thoại:");
        lblsdt.setBounds(20, 180, 150, 30);
        add(lblsdt);
        txtsdt = new JTextField();
        txtsdt.setBounds(200, 180, 200, 30);
        add(txtsdt);
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(20, 230, 150, 30);
        add(lblCCCD);
        txtcccd = new JTextField();
        txtcccd.setBounds(200, 230, 200, 30);
        add(txtcccd);

        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(20, 280, 150, 30);
        add(lblDiemTL);
        txtDiemTL = new JTextField("0.0");
        txtDiemTL.setBounds(200, 280, 200, 30);
        txtDiemTL.setEditable(false);
        txtDiemTL.setFocusable(false);
        txtDiemTL.setBackground(new Color(230, 230, 230));
        add(txtDiemTL);

        JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
        lblLoaiKH.setBounds(20,330, 150, 30);
        add(lblLoaiKH);
        txtLoaiKH = new JTextField("Bình thường");
        txtLoaiKH.setBounds(200, 330, 200, 30);
        txtLoaiKH.setEditable(false);
        txtLoaiKH.setFocusable(false);
        txtLoaiKH.setBackground(new Color(230, 230, 230));
        add(txtLoaiKH);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(450, 480, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm khách hàng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtHoTen.getText().trim();
                String SDT = txtsdt.getText().trim();
                String CCCD = txtcccd.getText().trim();

                if (maKH.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khách hàng!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenKH.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên khách hàng!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (SDT.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                KhachHangDTO kh = new KhachHangDTO(maKH, tenKH, SDT, CCCD);
                try {
                    boolean result = khBLL.insertKhachHang(kh);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
                        mainFrame.getPage("khachhang", KhachHang.class).refreshData();
                        mainFrame.showPage("khachhang");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại! Kiểm tra dữ liệu.", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "saveAction");

        getActionMap().put("saveAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLuu.doClick();
            }
        });

        JTextField[] textFields = { txtMaKH, txtHoTen, txtsdt };

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
