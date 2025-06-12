package GUI;
import javax.swing.*;

import BLL.KhachHangBLL;
import DTO.currentuser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TTCT_KH extends BasePanel {
	private JTextArea txtHoTen, txtMaKH, txtDiemTL, txtsdt, txtLoaiKH, txtcccd;

	public TTCT_KH(MainFrame mainFrame) {
		super(mainFrame);
		initUniqueComponents();
	}

	@Override
	public void onPageShown() {
		highlightMenuButton("Khách hàng");
	}

	protected void initUniqueComponents() {

		JLabel lblKhachhangLink = new JLabel("<html><u>Khách hàng</u></html>");
		lblKhachhangLink.setFont(new Font("Arial", Font.BOLD, 20));
		lblKhachhangLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblKhachhangLink.setBounds(20, 20, 120, 30);
		add(lblKhachhangLink);

		lblKhachhangLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.showPage("khachhang");
			}
		});

		JLabel lblArrow = new JLabel(" >> Thông tin khách hàng");
		lblArrow.setFont(new Font("Arial", Font.BOLD, 20));
		lblArrow.setBounds(140, 20, 300, 30);
		add(lblArrow);

		JLabel lblMaKH = new JLabel("Mã khách hàng:");
		lblMaKH.setBounds(20, 80, 150, 30);
		add(lblMaKH);
		txtMaKH = new JTextArea();
		txtMaKH.setBounds(200, 80, 200, 30);
		txtMaKH.setLineWrap(true);
		txtMaKH.setWrapStyleWord(true);
		txtMaKH.setEditable(false);
		txtMaKH.setFont(new Font("Arial", Font.BOLD, 20));
		txtMaKH.setForeground(Color.decode("#641A1F"));
		txtMaKH.setOpaque(false);
		add(txtMaKH);

		JLabel lblHoTen = new JLabel("Họ tên:");
		lblHoTen.setBounds(20, 130, 150, 30);
		add(lblHoTen);
		txtHoTen = new JTextArea();
		txtHoTen.setBounds(200, 130, 200, 60);
		txtHoTen.setLineWrap(true);
		txtHoTen.setWrapStyleWord(true);
		txtHoTen.setEditable(false);
		txtHoTen.setFont(new Font("Arial", Font.BOLD, 20));
		txtHoTen.setForeground(Color.decode("#641A1F"));
		txtHoTen.setOpaque(false);
		add(txtHoTen);

		JLabel lblSoLanMua = new JLabel("Số điện thoại:");
		lblSoLanMua.setBounds(20, 180, 150, 30);
		add(lblSoLanMua);
		txtsdt = new JTextArea();
		txtsdt.setBounds(200, 180, 200, 30);
		txtsdt.setLineWrap(true);
		txtsdt.setWrapStyleWord(true);
		txtsdt.setEditable(false);
		txtsdt.setFont(new Font("Arial", Font.BOLD, 20));
		txtsdt.setForeground(Color.decode("#641A1F"));
		txtsdt.setOpaque(false);
		add(txtsdt);

		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setBounds(20, 230, 150, 30);
		add(lblCCCD);
		txtcccd = new JTextArea();
		txtcccd.setBounds(200, 230, 200, 30);
		txtcccd.setLineWrap(true);
		txtcccd.setWrapStyleWord(true);
		txtcccd.setEditable(false);
		txtcccd.setFont(new Font("Arial", Font.BOLD, 20));
		txtcccd.setForeground(Color.decode("#641A1F"));
		txtcccd.setOpaque(false);
		add(txtcccd);

		JLabel lblDiemTL = new JLabel("Điểm tích lũy:");
		lblDiemTL.setBounds(20, 280, 150, 30);
		add(lblDiemTL);
		txtDiemTL = new JTextArea();
		txtDiemTL.setBounds(200, 280, 200, 30);
		txtDiemTL.setLineWrap(true);
		txtDiemTL.setWrapStyleWord(true);
		txtDiemTL.setEditable(false);
		txtDiemTL.setFont(new Font("Arial", Font.BOLD, 20));
		txtDiemTL.setForeground(Color.decode("#641A1F"));
		txtDiemTL.setOpaque(false);
		add(txtDiemTL);

		JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
		lblLoaiKH.setBounds(20, 330, 150, 30);
		add(lblLoaiKH);
		txtLoaiKH = new JTextArea();
		txtLoaiKH.setBounds(200, 330, 200, 30);
		txtLoaiKH.setLineWrap(true);
		txtLoaiKH.setWrapStyleWord(true);
		txtLoaiKH.setEditable(false);
		txtLoaiKH.setFont(new Font("Arial", Font.BOLD, 20));
		txtLoaiKH.setForeground(Color.decode("#641A1F"));
		txtLoaiKH.setOpaque(false);
		add(txtLoaiKH);
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBounds(450, 480, 100, 40);
		btnXoa.setBackground(Color.decode("#F0483E"));
		btnXoa.setForeground(Color.WHITE);
		add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!currentuser.coQuyen("Xóa khách hàng")) {

					JOptionPane.showMessageDialog(null, "Bạn không có quyền xóa!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					KhachHangBLL khBLL = new KhachHangBLL();
					boolean xoaThanhCong = khBLL.deleteKhachHangById(txtMaKH.getText());
					;
					if (xoaThanhCong) {
						JOptionPane.showMessageDialog(null, "Đã xóa khách hàng thành công!");
						mainFrame.getPage("khachhang", KhachHang.class).refreshData();
						mainFrame.showPage("khachhang");
					} else {
						System.out.println("Xóa khách hàng thất bại");
					}
				}
			}
		});

		JButton btnCapnhat = new JButton("Cập nhật");
		btnCapnhat.setBounds(610, 480, 100, 40);
		btnCapnhat.setBackground(Color.decode("#F0483E"));
		btnCapnhat.setForeground(Color.WHITE);
		add(btnCapnhat);

		btnCapnhat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maKH = txtMaKH.getText();

				CapNhatTT_KH capNhatPage = mainFrame.getPage("capnhatkh", CapNhatTT_KH.class);
				capNhatPage.loadCustomerInfoForUpdate(maKH);
				mainFrame.showPage("capnhatkh");
			}
		});

		setVisible(true);
	}

	public void setThongTin(String maKH, String tenKH, Double DTL, String maLKH, String sdt, String cccd) {
		txtMaKH.setText(maKH);
		txtHoTen.setText(tenKH);
		txtDiemTL.setText(String.valueOf(DTL));
		txtLoaiKH.setText(maLKH);
		txtsdt.setText(sdt);
		txtcccd.setText(cccd);
		revalidate();
		repaint();
	}
}
