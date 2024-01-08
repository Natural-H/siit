package com.application.views.student;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.application.models.users.Student;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;

public class HorarioView extends JPanel {
    private final Student context;

    private final DefaultTableModel dtm = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public HorarioView(User user) {
        context = (Student) user;

        dtm.setColumnIdentifiers(new String[] {
                "Materia y Profesor", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });
        JTable table = new JTable(dtm);
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        for (int i = 0; i < 7; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                updateTable();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 12, 5, 5);

        JLabel lbTitle = new JLabel("Mi Horario");
        lbTitle.setFont(lbTitle.getFont().deriveFont(
                Font.PLAIN, 22f));

        setLayout(new GridBagLayout());
        add(lbTitle, gbc);

        table.getColumnModel().getColumn(0).setPreferredWidth(175);
        gbc.gridy++;
        gbc.weighty = 1;
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
    }

    public void updateTable() {
        dtm.setRowCount(0);

        context.groups.forEach(g -> dtm.addRow(new Object[] {
                new String[] {g.materia.name, g.teacher.getName()},
                new String[] {!g.horario.days[0].isEmpty() ? g.horario.time : "", !g.horario.days[0].isEmpty() ? g.horario.places[0] : ""},
                new String[] {!g.horario.days[1].isEmpty() ? g.horario.time : "", !g.horario.days[1].isEmpty() ? g.horario.places[1] : ""},
                new String[] {!g.horario.days[2].isEmpty() ? g.horario.time : "", !g.horario.days[2].isEmpty() ? g.horario.places[2] : ""},
                new String[] {!g.horario.days[3].isEmpty() ? g.horario.time : "", !g.horario.days[3].isEmpty() ? g.horario.places[3] : ""},
                new String[] {!g.horario.days[4].isEmpty() ? g.horario.time : "", !g.horario.days[4].isEmpty() ? g.horario.places[4] : ""},
                new String[] {!g.horario.days[5].isEmpty() ? g.horario.time : "", !g.horario.days[5].isEmpty() ? g.horario.places[5] : ""},
        }));
    }
}
