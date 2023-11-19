package com.application.views.student;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.application.models.users.User;

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

        dtm.setDataVector(null, new String[] {
                "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        lbTitle.setFont(lbTitle.getFont().deriveFont(
                Font.PLAIN, 18f));

        setLayout(new GridBagLayout());
        add(lbTitle, gbc);

        gbc.gridy++;
        add(scrollPane, gbc);
    }
}
