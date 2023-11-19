package com.utils.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String[]) setListData((String[]) value);

        if (isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
        else setBackground(UIManager.getColor("Table.background"));

        DefaultListCellRenderer renderer = (DefaultListCellRenderer)getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        return this;
    }
}
