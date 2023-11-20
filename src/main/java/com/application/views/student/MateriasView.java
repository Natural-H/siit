package com.application.views.student;

import com.application.models.materia.AssignedMateria;
import com.application.models.users.Advance;
import com.application.models.users.Student;
import com.application.models.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MateriasView extends JPanel {
    private Student context;
    private GridBagConstraints gbc = new GridBagConstraints();
    private DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(dtm);
    private final JScrollPane scrollPane = new JScrollPane(table);

    public MateriasView(User user) {
        this.context = (Student) user;
        Insets leftInsets = new Insets(5, 50, 5, 5);
        Insets rightInsets = new Insets(5, 5, 5, 50);
        Insets normalInsets = new Insets(5, 5, 5, 5);
        setLayout(new GridBagLayout());

        String[][] plainMaterias = new String[Advance.Semestre.values().length][3];

//        for (int i = 0; i < Advance.Semestre.values().length; i++) {
//            Advance advance = Advance.advanceHashMap.get(Advance.Semestre.values()[i]);
//            for (int j = 0; j < advance.materias.length; j++)
//                plainMaterias[j][i] = advance.materias[j].nombre;
//        }

        for (int i = 0; i < Advance.Semestre.values().length; i++) {
            int finalI = i;
            AssignedMateria[] materias = context.history.filter(mat -> mat.materia.semestre == (finalI + 1)).toArray();

            for (int j = 0; j < materias.length; j++) {
                plainMaterias[j][i] = materias[j].materia.nombre;
            }
        }

        dtm.setDataVector(plainMaterias, new String[]{
                "Semestre 1", "Semestre 2", "Semestre 3"
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        table.setRowHeight(50);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));

        gbc.insets = new Insets(5, 50, 5, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);
    }
}
