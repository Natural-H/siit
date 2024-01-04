package com.application.views.student;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.application.models.users.Student;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;

public class HorarioView extends JPanel {
    private Student context;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel lbTitle = new JLabel("Materias");

    private DefaultTableModel dtm = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(dtm);
    private JScrollPane scrollPane = new JScrollPane(table);

    public HorarioView(User user) {
        context = (Student) user;

        dtm.setDataVector(null, new String[]{
                "Materia y Profesor", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });
        table.setRowHeight(50);
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        for (int i = 0; i < 7; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                updateTable();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        lbTitle.setFont(lbTitle.getFont().deriveFont(
                Font.PLAIN, 18f));

        setLayout(new GridBagLayout());
        add(lbTitle, gbc);

        gbc.gridy++;
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
