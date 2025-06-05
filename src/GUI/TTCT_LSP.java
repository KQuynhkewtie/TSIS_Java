package GUI;


import BLL.LoaiSPBLL;
import DTO.currentuser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TTCT_LSP extends BasePanel {
    private JTextArea txtTenLSP, txtMaLSP,txtTT;


    public TTCT_LSP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }
    protected void initUniqueComponents() {
        highlightMenuButton("Loại sản phẩm");

        JLabel lblLoaiSanPhamLink = new JLabel("<html><u>Loại sản phẩm</u></html>");
        lblLoaiSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblLoaiSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLoaiSanPhamLink.setBounds(20, 30, 160, 30);
        add(lblLoaiSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thông tin loại sản phẩm");
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
        txtMaLSP = new JTextArea();
        txtMaLSP.setBounds(20, 110, 300, 80);
        txtMaLSP.setLineWrap(true);
        txtMaLSP.setWrapStyleWord(true);
        txtMaLSP.setEditable(false);
        txtMaLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaLSP.setForeground(Color.decode("#641A1F"));
        txtMaLSP.setOpaque(false);
        add(txtMaLSP);

        JLabel lblLoaiSP = new JLabel("Tên loại sản phẩm:");
        lblLoaiSP.setBounds(460, 80, 150, 25);
        add(lblLoaiSP);
        txtTenLSP = new JTextArea();
        txtTenLSP.setBounds(460, 110, 300, 30);
        txtTenLSP.setLineWrap(true);
        txtTenLSP.setWrapStyleWord(true);
        txtTenLSP.setEditable(false);
        txtTenLSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenLSP.setForeground(Color.decode("#641A1F"));
        txtTenLSP.setOpaque(false);
        add(txtTenLSP);

        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(20, 160, 150, 30);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(20, 190, 300, 30);
        txtTT.setLineWrap(true);
        txtTT.setWrapStyleWord(true);
        txtTT.setEditable(false);
        txtTT.setFont(new Font("Arial", Font.BOLD, 20));
        txtTT.setForeground(Color.decode("#641A1F"));
        txtTT.setOpaque(false);
        add(txtTT);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(460, 160, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa loại sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa loại sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    LoaiSPBLL lspBLL = new LoaiSPBLL();
                    boolean xoaThanhCong = lspBLL.deleteLSPById(txtMaLSP.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("loaisp", LoaiSP.class).refreshData();
                        mainFrame.showPage("loaisp");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Loại sản phẩm có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton btnCapnhat = new JButton("Cập nhật");
        btnCapnhat.setBounds(660, 160, 100, 40);
        btnCapnhat.setBackground(Color.decode("#F0483E"));
        btnCapnhat.setForeground(Color.WHITE);
        add(btnCapnhat);

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maLSP = txtMaLSP.getText();
                CapNhatTT_LSP capNhatForm = mainFrame.getPage("capnhatloaisp", CapNhatTT_LSP.class);
                capNhatForm.loadlspInfoForUpdate(maLSP);
                mainFrame.showPage("capnhatloaisp");
            }
        });


        setVisible(true);
    }
    public void setThongTin(String maLSP, String tenLSP,String trangthai) {
        txtMaLSP.setText(maLSP);
        txtTenLSP.setText(tenLSP);
        txtTT.setText(trangthai);

        revalidate();
        repaint();
    }
}

