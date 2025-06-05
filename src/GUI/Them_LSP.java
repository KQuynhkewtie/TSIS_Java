package GUI;
import javax.swing.*;

import DTO.LoaiSPDTO;
import DTO.currentuser;
import BLL.LoaiSPBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Them_LSP extends BasePanel {
    JTextField txtMaLSP, txtTenLSP;
    private LoaiSPBLL lspBLL= new LoaiSPBLL();
    public Them_LSP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    @Override
    public void onPageShown() {
        highlightMenuButton("Loại sản phẩm");
        txtMaLSP.setText("");
        txtTenLSP.setText("");
    }
    protected void initUniqueComponents() {
        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(20, 30, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm loại sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(170, 30, 300, 30);
        add(lblArrow);

        lblLoaiSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("loaisp");

            }
        });

        JLabel lblMaSP = new JLabel("Mã loại sản phẩm:");
        lblMaSP.setBounds(20, 80, 150, 30);
        add(lblMaSP);
        txtMaLSP = new JTextField();
        txtMaLSP.setBounds(20, 110, 300, 30);

        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(460, 80, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextField();;
        txtTenLSP.setBounds(460, 110, 300, 30);
        add(txtTenLSP);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(460, 160, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maLSP = txtMaLSP.getText().trim();
                String tenLSP = txtTenLSP.getText().trim();

                if (maLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenLSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    LoaiSPDTO lsp = new LoaiSPDTO(maLSP, tenLSP);
                    boolean result = lspBLL.insertLSP(lsp);

                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("loaisp", LoaiSP.class).refreshData();
                        mainFrame.showPage("loaisp");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultButtonSafe(btnLuu);

        JTextField[] textFields = {txtTenLSP, txtMaLSP};

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

