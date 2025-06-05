package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class BasePanel extends JPanel {

    protected MainFrame mainFrame;

    public BasePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
    }
    protected void highlightMenuButton(String menuText) {
        mainFrame.highlightMenuButton(menuText);
    }

    protected void addExceltButton(JTable table) {
        ImageIcon iconExcel = new ImageIcon(getClass().getClassLoader().getResource("image/excel-icon.png"));
        Image imgExcel = iconExcel.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH); // Scale ảnh
        ImageIcon resizedIconExcel = new ImageIcon(imgExcel);

        JButton btnExportExcel = new JButton(resizedIconExcel);
        btnExportExcel.setBounds(650, 25, 40, 40);

        btnExportExcel.setBackground(null);
        btnExportExcel.setBorderPainted(false);
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setContentAreaFilled(false);

        btnExportExcel.setOpaque(true);

        btnExportExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnExportExcel.addActionListener(e -> {
            try {
                if (table != null && table.getRowCount() > 0) {
                    boolean success = helper.JTableExporter.exportJTableToExcel(table);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
            }

        });

        add(btnExportExcel);

    }

    public void onPageShown() {}

    public void refreshData() {}
}
