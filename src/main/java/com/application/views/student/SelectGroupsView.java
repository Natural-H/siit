package com.application.views.student;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Student;
import com.application.models.users.User;
import com.utils.CustomList;
import com.utils.swing.MultiLineTableCellRenderer;
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

    private DefaultComboBoxModel<Materia> comboModelMaterias = new DefaultComboBoxModel<>();
    private JComboBox<Materia> comboFilterMaterias = new JComboBox<>(comboModelMaterias);
    private DefaultListModel<Group> listModelToTake = new DefaultListModel<>();
    private JXList<Group> jListToTake = new JXList<>(listModelToTake);
    CustomList<Group> availableGroups;
    CustomList<Group> selectableGroups;

    public SelectGroupsView(User user) {
        student = (Student) user;
        setLayout(new GridBagLayout());

        JButton btnYes = new JButton("Añadir materias");
        btnYes.addActionListener(l -> {
            for (int i = 0; i < listModelToTake.size(); i++) {
                student.assignGroup(listModelToTake.getElementAt(i));
            }

            this.setEnabled(false);
        });

        table.setRowHeight(50);

        dtm.setColumnIdentifiers(new String[]{
                "Grupo", "Materia", "Semestre", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(175);
        table.getColumnModel().getColumn(2).setPreferredWidth(35);

        availableGroups = Group.groups.filter(g -> student.canTake(g.materia) && g.hasSpace());
        selectableGroups = availableGroups;

        comboModelMaterias.addElement(null);
        Materia.materias.filter(m -> student.canTake(m)).forEach(comboModelMaterias::addElement);

        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        for (int i = 3; i < 8; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        fillData();
        btnYes.setEnabled(availableGroups.any());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                handleJoinGroup(e);
            }
        });

        comboFilterMaterias.addItemListener(l -> {
            updateFilters();
            fillData();
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
        scrollPane1.setPreferredSize(new Dimension(350, 125));
        add(scrollPane1, gbc);

        gbc.gridx++;
        add(btnYes, gbc);
    }

    private void updateFilters() {
        selectableGroups = availableGroups.filter(g -> {
            Materia materia = (Materia) comboFilterMaterias.getSelectedItem();

            for (int i = 0; i < listModelToTake.size(); i++) {
                Group toCompare = listModelToTake.getElementAt(i);
                if (toCompare.materia.codeName.equals(g.materia.codeName))
                    return false;
            }

            return materia == null || materia.codeName.equals(g.materia.codeName);
        });
    }


    private void fillData() {
        dtm.setRowCount(0);
        Object[][] data = new Object[selectableGroups.size][9];

        for (int i = 0; i < selectableGroups.size; i++) {
            Group group = selectableGroups.get(i);

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

            if (!group.hasSpace()) {
                JOptionPane.showMessageDialog(this, "¡No queda espacio en este grupo!", "Sin espacio!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CustomList<Group> toCheck = jListToCustomList();

            if (isHorarioColliding(toCheck, group)) {
                JOptionPane.showMessageDialog(this, "¡Hay horarios superpuestos!", "Colisión!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isGroupColliding(toCheck, group)) {
                JOptionPane.showMessageDialog(this, "¡Ya estás en un grupo así!", "Colisión!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            listModelToTake.addElement(group);
            updateFilters();
            fillData();
        }
    }

    public boolean isHorarioColliding(CustomList<Group> groups, Group toCheck) {
        return groups.anyMatch(g -> g.horario.time.equals(toCheck.horario.time));
    }

    public boolean isGroupColliding(CustomList<Group> groups, Group toCheck) {
        return groups.anyMatch(g ->
                g.name.equals(toCheck.name) ||
                        g.materia.codeName.equals(toCheck.materia.codeName)
        );
    }

    public CustomList<Group> jListToCustomList() {
        CustomList<Group> groups = new CustomList<>();

        for (int i = 0; i < listModelToTake.size(); i++)
            groups.add(listModelToTake.getElementAt(i));

        return groups;
    }
}
