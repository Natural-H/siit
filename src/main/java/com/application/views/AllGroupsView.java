package com.application.views;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AllGroupsView extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    public static DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(dtm);

    public AllGroupsView() {
        setLayout(new GridBagLayout());

        dtm.setColumnIdentifiers(new String[]{
                "Grupo", "Materia", "Semestre", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });

        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        for (int i = 3; i < 8; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        table.setRowHeight(50);
        loadInfo();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
    }

    public static void loadInfo() {
        Object[][] data = new Object[Group.groups.size][9];

        for (int i = 0; i < Group.groups.size; i++) {
            Group group = Group.groups.get(i);

            data[i][0] = group.name;
            data[i][1] = new String[]{group.materia.nombre, group.teacher.getName()};
            data[i][2] = Integer.toString(group.semestre);

            for (int j = 3; j < 8; j++)
                data[i][j] = group.horario.days[j - 3].equals(Horario.Days[j - 3]) ?
                        new String[]{group.horario.horario, group.horario.places[j - 3]} : new String[]{"", ""};

            dtm.addRow(data[i]);
        }
    }
}
