package GUI;

import javax.swing.*;

import BLL.NhanVienBLL;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import BLL.VaiTroBLL;

public class TTCT_NV extends BasePanel {
    private VaiTroBLL bllvt = new VaiTroBLL();
    private JTextArea txtTenNV, txtMaNV, txtCCCD, txtSDT, txtvitri, txtMST, txtTT;

    public TTCT_NV(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhân viên");
    }

    protected void initUniqueComponents() {

        JLabel lblKhachhangLink = new JLabel("<html><u>Nhân viên</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(20, 20, 100, 30);
        add(lblKhachhangLink);

        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhanvien");
            }
        });
        JLabel lblArrow = new JLabel(" >> Thông tin nhân viên");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(120, 20, 300, 30);
        add(lblArrow);


        // form thêm nhân viên
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, 80, 150, 25);
        add(lblMaNV);
        txtMaNV = new JTextArea();
        txtMaNV.setBounds(20, 110, 300, 30);
        txtMaNV.setLineWrap(true);
        txtMaNV.setWrapStyleWord(true);
        txtMaNV.setEditable(false);
        txtMaNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNV.setForeground(Color.decode("#641A1F"));
        txtMaNV.setOpaque(false);
        add(txtMaNV);
        // họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(20, 160, 150, 25);
        add(lblHoTen);
        txtTenNV = new JTextArea();
        txtTenNV.setBounds(20, 190, 300, 30);
        txtTenNV.setLineWrap(true);
        txtTenNV.setWrapStyleWord(true);
        txtTenNV.setEditable(false);
        txtTenNV.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenNV.setForeground(Color.decode("#641A1F"));
        txtTenNV.setOpaque(false);
        add(txtTenNV);

        // số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(20, 240, 150, 25);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(20, 270, 300, 30);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false);
        add(txtSDT);

        // cccd
        JLabel lblCCCD = new JLabel("CCCD:");
        lblCCCD.setBounds(20, 320, 150, 25);
        add(lblCCCD);
        txtCCCD = new JTextArea();
        txtCCCD.setBounds(20, 350, 400, 40);
        txtCCCD.setLineWrap(true);
        txtCCCD.setWrapStyleWord(true);
        txtCCCD.setEditable(false);
        txtCCCD.setFont(new Font("Arial", Font.BOLD, 20));
        txtCCCD.setForeground(Color.decode("#641A1F"));
        txtCCCD.setOpaque(false);
        add(txtCCCD);


        JLabel lblVTCV = new JLabel("Vị trí công việc:");
        lblVTCV.setBounds(460, 80, 150, 25);
        add(lblVTCV);
        txtvitri = new JTextArea();
        txtvitri.setBounds(460, 110, 300, 30);
        txtvitri.setLineWrap(true);
        txtvitri.setWrapStyleWord(true);
        txtvitri.setEditable(false);
        txtvitri.setFont(new Font("Arial", Font.BOLD, 20));
        txtvitri.setForeground(Color.decode("#641A1F"));
        txtvitri.setOpaque(false);
        add(txtvitri);

        // mã số thuế
        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(460, 160, 150, 25);
        add(lblMST);
        txtMST = new JTextArea();
        txtMST.setBounds(460, 190, 400, 40);
        txtMST.setLineWrap(true);
        txtMST.setWrapStyleWord(true);
        txtMST.setEditable(false);
        txtMST.setFont(new Font("Arial", Font.BOLD, 20));
        txtMST.setForeground(Color.decode("#641A1F"));
        txtMST.setOpaque(false);
        add(txtMST);
        //Trạng thái
        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(460, 240, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(460, 270, 300, 40);
        txtTT.setLineWrap(true);
        txtTT.setWrapStyleWord(true);
        txtTT.setEditable(false);
        txtTT.setFont(new Font("Arial", Font.BOLD, 20));
        txtTT.setForeground(Color.decode("#641A1F"));
        txtTT.setOpaque(false);
        add(txtTT);
        // nút xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setBounds(460, 490, 100, 40);
        btnXoa.setBackground(Color.decode("#F0483E"));
        btnXoa.setForeground(Color.WHITE);
        add(btnXoa);


        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa nhân viên")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    NhanVienBLL nvBLL = new NhanVienBLL();
                    boolean xoaThanhCong = nvBLL.deleteNhanVien(txtMaNV.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("nhanvien", NhanVien.class).refreshData();
                        mainFrame.showPage("nhanvien");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Nhân viên có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                String maNV = txtMaNV.getText();
                CapNhatTT_NV capNhatPage = mainFrame.getPage("capnhatnv", CapNhatTT_NV.class);
                capNhatPage.loadnvInfoForUpdate(maNV);
                mainFrame.showPage("capnhatnv");
            }
        });


        setVisible(true);

    }
    public void setThongTin(String maNV, String tenNV, String CCCD, String SDT, String vitri,
                            String mst, String trangthai) {
        txtMaNV.setText(maNV);
        txtTenNV.setText(tenNV);
        txtCCCD.setText(CCCD);
        txtSDT.setText(SDT);
        String tenVitri = bllvt.getTenVaiTro(vitri);
        txtvitri.setText(tenVitri);
        txtMST.setText(mst);
        txtTT.setText(trangthai);

        revalidate();
        repaint();
    }
}
