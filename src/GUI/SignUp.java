package GUI;
import javax.swing.*;
import BLL.TaiKhoanBLL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class SignUp extends JFrame {
    private JTextField usernameField, manvField, emailField;
    private JPasswordField passwordField, password2Field;

    public SignUp() {
        setTitle("Đăng ký tài khoản");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);


        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1100, 100);
        headerPanel.setBackground(Color.decode("#AB282C"));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel logoLabel = new JLabel("PHARMACY");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setBounds(40, 30, 200, 30);
        headerPanel.add(logoLabel);

        JButton loginButton = new JButton("ĐĂNG NHẬP");
        loginButton.setBounds(940, 25, 130, 50);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        headerPanel.add(loginButton);

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bg.jpg"));
        Image scaledImage = bgIcon.getImage().getScaledInstance(1100, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 1100, 700);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);


        JPanel formPanel = new JPanel();
        formPanel.setBounds(355, 180, 400, 450);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        add(formPanel);
        layeredPane.add(formPanel, JLayeredPane.POPUP_LAYER);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignIn();
            }
        });

        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(150, 10, 150, 50);

        JLabel lblmanv = new JLabel("Mã nhân viên");
        lblmanv.setFont(new Font("Arial", Font.BOLD, 15));
        lblmanv.setBounds(65, 50, 200, 30);
        formPanel.add(lblmanv);
        manvField = new JTextField();
        manvField.setBounds(65, 80, 270, 30);
        manvField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(manvField);

        JLabel lblusername = new JLabel("Tên đăng nhập");
        lblusername.setFont(new Font("Arial", Font.BOLD, 15));
        lblusername.setBounds(65, 120, 140, 30);
        formPanel.add(lblusername);

        usernameField = new JTextField();
        usernameField.setBounds(65, 150, 270, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameField);

        JLabel lblemail = new JLabel("Email");
        lblemail.setFont(new Font("Arial", Font.BOLD, 15));
        lblemail.setBounds(65, 180, 100, 30);
        formPanel.add(lblemail);

        emailField = new JTextField();
        emailField.setBounds(65, 210, 270, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(emailField);

        JLabel lblmatkhau = new JLabel("Mật khẩu");
        lblmatkhau.setFont(new Font("Arial", Font.BOLD, 15));
        lblmatkhau.setBounds(65, 240, 140, 30);
        formPanel.add(lblmatkhau);

        passwordField = new JPasswordField();
        passwordField.setBounds(65, 270, 270, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordField);

        JLabel lblmatkhau2 = new JLabel("Nhập lại mật khẩu");
        lblmatkhau2.setFont(new Font("Arial", Font.BOLD, 15));
        lblmatkhau2.setBounds(65, 310, 140, 30);
        formPanel.add(lblmatkhau2);

        password2Field = new JPasswordField();
        password2Field.setBounds(65, 340, 270, 30);
        password2Field.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(password2Field);

        JButton registerButton = new JButton("ĐĂNG KÝ");
        registerButton.setFont(new Font("Arial", Font.BOLD, 17));
        registerButton.setBounds(65, 400, 270, 40);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        formPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenTK = usernameField.getText().trim();
                String email = emailField.getText().trim();
                String matKhau = new String(passwordField.getPassword()).trim();
                String matKhau2 = new String(password2Field.getPassword()).trim();
                String manv = manvField.getText().trim();

                if (tenTK.isEmpty() || email.isEmpty() || matKhau.isEmpty() || matKhau2.isEmpty() || manv.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!matKhau.equals(matKhau2)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TaiKhoanBLL tkbll = new TaiKhoanBLL();
                int result = tkbll.signup(tenTK, email, matKhau, manv);

                switch (result) {
                    case 1:
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new SignIn();
                        break;
                    case -1:
                        JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        break;
                    case -2:
                        JOptionPane.showMessageDialog(null, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        break;
                    case -3:
                        JOptionPane.showMessageDialog(null, "Nhân viên này đã có tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra trong quá trình đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });


        JTextField[] registerFields = {manvField, usernameField, emailField, passwordField, password2Field};

        for (int i = 0; i < registerFields.length; i++) {
            final int index = i;
            registerFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        registerButton.doClick();
                    } else if (key == KeyEvent.VK_DOWN && index < registerFields.length - 1) {
                        registerFields[index + 1].requestFocus();
                    } else if (key == KeyEvent.VK_UP && index > 0) {
                        registerFields[index - 1].requestFocus();
                    } else if (key == KeyEvent.VK_ENTER) {
                        registerButton.doClick();
                    }
                }
            });
        }
        formPanel.add(titleLabel);
        formPanel.add(usernameField);
        formPanel.add(emailField);
        formPanel.add(passwordField);
        formPanel.add(registerButton);


        setVisible(true);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}
