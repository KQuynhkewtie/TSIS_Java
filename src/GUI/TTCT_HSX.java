package GUI;


import javax.swing.*;

import BLL.HangSanXuatBLL;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TTCT_HSX extends BasePanel {

    private JTextArea txtTenHSX, txtMaHSX, txtMST, txtSDT, txtDC,txtTT;

    public TTCT_HSX(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Hãng sản xuất");
    }
    protected void initUniqueComponents() {


        JLabel lblHSXLink = new JLabel("<html><u>Hãng sản xuất</u></html>");
        lblHSXLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblHSXLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHSXLink.setBounds(20, 20, 160, 30);
        add(lblHSXLink);

        JLabel lblArrow = new JLabel(" >> Thông tin hãng sản xuất");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(160, 20, 300, 30);
        add(lblArrow);

        lblHSXLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("hangsx");
            }
        });



        JLabel lblMaHSX = new JLabel("Mã hãng sản xuất:");
        lblMaHSX.setBounds(20, 80, 150, 25);
        add(lblMaHSX);
        txtMaHSX = new JTextArea();
        txtMaHSX.setBounds(20, 110, 300, 30);
        txtMaHSX.setLineWrap(true);
        txtMaHSX.setWrapStyleWord(true);
        txtMaHSX.setEditable(false);
        txtMaHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaHSX.setForeground(Color.decode("#641A1F"));
        txtMaHSX.setOpaque(false);
        add(txtMaHSX);

        JLabel lblTenHSX = new JLabel("Tên hãng sản xuất:");
        lblTenHSX.setBounds(460, 80, 150, 25);
        add(lblTenHSX);
        txtTenHSX = new JTextArea();
        txtTenHSX.setBounds(460, 110, 300, 30);
        txtTenHSX.setLineWrap(true);
        txtTenHSX.setWrapStyleWord(true);
        txtTenHSX.setEditable(false);
        txtTenHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenHSX.setForeground(Color.decode("#641A1F"));
        txtTenHSX.setOpaque(false);
        add(txtTenHSX);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(460, 160, 150, 25);
        add(lblMST);
        txtMST = new JTextArea();
        txtMST.setBounds(460, 190, 300, 30);
        txtMST.setLineWrap(true);
        txtMST.setWrapStyleWord(true);
        txtMST.setEditable(false);
        txtMST.setFont(new Font("Arial", Font.BOLD, 20));
        txtMST.setForeground(Color.decode("#641A1F"));
        txtMST.setOpaque(false);
        add(txtMST);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(20, 160, 150, 25);
        add(lblDC);
        txtDC = new JTextArea();
        txtDC.setBounds(20, 190, 300, 30);
        txtDC.setLineWrap(true);
        txtDC.setWrapStyleWord(true);
        txtDC.setEditable(false);
        txtDC.setFont(new Font("Arial", Font.BOLD, 20));
        txtDC.setForeground(Color.decode("#641A1F"));
        txtDC.setOpaque(false);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(460, 240, 150, 25);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(460, 270, 300, 30);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false);
        add(txtSDT);

        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(20, 240, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(20, 270, 300, 30);
        txtTT.setLineWrap(true);
        txtTT.setWrapStyleWord(true);
        txtTT.setEditable(false);
        txtTT.setFont(new Font("Arial", Font.BOLD, 20));
        txtTT.setForeground(Color.decode("#641A1F"));
        txtTT.setOpaque(false);
        add(txtTT);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(460, 490, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa hãng sản xuất")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa hãng sản xuất này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
                    boolean xoaThanhCong = hsxBLL.deleteHangSanXuat(txtMaHSX.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa hãng sản xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("hangsx", HangSanXuat.class).refreshData();
                        mainFrame.showPage("hangsx");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Hãng sản xuất có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(660, 490, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maHSX = txtMaHSX.getText();
                CapNhatTT_HSX capNhatForm = mainFrame.getPage("capnhathsx", CapNhatTT_HSX.class);
                capNhatForm.loadhsxInfoForUpdate(maHSX);
                mainFrame.showPage("capnhathsx");
            }
        });

        setVisible(true);
    }
    public void setThongTin(String maHSX, String tenHSX, String MST,String DC, String SDT, String trangthai ) {
        txtMaHSX.setText(maHSX);
        txtTenHSX.setText(tenHSX);
        txtMST.setText(MST);
        txtDC.setText(DC);
        txtSDT.setText(SDT);
        txtTT.setText(trangthai);
        revalidate();
        repaint();
    }
}

