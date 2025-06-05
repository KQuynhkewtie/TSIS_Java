package GUI;

import DTO.currentuser;
import javax.swing.*;
import BLL.SanPhamBLL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BLL.HangSanXuatBLL;
import BLL.LoaiSPBLL;
import DTO.LoaiSPDTO;
import DTO.HangSanXuatDTO;

public class TTCT_SP extends BasePanel {
    private HangSanXuatBLL hsxBLL = new HangSanXuatBLL();
    private LoaiSPBLL lspBLL = new LoaiSPBLL();
    private JTextArea txtTenSP, txtMaSP, cbLoaiSP, txtSoLuong, txtHSX, txtQcdg,  txtSodk,  txtGia, txtTT;

    public TTCT_SP(MainFrame mainFrame) {
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

        JLabel lblArrow = new JLabel(" >> Thông tin sản phẩm");
        lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
        lblArrow.setBounds(120, 20, 300, 30);
        add(lblArrow);

        lblSanPhamLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showPage("sanpham");
            }
        });


        JLabel lblTenSP = new JLabel("Mã sản phẩm:");
        lblTenSP.setBounds(20, 80, 150, 25);
        add(lblTenSP);
        txtMaSP = new JTextArea();
        txtMaSP.setBounds(20, 110, 400, 60);
        txtMaSP.setLineWrap(true);
        txtMaSP.setWrapStyleWord(true);
        txtMaSP.setEditable(false);
        txtMaSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtMaSP.setForeground(Color.decode("#641A1F"));
        txtMaSP.setOpaque(false);
        add(txtMaSP);

        JLabel lblMaSP = new JLabel("Tên sản phẩm:");
        lblMaSP.setBounds(460, 80, 150, 25);
        add(lblMaSP);
        txtTenSP = new JTextArea();
        txtTenSP.setBounds(460, 110, 300, 30);
        txtTenSP.setLineWrap(true);
        txtTenSP.setWrapStyleWord(true);
        txtTenSP.setEditable(false);
        txtTenSP.setFont(new Font("Arial", Font.BOLD, 20));
        txtTenSP.setForeground(Color.decode("#641A1F"));
        txtTenSP.setOpaque(false);
        add(txtTenSP);

        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");
        lblLoaiSP.setBounds(20, 160, 150, 25);
        add(lblLoaiSP);
        cbLoaiSP = new JTextArea();
        cbLoaiSP.setBounds(20, 190, 400, 40);
        cbLoaiSP.setLineWrap(true);
        cbLoaiSP.setWrapStyleWord(true);
        cbLoaiSP.setEditable(false);
        cbLoaiSP.setFont(new Font("Arial", Font.BOLD, 20));
        cbLoaiSP.setForeground(Color.decode("#641A1F"));
        cbLoaiSP.setOpaque(false);
        add(cbLoaiSP);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(460, 160, 150, 25);
        add(lblSoLuong);
        txtSoLuong = new JTextArea();
        txtSoLuong.setBounds(460, 190, 400, 40);
        txtSoLuong.setLineWrap(true);
        txtSoLuong.setWrapStyleWord(true);
        txtSoLuong.setEditable(false);
        txtSoLuong.setFont(new Font("Arial", Font.BOLD, 20));
        txtSoLuong.setForeground(Color.decode("#641A1F"));
        txtSoLuong.setOpaque(false);
        add(txtSoLuong);

        JLabel lblHSX = new JLabel("Hãng sản xuất:");
        lblHSX.setBounds(20, 240, 150, 25);
        add(lblHSX);
        txtHSX = new JTextArea();
        txtHSX.setBounds(20, 270, 300, 80);
        txtHSX.setLineWrap(true);
        txtHSX.setWrapStyleWord(true);
        txtHSX.setEditable(false);
        txtHSX.setFont(new Font("Arial", Font.BOLD, 20));
        txtHSX.setForeground(Color.decode("#641A1F"));
        txtHSX.setOpaque(false);
        add(txtHSX);

        JLabel lblqcdg = new JLabel("Quy cách đóng gói:");
        lblqcdg.setBounds(460, 240, 150, 25);
        add(lblqcdg);
        txtQcdg = new JTextArea();
        txtQcdg.setBounds(460, 270, 300, 40);
        txtQcdg.setLineWrap(true);
        txtQcdg.setWrapStyleWord(true);
        txtQcdg.setEditable(false);
        txtQcdg.setFont(new Font("Arial", Font.BOLD, 20));
        txtQcdg.setForeground(Color.decode("#641A1F"));
        txtQcdg.setOpaque(false);
        add(txtQcdg);



        JLabel lblSodk = new JLabel("Số đăng ký:");
        lblSodk.setBounds(460, 320, 150, 25);
        add(lblSodk);
        txtSodk = new JTextArea();
        txtSodk.setBounds(460, 350, 300, 40);
        txtSodk.setLineWrap(true);
        txtSodk.setWrapStyleWord(true);
        txtSodk.setEditable(false);
        txtSodk.setFont(new Font("Arial", Font.BOLD, 20));
        txtSodk.setForeground(Color.decode("#641A1F"));
        txtSodk.setOpaque(false);
        add(txtSodk);


        JLabel lblGia = new JLabel("Giá bán:");
        lblGia.setBounds(460, 410, 150, 25);
        add(lblGia);
        txtGia = new JTextArea();
        txtGia.setBounds(460, 440, 300, 40);
        txtGia.setLineWrap(true);
        txtGia.setWrapStyleWord(true);
        txtGia.setEditable(false);
        txtGia.setFont(new Font("Arial", Font.BOLD, 20));
        txtGia.setForeground(Color.decode("#641A1F"));
        txtGia.setOpaque(false);
        add(txtGia);

        //Trạng thái
        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setBounds(20, 320, 150, 25);
        add(lblTT);
        txtTT = new JTextArea();
        txtTT.setBounds(20, 350, 300, 40);
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
                if (!currentuser.coQuyen("Xóa sản phẩm")) {

                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    SanPhamBLL bllSanPham = new SanPhamBLL();
                    boolean xoaThanhCong = bllSanPham.deleteSanPhamById(txtMaSP.getText());;
                    if (xoaThanhCong) {
                        JOptionPane.showMessageDialog(null, "Đã xóa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.getPage("sanpham", SanPham.class).refreshData();
                        mainFrame.showPage("sanpham");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại. Sản phẩm có thể đã liên kết dữ liệu khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                String maSP = txtMaSP.getText();
                CapNhatTT_SP capNhatPage = mainFrame.getPage("capnhatsp", CapNhatTT_SP.class);
                capNhatPage.loadProductInfoForUpdate(maSP);
                mainFrame.showPage("capnhatsp");
            }
        });
        
        setVisible(true);
    }


    public void setThongTin( String maSP, String tenSP,String loaiSP, int soLuong, String maHSX, String dongGoi, String soDk,  Double gia, String trangthai) {
        txtMaSP.setText(maSP);
        txtTenSP.setText(tenSP);
        LoaiSPDTO loaiSPObj = lspBLL.getLSPById(loaiSP);
        String tenLSP = (loaiSPObj != null) ? loaiSPObj.getTenLSP() : "";
        cbLoaiSP.setText(tenLSP);
        txtSoLuong.setText(String.valueOf(soLuong));
        HangSanXuatDTO hsxObj = hsxBLL.getHSXbyID(maHSX);
        String tenHSX = (hsxObj != null) ? hsxObj.getTenHSX() : "";
        txtHSX.setText(tenHSX);
        txtQcdg.setText(dongGoi);
        txtSodk.setText(soDk);
        txtGia.setText(String.valueOf(gia));
        txtTT.setText(trangthai);
        revalidate();
        repaint();
    }

}
