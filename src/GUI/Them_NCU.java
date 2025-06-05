package GUI;

import javax.swing.*;
import DTO.NhaCungUngDTO;
import DTO.currentuser;
import BLL.NhaCungUngBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Them_NCU extends BasePanel {
    JTextField txtMaNCU, txtTen,txtMST,txtDC,txtSDT,txtEmail;
    private NhaCungUngBLL ncuBLL= new NhaCungUngBLL();
    public Them_NCU(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Nhà cung ứng");
        txtMaNCU.setText("");
        txtTen.setText("");
        txtMST.setText("");
        txtDC.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    protected void initUniqueComponents() {
        JLabel lblncuLink = new JLabel("<html><u>Nhà cung ứng</u></html>");
        lblncuLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblncuLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblncuLink.setBounds(20, 20, 160, 30);
        add(lblncuLink);

        JLabel lblArrow = new JLabel(" >> Thêm nhà cung ứng");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(160, 20, 300, 30);
        add(lblArrow);

        lblncuLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("nhacungung");
            }
        });


        JLabel lblMaNCU = new JLabel("Mã nhà cung ứng:");
        lblMaNCU.setBounds(20, 80, 150, 25);
        add(lblMaNCU);
        txtMaNCU = new JTextField();
        txtMaNCU.setBounds(20, 110, 300, 30);
        add(txtMaNCU);

        JLabel lblTen = new JLabel("Tên nhà cung ứng:");
        lblTen.setBounds(20, 160, 150, 25);
        add(lblTen);
        txtTen = new JTextField();
        txtTen.setBounds(20, 190, 300, 30);
        add(txtTen);

        JLabel lblMST = new JLabel("Mã số thuế:");
        lblMST.setBounds(20, 240, 150, 25);
        add(lblMST);
        txtMST = new JTextField();
        txtMST.setBounds(20, 270, 300, 30);
        add(txtMST);

        JLabel lblDC = new JLabel("Địa chỉ:");
        lblDC.setBounds(20, 320, 150, 25);
        add(lblDC);
        txtDC = new JTextField();
        txtDC.setBounds(20, 350, 300, 30);
        add(txtDC);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(460, 80, 150, 25);
        add(lblSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(460, 110, 300, 30);
        add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(460, 160, 150, 25);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(460, 190, 300, 30);
        add(txtEmail);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(460, 490, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm nhà cung ứng")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String maNCU = txtMaNCU.getText().trim();
                String tenNCU = txtTen.getText().trim();
                String MST = txtMST.getText().trim();
                String DC = txtDC.getText().trim();
                String SDT = txtSDT.getText().trim();
                String Email = txtEmail.getText().trim();
                if (maNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenNCU.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên nhà cung ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                if (Email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    NhaCungUngDTO ncu= new NhaCungUngDTO(maNCU, tenNCU, MST, DC, SDT, Email);
                    boolean result = ncuBLL.insertNCU(ncu);
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thành công!", "Lỗi", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("nhacungung", NhaCungUng.class).refreshData();
                        mainFrame.showPage("nhacungung");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm nhà cung ứng thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung ứng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultButtonSafe(btnLuu);

        JTextField[] textFields = {txtMaNCU, txtTen, txtMST, txtSDT, txtEmail,txtDC};

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
