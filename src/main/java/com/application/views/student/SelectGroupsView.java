package com.application.views.student;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Student;
import com.application.models.users.User;
import com.utils.CustomList;
import com.utils.swing.MultiLineTableCellRenderer;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectGroupsView extends JPanel {
    private Student student;
    private GridBagConstraints gbc = new GridBagConstraints();
    private DefaultTableModel dtm = new DefaultTableModel();
    private JTable table = new JTable(dtm) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JScrollPane scrollPane = new JScrollPane(table);

    private DefaultComboBoxModel<Materia> assignableMaterias = new DefaultComboBoxModel<>();
    private JXComboBox comboFilterMaterias = new JXComboBox(assignableMaterias);
    private DefaultListModel<Group> modelToTake = new DefaultListModel<>();
    private JXList<Group> jListToTake = new JXList<>(modelToTake);

    public SelectGroupsView(User user) {
        student = (Student) user;
        setLayout(new GridBagLayout());

        table.setRowHeight(50);

        dtm.setColumnIdentifiers(new String[]{
                "Grupo", "Materia", "Semestre", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });

        assignableMaterias.addElement(null);
        Materia.materias.filter(m -> student.canTake(m)).forEach(assignableMaterias::addElement);

        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        for (int i = 3; i < 8; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        fillData();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                handleJoinGroup(e);
            }
        });

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(scrollPane, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(comboFilterMaterias, gbc);

        gbc.gridx++;
        JScrollPane scrollPane1 = new JScrollPane(jListToTake);
        scrollPane1.setPreferredSize(new Dimension(150, 125));
        add(scrollPane1, gbc);

        gbc.gridx++;
        add(new JButton("Yes"), gbc);
    }

    private void fillData() {
        CustomList<Group> availableGroups = Group.groups.filter(g -> student.canTake(g.materia));
        Object[][] data = new Object[availableGroups.size][9];

        for (int i = 0; i < availableGroups.size; i++) {
            Group group = availableGroups.get(i);

            data[i][0] = group.name;
            data[i][1] = new String[]{group.materia.name, group.teacher.getName()};
            data[i][2] = Integer.toString(group.semestre);

            for (int j = 3; j < 8; j++)
                data[i][j] = group.horario.days[j - 3].equals(Horario.Days[j - 3]) ?
                        new String[]{group.horario.time, group.horario.places[j - 3]} : new String[]{"", ""};

            dtm.addRow(data[i]);
        }
    }

    private void handleJoinGroup(MouseEvent e) {
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            if (JOptionPane.showConfirmDialog(this, "¿Seguro que quieres unirte a este grupo?", "Confirma", JOptionPane.YES_NO_OPTION) != 0)
                return;

            Group group = Group.groups.find(g -> g.name.equals(
                    dtm.getValueAt(table.getSelectedRow(), 0).toString()
            ) && g.materia.name.equals(
                    ((String[]) dtm.getValueAt(table.getSelectedRow(), 1))[0]
            ));

            modelToTake.addElement(group);
        }
    }
}
