package com.application.views.student;

import com.application.models.materia.AssignedMateria;
import com.application.models.users.Advance;
import com.application.models.users.Student;
import com.application.models.users.User;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class HistoryView extends JPanel {
    private Student context;
    private DefaultTableModel[] models;
    private JTable[] tables;

    public HistoryView(User user) {
        this.context = (Student) user;
        setLayout(new GridBagLayout());

        models = new DefaultTableModel[context.semestre - 1];
        tables = new JTable[context.semestre - 1];

        for (int i = 0; i < tables.length; i++) {
            models[i] = new DefaultTableModel();
            tables[i] = new JTable(models[i]);
        }

        for (int i = 0; i < models.length; i++) {
            DefaultTableModel model = models[i];

            model.setColumnIdentifiers(new String[] {
                    "Materia", "Unidad 1", "Unidad 2", "Unidad 3", "Unidad 4", "Unidad 5",
                    "Unidad 6", "Unidad 7", "Unidad 8", "Unidad 9", "Unidad 10", "Promedio"
            });

            int finalI = i + 1;
            AssignedMateria[] toRegister = context.history.filter(a -> a.materia.semestre == finalI).toArray(new AssignedMateria[0]);

            for (AssignedMateria materia : toRegister) {
                Object[] info = new Object[12];
                info[0] = materia.materia.name;
                if (!materia.state.equals(AssignedMateria.State.NOT_COURSED)) {
                    System.arraycopy(materia.grades, 0, info, 1, materia.grades.length);
                    info[11] = Arrays.stream(materia.grades).mapToDouble(Double::doubleValue).sum() / 10;
                }
                else {
                    System.arraycopy(new Double[10], 0, info, 1, 10);
                    info[11] = null;
                }
                model.addRow(info);
            }
        }

        JScrollPane scrollPane = new JScrollPane(new JPanel() {
            {
                setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;
                gbc.weighty = 0;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.insets = new Insets(5, 5, 5, 5);

                for (int i = 0; i < tables.length; i++) {
                    JTable table = tables[i];

                    table.getPreferredScrollableViewportSize().height = 60;
                    table.setFillsViewportHeight(true);
                    gbc.weighty = i == tables.length - 1 ? 1 : 0;
                    add(new JScrollPane(table), gbc);
                    gbc.gridy++;
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(scrollPane, gbc);
    }
}
