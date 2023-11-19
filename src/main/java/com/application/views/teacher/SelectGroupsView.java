package com.application.views.teacher;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelectGroupsView extends JPanel {
    private Teacher context;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(dtm);

    public SelectGroupsView(User user) {
        this.context = (Teacher) user;
        setLayout(new GridBagLayout());

        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
//        table.setDefaultRenderer(String[].class, renderer);
        dtm.setColumnIdentifiers(new String[]{
                "Grupo", "Materia", "Semestre", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });

        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table.getColumnModel().getColumn(3).setCellRenderer(renderer);
        table.getColumnModel().getColumn(4).setCellRenderer(renderer);
        table.getColumnModel().getColumn(5).setCellRenderer(renderer);
        table.getColumnModel().getColumn(6).setCellRenderer(renderer);
        table.getColumnModel().getColumn(7).setCellRenderer(renderer);

        table.setRowHeight(50);
        Object[][] data = new Object[Group.groups.size][9];

        for (int i = 0; i < Group.groups.size; i++) {
            Group group = Group.groups.get(i);

            data[i][0] = group.name;
            data[i][1] = new String[]{group.materia.nombre, group.teacher.getName()};
            data[i][2] = Integer.toString(group.semestre);
            data[i][3] = group.horario.days[0].equals(Horario.Days[0]) ? new String[] { group.horario.horario, group.horario.places[0]} : "";
            data[i][4] = group.horario.days[1].equals(Horario.Days[1]) ? new String[] { group.horario.horario, group.horario.places[1]} : "";
            data[i][5] = group.horario.days[2].equals(Horario.Days[2]) ? new String[] { group.horario.horario, group.horario.places[2]} : "";
            data[i][6] = group.horario.days[3].equals(Horario.Days[3]) ? new String[] { group.horario.horario, group.horario.places[3]} : "";
            data[i][7] = group.horario.days[4].equals(Horario.Days[4]) ? new String[] { group.horario.horario, group.horario.places[4]} : "";

            data[i][8] = "";

            dtm.addRow(data[i]);
        }

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
    }
}
