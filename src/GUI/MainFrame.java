package GUI;
import DAL.VaiTroDAL;
import DTO.currentuser;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final JPanel sidebar;
    private final Map<String, BasePanel> pages = new HashMap<>();
    private List<JButton> menuButtons;

    public MainFrame() {
        setTitle("Quản lý nhà thuốc");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setPreferredSize(new Dimension(250, 700));

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setBackground(Color.decode("#AB282C"));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        leftContainer.add(sidebar, BorderLayout.CENTER);

        JPanel mainTopBar = new JPanel();
        mainTopBar.setPreferredSize(new Dimension(850, 40));
        mainTopBar.setBackground(Color.decode("#AB282C"));
        mainTopBar.setLayout(new BorderLayout());

        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.add(mainTopBar, BorderLayout.NORTH);
        rightContainer.add(contentPanel, BorderLayout.CENTER);

        add(leftContainer, BorderLayout.WEST);
        add(rightContainer, BorderLayout.CENTER);

        menuButtons = new ArrayList<>();

        addLogoToSidebar();
        addUserInfoToSidebar();
        addMenuButtonsToSidebar();
        setupSidebarNavigation();

        initPages();

        showPage("home");
        setVisible(true);
    }

    private void addLogoToSidebar() {
        JLabel logo = new JLabel("Pharmacy", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(15));
    }

    private void addUserInfoToSidebar() {
       
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout(10, 0)); 
        userPanel.setOpaque(false);
        userPanel.setMaximumSize(new Dimension(250, 60));
        userPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10)); 

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/staff.png"));
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(newImage);

        JLabel iconLabel = new JLabel(resizedIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); 

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(currentuser.getUsername() != null ? currentuser.getUsername() : "Chưa đăng nhập");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        VaiTroDAL vaitroDAL = new VaiTroDAL();
        String tenVT = vaitroDAL.getTenVaiTro(currentuser.getMaVaiTro());
        JLabel roleLabel = new JLabel(tenVT != null ? tenVT : "Không rõ vai trò");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(nameLabel);
        textPanel.add(roleLabel);

        userPanel.add(iconLabel, BorderLayout.WEST);
        userPanel.add(textPanel, BorderLayout.CENTER);

        sidebar.add(userPanel);
    }

    private void addMenuButtonsToSidebar() {
        String[][] menuItems = {
                { "Trang chủ", "image/homepage.png" },
                { "Sản phẩm", "image/sanpham.png" },
                { "Loại sản phẩm", "image/loaisp.png" },
                { "Nhân viên", "image/nhanvien.png" },
                { "Khách hàng", "image/khachhang.png" },
                { "Phiếu nhập hàng", "image/pnh.png" },
                { "Hóa đơn", "image/hoadon.png" },
                { "Thống kê", "image/thongke.png" },
                { "Hãng sản xuất", "image/hsx.png" },
                { "Nhà cung ứng", "image/ncu.png" },
                { "Đăng xuất", "image/dangxuat.png" }
        };

        for (String[] item : menuItems) {
            JButton btn = createMenuButton(item[0], item[1]);
            sidebar.add(btn);
            menuButtons.add(btn);
        }
    }

    private JButton createMenuButton(String text, String iconPath) {
       
        JButton btn = new JButton(text);

        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image image = icon.getImage().getScaledInstance(22, 26, Image.SCALE_SMOOTH); 
            btn.setIcon(new ImageIcon(image));
            btn.setHorizontalAlignment(SwingConstants.LEFT); 
            btn.setIconTextGap(15); 
        }

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 49));
        btn.setBackground(Color.decode("#641A1F"));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setMargin(new Insets(0, 40, 0, 0)); 
        btn.setOpaque(true);

        return btn;
    }

    private void setupSidebarNavigation() {
        for (JButton btn : menuButtons) {
            String btnText = btn.getText();

            btn.addActionListener(e -> {
                switch (btnText) {
                    case "Trang chủ":
                        showPage("home");
                        break;
                    case "Sản phẩm":
                        showPage("sanpham");
                        break;
                    case "Loại sản phẩm":
                        showPage("loaisp");
                        break;
                    case "Nhân viên":
                        showPage("nhanvien");
                        break;
                    case "Khách hàng":
                        showPage("khachhang");
                        break;
                    case "Phiếu nhập hàng":
                        showPage("phieunhaphang");
                        break;
                    case "Hóa đơn":
                        showPage("hoadon");
                        break;
                    case "Thống kê":
                        showPage("thongke");
                        break;
                    case "Hãng sản xuất":
                        showPage("hangsx");
                        break;
                    case "Nhà cung ứng":
                        showPage("nhacungung");
                        break;
                    case "Đăng xuất":
                        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất?",
                                "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            currentuser.clear();
                            this.setVisible(false);
                            SignIn loginFrame = new SignIn();
                            loginFrame.setVisible(true);
                            break;
                        }
                    default:
                        System.out.println("Chưa xử lý: " + btnText);
                }
            });
        }
    }

    private void initPages() {

        addPage("home", new HomePage(this));
        addPage("khachhang", new KhachHang(this));
        addPage("themkh", new Them_KH(this));
        addPage("ttctkh", new TTCT_KH(this));
        addPage("capnhatkh", new CapNhatTT_KH(this));
        addPage("nhanvien", new NhanVien(this));
        addPage("themnv", new Them_NV(this));
        addPage("ttctnv", new TTCT_NV(this));
        addPage("capnhatnv", new CapNhatTT_NV(this));
        addPage("sanpham", new SanPham(this));
        addPage("themsp", new Them_SP(this));
        addPage("ttctsp", new TTCT_SP(this));
        addPage("capnhatsp", new CapNhatTT_SP(this));
        addPage("loaisp", new LoaiSP(this));
        addPage("themloaisp", new Them_LSP(this));
        addPage("ttctloaisp", new TTCT_LSP(this));
        addPage("capnhatloaisp", new CapNhatTT_LSP(this));
        addPage("hangsx", new HangSanXuat(this));
        addPage("themhsx", new Them_HSX(this));
        addPage("ttcthsx", new TTCT_HSX(this));
        addPage("capnhathsx", new CapNhatTT_HSX(this));
        addPage("nhacungung", new NhaCungUng(this));
        addPage("themncu", new Them_NCU(this));
        addPage("ttctncu", new TTCT_NCU(this));
        addPage("capnhatncu", new CapNhatTT_NCU(this));
        addPage("phieunhaphang", new PhieuNhapHang(this));
        addPage("thempnh", new Them_PNH(this));
        addPage("ttctpnh", new TTCT_PNH(this));
        addPage("capnhatpnh", new CapNhatTT_PNH(this));
        addPage("hoadon", new HoaDon(this));
        addPage("themhd", new Them_HD(this));
        addPage("ttcthd", new TTCT_HD(this));
        addPage("capnhathd", new CapNhatTT_HD(this));
        addPage("thongke", new ThongKe(this));
    }

    private void addPage(String name, BasePanel page) {
        pages.put(name, page);
        contentPanel.add(page, name);
    }

    public void showPage(String pageName) {
        if (!pages.containsKey(pageName)) {
            throw new IllegalArgumentException("Trang không tồn tại: " + pageName);
        }

        cardLayout.show(contentPanel, pageName);
        BasePanel currentPage = pages.get(pageName);
        currentPage.onPageShown(); 
    }

    public <T extends BasePanel> T getPage(String name, Class<T> type) {
        BasePanel page = pages.get(name);
        if (page == null) {
            throw new IllegalArgumentException("Trang không tồn tại: " + name);
        }
        return type.cast(page);
    }

    public void highlightMenuButton(String menuText) {
        for (JButton btn : menuButtons) {
            if (btn.getText().equals(menuText)) {
                btn.setBackground(Color.decode("#EF5D7A"));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                btn.setBackground(Color.decode("#641A1F"));
                btn.setFont(new Font("Arial", Font.BOLD, 12));
            }
        }
    }

}
