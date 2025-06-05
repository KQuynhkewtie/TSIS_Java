package GUI;

import javax.swing.*;


import DTO.HangSanXuatDTO;
import DTO.currentuser;
import BLL.HangSanXuatBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Them_HSX extends BasePanel {
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    public Them_HSX(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hãng sản xuất");
    }
    protected void initUniqueComponents() {


        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(20, 20, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(160, 20, 300, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("hangsx");

            }
        });

        JLabel lblMaSoThue = new JLabel("Mã số thuế:");
        lblMaSoThue.setBounds(460, 160, 150, 25);
        add(lblMaSoThue);
        JTextField txtMaSoThue = new JTextField();
        txtMaSoThue.setBounds(460, 190, 300, 30);
        add(txtMaSoThue);

        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(20, 80, 150, 25);
        add(lblMaHSX);
        JTextField txtMaHSX = new JTextField();
        txtMaHSX.setBounds(20, 110, 300, 30);
        add(txtMaHSX);

        JLabel lbldiachi = new JLabel("Địa chỉ:");
        lbldiachi.setBounds(20, 160, 150, 25);
        add(lbldiachi);
        JTextField txtdiachi = new JTextField();
        txtdiachi.setBounds(20, 190, 300, 30);
        add(txtdiachi);

        JLabel lbltenHSX = new JLabel("Tên hãng sản xuất:");
        lbltenHSX.setBounds(460, 80, 150, 25);
        add(lbltenHSX);
        JTextField txttenHSX = new JTextField();
        txttenHSX.setBounds(460, 110, 300, 30);
        add(txttenHSX);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(460, 240, 150, 25);
        add(lblsdt);
        JTextField txtsdt = new JTextField();
        txtsdt.setBounds(460, 270, 300, 30);
        add(txtsdt);


        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(460, 490, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maHSX = txtMaHSX.getText().trim();
                String tenHSX = txttenHSX.getText().trim();
                String MST = txtMaSoThue.getText().trim();
                String DC = txtdiachi.getText().trim();
                String SDT = txtsdt.getText().trim();
                if (maHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                try {
                    HangSanXuatDTO hsx = new HangSanXuatDTO(maHSX, tenHSX, MST, DC, SDT);

                    boolean result = hsxBLL.insertHangSanXuat(hsx);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm hãng sản xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("hangsx", HangSanXuat.class).refreshData();
                        mainFrame.showPage("hangsx");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm hãng sản xuất thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultButtonSafe(btnLuu);

        JTextField[] textFields = {txtMaHSX, txttenHSX, txtMaSoThue, txtdiachi, txtsdt};

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
