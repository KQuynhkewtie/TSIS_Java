package GUI;

import DTO.currentuser;
import GUI.Chart.BarChart;
import BLL.ThongKeBLL;
import DTO.ThongKeDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TabDoanhThu extends JPanel {
    private JTextField dayField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel lblTotalRevenue;
    private ThongKeBLL thongKeBLL;
    private BarChart barChart;
    private JRadioButton rbDay, rbMonth, rbYear, rbTotal;

    public TabDoanhThu() {
        setLayout(null);
        this.thongKeBLL = new ThongKeBLL();

        initUIComponents();
        initChart();
        setVisible(true);
    }

    private void initUIComponents() {
       
        JLabel lblInstruction = new JLabel("Nhập ngày/tháng/năm:");
        lblInstruction.setBounds(10, 2, 150, 25);
        add(lblInstruction);

        rbDay = new JRadioButton("Theo ngày");
        rbMonth = new JRadioButton("Theo tháng");
        rbYear = new JRadioButton("Theo năm");
        rbTotal = new JRadioButton("Tổng");

        rbDay.setBounds(10, 20, 100, 25);
        rbMonth.setBounds(110, 20, 100, 25);
        rbYear.setBounds(210, 20, 100, 25);
        rbTotal.setBounds(310, 20, 100, 25);

        ButtonGroup group = new ButtonGroup();
        group.add(rbDay);
        group.add(rbMonth);
        group.add(rbYear);
        group.add(rbTotal);
        rbDay.setSelected(true);

        add(rbDay);
        add(rbMonth);
        add(rbYear);
        add(rbTotal);

        rbDay.addActionListener(e -> {
            updatePlaceholder("DD/MM/YYYY");
            barChart.setVisible(false);
        });

        rbMonth.addActionListener(e -> {
            updatePlaceholder("MM/YYYY");
            barChart.setVisible(true);
        });

        rbYear.addActionListener(e -> {
            updatePlaceholder("YYYY");
            barChart.setVisible(true);
        });

        rbTotal.addActionListener(e -> {
            dayField.setText("");
            barChart.setVisible(true);
            searchTotal();
        });

        this.dayField = new JTextField("DD/MM/YYYY");
        dayField.setBounds(10, 50, 400, 30);
        dayField.setForeground(Color.GRAY);
        add(dayField);

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setBounds(450, 50, 100, 30);
        btnSearch.setBackground(Color.decode("#F5A623"));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> performSearch());
        add(btnSearch);

        dayField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!currentuser.coQuyen("Xem doanh thu")) {
                    JOptionPane.showMessageDialog(null, "Bạn không có quyền xem doanh thu!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    dayField.transferFocus();
                    return;
                }
                if (dayField.getText().matches("^(DD/MM/YYYY|MM/YYYY|YYYY)$")) {
                    dayField.setText("");
                    dayField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dayField.getText().trim().isEmpty()) {
                    if (rbDay.isSelected()) updatePlaceholder("DD/MM/YYYY");
                    else if (rbMonth.isSelected()) updatePlaceholder("MM/YYYY");
                    else if (rbYear.isSelected()) updatePlaceholder("YYYY");
                }
            }
        });

        dayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        JPanel revenuePanel = new JPanel();
        revenuePanel.setLayout(new BoxLayout(revenuePanel, BoxLayout.Y_AXIS));
        revenuePanel.setBounds(600, 15, 300, 50);

        JLabel lblRevenue = new JLabel("Tổng doanh thu:");
        lblRevenue.setFont(new Font("Arial", Font.BOLD, 17));
        lblRevenue.setAlignmentX(Component.LEFT_ALIGNMENT);
        revenuePanel.add(lblRevenue);

        lblTotalRevenue = new JLabel("0");
        lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 17));
        lblTotalRevenue.setForeground(Color.decode("#641A1F"));
        lblTotalRevenue.setAlignmentX(Component.LEFT_ALIGNMENT);
        revenuePanel.add(lblTotalRevenue);

        add(revenuePanel);

        String[] columns = {"STT", "Thời gian", "Số hóa đơn", "Doanh thu"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 430, 750, 110);
        add(scrollPane);
    }

    private void initChart() {
        barChart = new BarChart();
        barChart.setBounds(20, 90, 750, 330);
        add(barChart);
        barChart.setVisible(false); 
    }

    private void updatePlaceholder(String placeholder) {
        dayField.setText(placeholder);
        dayField.setForeground(Color.GRAY);
    }

    private void performSearch() {
        String input = dayField.getText().trim();

        try {
            if (rbDay.isSelected()) {
                searchByDay(input);
            } else if (rbMonth.isSelected()) {
                searchByMonth(input);
            } else if (rbYear.isSelected()) {
                searchByYear(input);
            } else if (rbTotal.isSelected()) {
                searchTotal();
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByDay(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(dateStr);

        List<ThongKeDTO> data = thongKeBLL.getTableDataForDay(date);

        barChart.setVisible(false);

        updateTable(data, "dd/MM/yyyy");

        updateTotalRevenue(data);
    }

    private void searchByMonth(String monthYear) throws ParseException {
        List<ThongKeDTO> tableData = thongKeBLL.getTableDataForMonth(monthYear);
        double[] chartData = thongKeBLL.getChartDataForMonth(monthYear);

        if (chartData == null || chartData.length == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu cho tháng này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        barChart.updateCumNgayChart(monthYear, chartData);
        barChart.setVisible(true);

        updateTable(tableData, "dd/MM");

        updateTotalRevenue(tableData);
    }

    private void searchByYear(String year) {
        List<ThongKeDTO> tableData = thongKeBLL.getTableDataForYear(year);
        double[] chartData = thongKeBLL.getChartDataForYear(year);

        barChart.updateMonthChart(year, chartData);
        barChart.setVisible(true);

        updateTable(tableData, "MM/yyyy");

        updateTotalRevenue(tableData);
    }

    private void searchTotal() {
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());

        List<ThongKeDTO> tableData = thongKeBLL.getTableDataForTotal(currentYear);
        double[] chartData = thongKeBLL.getChartDataForTotal(currentYear);

        barChart.updateYearChart(currentYear, chartData);
        barChart.setVisible(true);

        updateTable(tableData, "yyyy");

        updateTotalRevenue(tableData);
    }

    private void updateTable(List<ThongKeDTO> data, String dateFormat) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        int stt = 1;
        for (ThongKeDTO item : data) {
            String time;

            if (dateFormat.equals("dd/MM/yyyy") || dateFormat.equals("dd/MM")) {               
                time = sdf.format(item.getNgay());
            } else if (dateFormat.equals("MM/yyyy")) {
                if (item.getNgay() != null) {
                    time = sdf.format(item.getNgay()); 
                } else if (item.getThang() != null) {
                    time = item.getThang(); 
                } else {
                    time = "";
                }
            } else if (dateFormat.equals("yyyy")) {
                if (item.getThang() != null) {
                    time = item.getThang(); 
                } else if (item.getNam() != null) {
                    time = item.getNam(); 
                } else {
                    time = "";
                }
            } else {
                time = "";
            }

            tableModel.addRow(new Object[]{
                    stt++,
                    time,
                    item.getSoHoaDon(),
                    thongKeBLL.formatCurrency(item.getDoanhThu())
            });
        }
    }

    private void updateTotalRevenue(List<ThongKeDTO> data) {
        double total = data.stream().mapToDouble(ThongKeDTO::getDoanhThu).sum();
        lblTotalRevenue.setText(thongKeBLL.formatCurrency(total));
    }
}