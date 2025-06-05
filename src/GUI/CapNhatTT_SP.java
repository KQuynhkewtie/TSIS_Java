package GUI;

import javax.swing.*;

import BLL.SanPhamBLL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import DTO.*;

import java.util.List;
import DTO.SanPhamDTO;
import BLL.LoaiSPBLL;
import BLL.HangSanXuatBLL;
import DTO.LoaiSPDTO;
import DTO.HangSanXuatDTO;

public class CapNhatTT_SP extends BasePanel {
    private SanPhamBLL SanPhamBLL = new SanPhamBLL();
    private LoaiSPBLL lspBLL = new LoaiSPBLL();
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private JComboBox<String> comboMaLSP, comboHSX;
    private JTextField  txtTenSP, txtMaSP , txtQcdg, txtSodk,  txtGia, txtSoLuong;
    private JRadioButton rbDangban, rbNgungban;
    private ButtonGroup groupTrangThai;
    public CapNhatTT_SP(MainFrame mainFrame) {
        super(mainFrame);
        initUniqueComponents();
    }

    @Override
    public void onPageShown() {
        highlightMenuButton("Sản phẩm");
    }


    protected void initUniqueComponents() {
        JLabel lblSanPhamLink = new JLabel("<html><u>Sản phẩm</u></html>");
        lblSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSanPhamLink.setBounds(20, 20, 100, 30);
        add(lblSanPhamLink);

        JLabel lblTTSanPhamLink = new JLabel("<html>>><u> Thông tin sản phẩm</u></html>");
        lblTTSanPhamLink.setFont(new Font("Arial", Font.BOLD, 20));
        lblTTSanPhamLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblTTSanPhamLink.setBounds(120, 20, 300, 30);
        add(lblTTSanPhamLink);

        JLabel lblArrow = new JLabel(" >> Cập nhật thông tin sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(340, 20, 900, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("sanpham");
            }
        });

        lblTTSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maSP =  txtMaSP.getText().trim();
                SanPhamDTO sp = SanPhamBLL.getSanPhamById(maSP);
                if (sp != null) {
                    TTCT_SP ttctPage = mainFrame.getPage("ttctsp", TTCT_SP.class);
                    ttctPage.setThongTin( sp.getMaSP(),sp.getTenSP(), sp.getMaLSP(), sp.getsoluong(), sp.getMaHSX(), sp.getQuyCachDongGoi(), sp.getSoDangKy(),sp.getGiaBan(), sp.getTrangThai());
                    mainFrame.showPage("ttctsp");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm!");
                }
            }
        });


        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        lblMaSP.setBounds(20, 80, 150, 25);
        add(lblMaSP);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(20, 110, 300, 30);
        txtMaSP.setBackground(new Color(230, 230, 230));
        txtMaSP.setEditable(false);
        txtMaSP.setFocusable(false);
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
        //Trạng thái

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setBounds(20, 320, 150, 25);
        add(lblTrangThai);

        rbDangban = new JRadioButton("Đang bán");
        rbDangban.setBounds(20, 350, 150, 25);
        rbDangban.setSelected(true);
        add(rbDangban);

        rbNgungban = new JRadioButton("Ngưng bán");
        rbNgungban.setBounds(20, 380, 150, 25);
        add(rbNgungban);

        groupTrangThai = new ButtonGroup();
        groupTrangThai.add(rbDangban);
        groupTrangThai.add(rbNgungban);

        // Nút Lưu
        JButton btnLuusua = new JButton("Lưu");
        btnLuusua.setBounds(469, 490, 100, 40);
        btnLuusua.setBackground(Color.decode("#F0483E"));
        btnLuusua.setForeground(Color.WHITE);
        add(btnLuusua);
        btnLuusua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!currentuser.coQuyen("Sửa sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maSP = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String maLSP = comboMaLSP.getSelectedItem().toString().split(" - ")[0];
                String maHSX = comboHSX.getSelectedItem().toString().split(" - ")[0];
                String dongGoi = txtQcdg.getText().trim();
                String sodk = txtSodk.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();
                String giaStr = txtGia.getText().trim();


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
                if (dongGoi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (sodk.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (soLuongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập giá bán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double giaBan;
                int soluong;
                try {
                    giaBan = Double.parseDouble(giaStr);
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
                String trangthai = null;
                if (rbDangban.isSelected()) {
                    trangthai = "ĐANG BÁN";
                } else if (rbNgungban.isSelected()) {
                    trangthai = "NGƯNG BÁN";
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int soLuong = Integer.parseInt(soLuongStr);
                    double gia = Double.parseDouble(giaStr);


                    // Tạo đối tượng sản phẩm cập nhật
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMaSP(maSP);
                    sp.setTenSP(tenSP);
                    sp.setMaLSP(maLSP);
                    sp.setMaHSX(maHSX);
                    sp.setQuyCachDongGoi(dongGoi);
                    sp.setSoDangKy(sodk);
                    sp.setsoluong(soLuong);
                    sp.setGiaBan(gia);
                    sp.setTrangThai(trangthai);
                    System.out.println("MASP cần cập nhật: " + maSP);

                    boolean success = SanPhamBLL.updateSanPham(sp);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                        TTCT_SP ttctPage = mainFrame.getPage("ttctsp", TTCT_SP.class);
                        ttctPage.setThongTin( sp.getMaSP(), sp.getTenSP(),sp.getMaLSP(), sp.getsoluong(),
                                sp.getMaHSX(), sp.getQuyCachDongGoi(),  sp.getSoDangKy(), sp.getGiaBan(), sp.getTrangThai());
                        mainFrame.showPage("ttctsp");
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


    public void loadProductInfoForUpdate(String maSP) {
        SanPhamDTO sp = SanPhamBLL.getSanPhamById(maSP);

        if (sp != null) {
            txtTenSP.setText(sp.getTenSP());
            txtMaSP.setText(sp.getMaSP());
            for (int i = 0; i < comboMaLSP.getItemCount(); i++) {
                if (comboMaLSP.getItemAt(i).startsWith(sp.getMaLSP() + " -")) {
                    comboMaLSP.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < comboHSX.getItemCount(); i++) {
                if (comboHSX.getItemAt(i).startsWith(sp.getMaHSX() + " -")) {
                    comboHSX.setSelectedIndex(i);
                    break;
                }
            }
            txtGia.setText(String.valueOf(sp.getGiaBan()));
            txtQcdg.setText(sp.getQuyCachDongGoi());
            txtSodk.setText(sp.getSoDangKy());
            txtSoLuong.setText(String.valueOf(sp.getsoluong()));
            if ("ĐANG BÁN".equals(sp.getTrangThai())) {
                rbDangban.setSelected(true);
            } else if ("NGƯNG BÁN".equals(sp.getTrangThai())) {
                rbNgungban.setSelected(true);
            } else {
                groupTrangThai.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
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


