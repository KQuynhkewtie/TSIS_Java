package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BLL.NhaCungUngBLL;
import DTO.currentuser;


public class TTCT_NCU extends BasePanel {
    private JTextArea txtTenNCU, txtMaNCU, txtMSTHUE, txtDC, txtSDT, txtEMAIL,txtTT;

    public TTCT_NCU(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhà cung ứng");
    }

    protected void initUniqueComponents() {
        JLabel lblKhachhangLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblKhachhangLink.setBounds(20, 20, 160, 30);
        add(lblKhachhangLink);

        lblKhachhangLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhacungung");
            }
        });



        JLabel lblArrow = new JLabel(" >> Thông tin nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(160, 20, 300, 30);
        add(lblArrow);


        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(20, 80, 150, 25);
        add(lblMaNCU);
        txtMaNCU = new JTextArea();
        txtMaNCU.setBounds(20, 110, 300, 30);
        txtMaNCU.setLineWrap(true);
        txtMaNCU.setWrapStyleWord(true);
        txtMaNCU.setEditable(false);
        txtMaNCU.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaNCU.setForeground(Color.decode("#641A1F"));
        txtMaNCU.setOpaque(false);
        add(txtMaNCU);

        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(20, 160, 150, 25);
        add(lblTen);
        txtTenNCU = new JTextArea();
        txtTenNCU.setBounds(20, 190, 300, 30);
        txtTenNCU.setLineWrap(true);
        txtTenNCU.setWrapStyleWord(true);
        txtTenNCU.setEditable(false);
        txtTenNCU.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenNCU.setForeground(Color.decode("#641A1F"));
        txtTenNCU.setOpaque(false);
        add(txtTenNCU);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(20, 240, 150, 25);
        add(lblMST);
        txtMSTHUE = new JTextArea();
        txtMSTHUE.setBounds(20, 270, 300, 30);
        txtMSTHUE.setLineWrap(true);
        txtMSTHUE.setWrapStyleWord(true);
        txtMSTHUE.setEditable(false);
        txtMSTHUE.setFont(new Font("Arial", Font.BOLD, 20));
        txtMSTHUE.setForeground(Color.decode("#641A1F"));
        txtMSTHUE.setOpaque(false);
        add(txtMSTHUE);


        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(20, 320, 150, 25);
        add(lblDC);
        txtDC = new JTextArea();
        txtDC.setBounds(20, 350, 300, 30);
        txtDC.setLineWrap(true);
        txtDC.setWrapStyleWord(true);
        txtDC.setEditable(false);
        txtDC.setFont(new Font("Arial", Font.BOLD, 20));
        txtDC.setForeground(Color.decode("#641A1F"));
        txtDC.setOpaque(false);
        add(txtDC);


        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(460, 80, 150, 25);
        add(lblSDT);
        txtSDT = new JTextArea();
        txtSDT.setBounds(460, 110, 300, 30);
        txtSDT.setLineWrap(true);
        txtSDT.setWrapStyleWord(true);
        txtSDT.setEditable(false);
        txtSDT.setFont(new Font("Arial", Font.BOLD, 20));
        txtSDT.setForeground(Color.decode("#641A1F"));
        txtSDT.setOpaque(false);
        add(txtSDT);


        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(460, 160, 150, 25);
        add(lblEmail);
        txtEMAIL = new JTextArea();
        txtEMAIL.setBounds(460, 190, 300, 30);
        txtEMAIL.setLineWrap(true);
        txtEMAIL.setWrapStyleWord(true);
        txtEMAIL.setEditable(false);
        txtEMAIL.setFont(new Font("Arial", Font.BOLD, 20));
        txtEMAIL.setForeground(Color.decode("#641A1F"));
        txtEMAIL.setOpaque(false);
        add(txtEMAIL);

        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(460, 240, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(460, 270, 300, 30);
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
        setVisible(true);

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Xóa nhà cung ứng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà cung ứng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    NhaCungUngBLL ncuBLL = new NhaCungUngBLL();
                    boolean xoaThanhCong = ncuBLL.deleteNCUById(txtMaNCU.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa nhà cung ứng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("nhacungung", NhaCungUng.class).refreshData();
                        mainFrame.showPage("nhacungung");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Nhà cung ứng có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                String maNCU = txtMaNCU.getText();
                CapNhatTT_NCU capNhatPage = mainFrame.getPage("capnhatncu", CapNhatTT_NCU.class);
                capNhatPage.loadncuInfoForUpdate(maNCU);
                mainFrame.showPage("capnhatncu");
            }
        });


        setVisible(true);
    }

    public void setThongTin(String maNCU, String tenNCU, String MST, String DC, String SDT,
                            String EMAIL, String trangthai) {
        txtMaNCU.setText(maNCU);
        txtTenNCU.setText(tenNCU);
        txtMSTHUE.setText(MST);
        txtDC.setText(DC);
        txtSDT.setText(SDT);
        txtEMAIL.setText(EMAIL);
        txtTT.setText(trangthai);

        revalidate();
        repaint();
    }
} 
