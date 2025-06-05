package GUI;

import javax.swing.*;
import BLL.KhachHangBLL;
import DTO.KhachHangDTO;
import DTO.currentuser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapNhatTT_KH extends BasePanel {
    private KhachHangBLL bllKhachhang = new KhachHangBLL();
    private JTextField txtHoTen, txtMaKH, txtDiemTL, txtsdt, txtLoaiKH;

    public CapNhatTT_KH(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Khách hàng");
    }

    protected void initUniqueComponents() {

        JLabel lblKhachhangLink = new JLabel("<html><u>Khách Hàng</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(20, 20, 120, 30);
        add(lblKhachhangLink);

        JLabel lblTTKhachhangLink = new JLabel("<html>>> <u>Thông tin khách hàng</u></html>");
        lblTTKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTKhachhangLink.setBounds(140, 20, 240, 30);
        add(lblTTKhachhangLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin khách hàng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(380, 20, 900, 30);
        add(lblArrow);

        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("khachhang");
            }
        });

        lblTTKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maKH = txtMaKH.getText().trim();
                System.out.println(maKH);
                KhachHangDTO kh = bllKhachhang.getKhachHangById(maKH);
                if (kh != null) {
                    TTCT_KH ttctPage = mainFrame.getPage("ttctkh", TTCT_KH.class);
                    ttctPage.setThongTin(maKH, txtHoTen.getText(),
                            Double.parseDouble(txtDiemTL.getText()),
                            txtLoaiKH.getText(), txtsdt.getText());
                    mainFrame.showPage("ttctkh");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!");
                }
            }
        });

        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setBounds(20, 80, 150, 30);
        add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setBounds(200, 80, 200, 30);
        txtMaKH.setBackground(new Color(230, 230, 230));
        txtMaKH.setEditable(false);
        txtMaKH.setFocusable(false);
        add(txtMaKH);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(20, 130, 150, 30);
        add(lblHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(200, 130, 200, 30);
        add(txtHoTen);

        JLabel lblsdt = new JLabel("Số điện thoại:");
        lblsdt.setBounds(20, 180, 150, 30);
        add(lblsdt);
        txtsdt = new JTextField();
        txtsdt.setBounds(200, 180, 200, 30);
        add(txtsdt);

        JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
        lblDiemTL.setBounds(20, 230, 150, 30);
        add(lblDiemTL);
        txtDiemTL = new JTextField();
        txtDiemTL.setBounds(200, 230, 200, 30);
        add(txtDiemTL);
        txtDiemTL.setEditable(false);
        txtDiemTL.setFocusable(false);
        txtDiemTL.setBackground(new Color(230, 230, 230));

        JLabel lblLoaiKH = new JLabel("Mã loại khách hàng:");
        lblLoaiKH.setBounds(20, 280, 150, 30);
        add(lblLoaiKH);
        txtLoaiKH = new JTextField();
        txtLoaiKH.setBounds(200, 280, 200, 30);
        add(txtLoaiKH);
        txtLoaiKH.setEditable(false);
        txtLoaiKH.setFocusable(false);
        txtLoaiKH.setBackground(new Color(230, 230, 230));

        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(450, 480, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa khách hàng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String maKH = txtMaKH.getText().trim();
                String tenKH = txtHoTen.getText().trim();
                String DTLstr = txtDiemTL.getText().trim();
                String maloaikh = txtLoaiKH.getText().trim();
                String sdt = txtsdt.getText().trim();

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

                if (sdt.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double DTL = Double.parseDouble(DTLstr);

                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMaKH(maKH);
                    kh.setHoTen(tenKH);
                    kh.setDiemTichLuy(DTL);
                    kh.setLoaiKhach(maloaikh);
                    kh.setSdt(sdt);

                    boolean success = bllKhachhang.updateKhachHang(kh);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");

                        TTCT_KH ttctPage = mainFrame.getPage("ttctkh", TTCT_KH.class);
                        ttctPage.setThongTin(kh.getMaKH(), kh.getHoTen(),
                                kh.getDiemTichLuy(), kh.getLoaiKhach(), kh.getSdt());
                        mainFrame.showPage("ttctkh");
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

    public void loadCustomerInfoForUpdate(String maKH) {
        KhachHangDTO kh = bllKhachhang.getKhachHangById(maKH);

        if (kh != null) {
            txtHoTen.setText(kh.getHoTen());
            txtMaKH.setText(kh.getMaKH());
            txtDiemTL.setText(String.valueOf(kh.getDiemTichLuy()));
            txtLoaiKH.setText(String.valueOf(kh.getLoaiKhach()));
            txtsdt.setText(kh.getSdt());

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
        }
    }
}
