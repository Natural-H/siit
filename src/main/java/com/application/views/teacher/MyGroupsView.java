package com.application.views.teacher;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.Group;
import com.application.models.materia.Materia;
import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.utils.CustomList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Objects;

public class MyGroupsView extends JPanel {
    private Teacher context;
    private JComboBox<Group> comboGroups;
    private DefaultComboBoxModel<Student> studentDefaultListModel = new DefaultComboBoxModel<>();
    private JComboBox<Student> comboStudents = new JComboBox<>(studentDefaultListModel);
    private JLabel[] lbGrades = new JLabel[10];
    private JTextField[] txtGrades = new JTextField[10];

    public MyGroupsView(User user) {
        context = (Teacher) user;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Estudiantes y calificaciones del grupo")
        ));

        comboGroups = new JComboBox<>(context.groups.toArray(new Group[0]));

        for (int i = 0; i < txtGrades.length; i++) txtGrades[i] = new JTextField(5);
        for (int i = 0; i < lbGrades.length; i++) lbGrades[i] = new JLabel("Unidad " + (i + 1));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                updateList((Group) Objects.requireNonNull(comboGroups.getSelectedItem()));
            }
        });

        comboGroups.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                updateList((Group) itemEvent.getItem());
        });
        comboStudents.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED)
                updateGrades((Student) l.getItem(), ((Group) Objects.requireNonNull(comboGroups.getSelectedItem())).materia);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridwidth = 10;
        add(new JLabel("Mis grupos") {
            {
                setFont(getFont().deriveFont(22f));
            }
        }, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Grupo"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 9;
        add(comboGroups, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Estudiante"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 9;
        add(comboStudents, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 10;
        add(new JLabel("Calificaciones") {
            {
                setFont(getFont().deriveFont(18f));
            }
        }, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        for (int i = 0; i < txtGrades.length; i++) {
            add(lbGrades[i], gbc);
            gbc.gridy++;
            add(txtGrades[i], gbc);
            gbc.gridy--;
            gbc.gridx++;
        }

        gbc.gridwidth = 10;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy += 2;
        add(new JButton("Guardar Calificaciones") {
            {
                addActionListener(l -> {
                    Group group = (Group) comboGroups.getSelectedItem();
                    assert group != null;

                    Double[] grades = new Double[txtGrades.length];
                    for (int i = 0; i < txtGrades.length; i++)
                        grades[i] = Double.parseDouble(txtGrades[i].getText().isEmpty() ? "0.0" : txtGrades[i].getText());

                    if (Arrays.stream(grades).anyMatch(d -> Double.isNaN(d))) {
                        JOptionPane.showMessageDialog(this, "¡Una calificación es inválida!", "Fallo de formato", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    context.gradeStudent((Student) Objects.requireNonNull(comboStudents.getSelectedItem()), group.materia, grades);
                });
            }
        }, gbc);
    }

    private void updateGrades(Student student, Materia materia) {
        if (student == null)
            return;

        AssignedMateria assignedMateria = student.assigned
                .find(a -> a.materia.codeName.equals(materia.codeName));

        for (int i = 0; i < assignedMateria.grades.length; i++)
            txtGrades[i].setText(Double.toString(assignedMateria.grades[i]));
    }

    public void updateList(Group selected) {
        studentDefaultListModel.removeAllElements();

        CustomList<Student> sortedStudents = selected.students.getCopy();
        sortedStudents.sort((s1, s2) -> s1.getName().compareTo(s2.getName()) < 0);

        sortedStudents.forEach(s -> studentDefaultListModel.addElement(s));
        updateGrades(sortedStudents.get(0), selected.materia);
    }
}
