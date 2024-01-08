package com.application.views.student;

import com.application.models.users.Student;
import com.application.models.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class PartialGradesView extends JPanel {
    private Student context;
    private final DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PartialGradesView(User user) {
        context = (Student) user;
        setLayout(new GridBagLayout());

        dtm.setColumnIdentifiers(new String[]{
                "Materia", "Unidad 1", "Unidad 2", "Unidad 3", "Unidad 4", "Unidad 5",
                "Unidad 6", "Unidad 7", "Unidad 8", "Unidad 9", "Unidad 10", "Promedio"
        });

        Object[] info = new Object[12];
        context.assigned.forEach(a -> {
                    info[0] = a.materia.name;
                    System.arraycopy(a.grades, 0, info, 1, a.grades.length);
                    info[11] = Arrays.stream(a.grades).mapToDouble(Double::doubleValue).sum() /
                            Arrays.stream(a.grades).filter(n -> n != 0).count();
                    info[11] = Double.isNaN((Double) info[11]) ? 0 : info[11];

                    dtm.addRow(info);
                }
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTable table = new JTable(dtm);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(215);

        for (int i = 1; i < 12; i++) table.getColumnModel().getColumn(i).setPreferredWidth(55);
        add(new JScrollPane(table), gbc);
    }
}
