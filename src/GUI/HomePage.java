package GUI;

import javax.swing.*;
import java.awt.*;
import BLL.NhanVienBLL;
import BLL.SanPhamBLL;
import BLL.KhachHangBLL;
import BLL.HoaDonBLL;

public class HomePage extends BasePanel {
	private JPanel detailPanel;
	private NhanVienBLL bllnv = new NhanVienBLL();
	private SanPhamBLL bllsp = new SanPhamBLL();
	private KhachHangBLL bllkh = new KhachHangBLL();
	private HoaDonBLL bllhd = new HoaDonBLL();

	public HomePage(MainFrame mainFrame) {
		super(mainFrame);
		initUniqueComponents();
	}

	@Override
	public void onPageShown() {
		highlightMenuButton("Trang chủ");
		updateSummaryCards();
	}

	protected void initUniqueComponents() {

		JLabel lblTitle = new JLabel("Trang chủ");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setBounds(20, 20, 200, 30);
		add(lblTitle);


		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/pharmacy.jpg"));
		JLabel pharmacyImage = new JLabel(icon);
		pharmacyImage.setBounds(100, 60, 537, 280);
		add(pharmacyImage);

		JPanel summaryPanel = new JPanel();
		summaryPanel.setBounds(20, 80, 800, 100);
		summaryPanel.setLayout(new GridLayout(1, 2, 20, 10));
		add(summaryPanel);

		detailPanel = new JPanel();
		detailPanel.setBounds(20, 350, 750, 250);
		detailPanel.setLayout(new GridLayout(2, 2, 25, 25));
		add(detailPanel);


	}

	private JPanel createCard(String title, String subtitle, String value, Color bgColor) {
		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBackground(bgColor);
		card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setBounds(20, 10, 200, 25);
		card.add(lblTitle);

		JLabel lblSubtitle = new JLabel(subtitle);
		lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSubtitle.setBounds(20, 40, 200, 20);
		card.add(lblSubtitle);

		JLabel lblValue = new JLabel(value);
		lblValue.setFont(new Font("Arial", Font.BOLD, 20));
		lblValue.setBounds(20, 70, 200, 30);
		card.add(lblValue);

		return card;
	}

	private void updateSummaryCards() {
		detailPanel.removeAll();

		int totalProducts = bllsp.countSanPham();
		int totalEmployees = bllnv.countNhanVien();
		int totalCustomers = bllkh.countKhachHang();
		double totalRevenue = bllhd.getDoanhThunamhientai();

		detailPanel.add(createCard("Sản phẩm", "Số lượng sản phẩm", String.valueOf(totalProducts), Color.WHITE));
		detailPanel.add(createCard("Doanh thu", "Tổng doanh thu", String.format("%.0f", totalRevenue), Color.WHITE));
		detailPanel.add(createCard("Nhân viên", "Số lượng nhân viên", String.valueOf(totalEmployees), Color.WHITE));
		detailPanel.add(createCard("Khách hàng", "Số lượng khách hàng", String.valueOf(totalCustomers), Color.WHITE));
		detailPanel.revalidate();
		detailPanel.repaint();
	}

}