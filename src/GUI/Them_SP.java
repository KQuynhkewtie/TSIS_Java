package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import DTO.HangSanXuatDTO;
import DTO.LoaiSPDTO;
import DTO.SanPhamDTO;
import DTO.currentuser;
import BLL.SanPhamBLL;
import BLL.HangSanXuatBLL;
import BLL.LoaiSPBLL;

import java.util.List;

public class Them_SP extends BasePanel {
    JTextField txtMaSP,txtTenSP, txtSoLuong,txtQcdg,txtSodk,txtGia;
    private SanPhamBLL spBLL = new SanPhamBLL();
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private LoaiSPBLL lspBLL = new LoaiSPBLL();
    private JComboBox<String> comboMaLSP, comboHSX;
    public Them_SP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Sản phẩm");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoLuong.setText("");
        txtQcdg.setText("");
        txtSodk.setText("");
        txtGia.setText("");
        comboMaLSP.setSelectedIndex(-1);
        comboHSX.setSelectedIndex(-1);
    }

    protected void initUniqueComponents() {
        JLabel lblSanPhamLink = new JLabel("<html><u>Sản phẩm</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(20, 20, 100, 30);
        add(lblSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Thêm sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(120, 20, 200, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("sanpham");
            }
        });

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(20, 80, 150, 25);
        add(lblMaSP);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(20, 110, 300, 30);
        add(txtMaSP);

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        lblTenSP.setBounds(460, 80, 150, 25);
        add(lblTenSP);
        txtTenSP = new JTextField();
        txtTenSP.setBounds(460, 110, 300, 30);
        add(txtTenSP);


        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");
        lblLoaiSP.setBounds(20, 160, 150, 25);
        add(lblLoaiSP);

        comboMaLSP = new JComboBox<>();
        comboMaLSP.setBounds(20, 190, 300, 30);
        add(comboMaLSP);
        loadLoaiSanPhamVaoComboBox();

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(460, 160, 150, 25);
        add(lblSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(460, 190, 300, 30);
        add(txtSoLuong);

        JLabel lblHSX = new JLabel("Hãng sản xuất:");
        lblHSX.setBounds(20, 240, 150, 25);
        add(lblHSX);
        comboHSX = new JComboBox<>();
        comboHSX.setBounds(20, 270, 300, 30);
        add(comboHSX);
        loadHSXVaoComboBox();

        JLabel lblqcdg = new JLabel("Quy cách đóng gói:");
        lblqcdg.setBounds(460, 240, 150, 25);
        add(lblqcdg);
        txtQcdg = new JTextField();
        txtQcdg.setBounds(460, 270, 300, 30);
        add(txtQcdg);

        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(460, 320, 150, 25);
        add(lblSodk);
        txtSodk = new JTextField();
        txtSodk.setBounds(460, 350, 300, 30);
        add(txtSodk);


        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(460, 410, 150, 25);
        add(lblGia);
        txtGia = new JTextField();
        txtGia.setBounds(460, 440, 300, 30);
        add(txtGia);

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setBounds(460, 490, 100, 40);
        btnLuu.setBackground(Color.decode("#F0483E"));
        btnLuu.setForeground(Color.WHITE);
        add(btnLuu);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Thêm sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maSP = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                String giaBanStr = txtGia.getText().trim();
                String quyCachDongGoi = txtQcdg.getText().trim();
                String soDangKy = txtSodk.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();

                if (maLSP.isEmpty() || maHSX.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn loại sản phẩm và hãng sản xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (maSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (tenSP.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (quyCachDongGoi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (soDangKy.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (soLuongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (giaBanStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập giá bán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double giaBan;
                int soluong;
                try {
                    giaBan = Double.parseDouble(giaBanStr);
                    if (giaBan < 0) {
                        JOptionPane.showMessageDialog(null, "Giá bán phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Giá bán phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    soluong = Integer.parseInt(soLuongStr);
                    if (soluong < 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    SanPhamDTO sp = new SanPhamDTO(maSP, maHSX, maLSP, tenSP, quyCachDongGoi,  soDangKy, soluong, giaBan);

                    System.out.println("Mã loại sản phẩm lấy từ giao diện: [" + maLSP + "]");
                    boolean result = spBLL.insertSanPham(sp);

                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("sanpham", SanPham.class).refreshData();
                        mainFrame.showPage("sanpham");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại! Kiểm tra dữ liệu." , "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultButtonSafe(btnLuu);

        JTextField[] textFields = {txtTenSP, txtMaSP, txtSoLuong, txtQcdg,  txtSodk, txtGia};

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
    private void loadLoaiSanPhamVaoComboBox() {
        List<LoaiSPDTO> loaiSPList = lspBLL.getAllLSP();
        comboMaLSP.removeAllItems();
        comboMaLSP.addItem("-- Chọn loại sản phẩm --");
        for (LoaiSPDTO lsp : loaiSPList) {
            comboMaLSP.addItem(lsp.getMaLSP() + " - " + lsp.getTenLSP());
        }
    }

    private void loadHSXVaoComboBox() {
        List<HangSanXuatDTO> hsxList = hsxBLL.getAllHangSanXuat();
        comboHSX.removeAllItems();
        comboHSX.addItem("-- Chọn hãng sản xuất --");
        for (HangSanXuatDTO hsx : hsxList) {
            comboHSX.addItem(hsx.getMaHSX() + " - " + hsx.getTenHSX());
        }
    }
}
