package com.application.views.student;

import com.application.models.materia.AssignedMateria;
import com.application.models.users.Advance;
import com.application.models.users.Student;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MateriasView extends JPanel {
    private final Student context;
    private final DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public MateriasView(User user) {
        this.context = (Student) user;
        setLayout(new GridBagLayout());

        Object[][] plainMaterias = new Object[Advance.Semestre.values().length][3];

        JTable table = new JTable(dtm);
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        dtm.setColumnIdentifiers(new String[]{
                "Semestre 1", "Semestre 2", "Semestre 3"
        });

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);

                dtm.setRowCount(0);
                for (int i = 0; i < Advance.Semestre.values().length; i++) {
                    int finalI = i;
                    AssignedMateria[] materias = context.history.filter(mat -> mat.materia.semestre == (finalI + 1)).toArray(new AssignedMateria[0]);

                    for (int j = 0; j < materias.length; j++) {
                        plainMaterias[j][i] = new String[]{materias[j].materia.name, String.valueOf(materias[j].state)};
                    }
                }

                for (int i = 0; i < plainMaterias[0].length; i++) dtm.addRow(plainMaterias[i]);
            }
        });

//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

//        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++)
//            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

//        table.setRowHeight(50);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 50, 5, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
    }
}
