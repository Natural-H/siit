package com.application.views.teacher;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.application.views.AllGroupsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class RegisterGroupView extends JPanel {
    private final Teacher context;
    private final DefaultComboBoxModel<String> groupsModel = new DefaultComboBoxModel<>(Group.groupNames);
    private final JComboBox<String> comboGroups = new JComboBox<>(groupsModel);
    private final JComboBox<Materia> comboMaterias = new JComboBox<>(Materia.materias.toArray());
    private JComboBox<String> comboHorarios = new JComboBox<>(Horario.Horarios);
    private final JComboBox<String>[] combosPlaces = new JComboBox[]{
            new JComboBox<>(Horario.Places),
            new JComboBox<>(Horario.Places),
            new JComboBox<>(Horario.Places),
            new JComboBox<>(Horario.Places),
            new JComboBox<>(Horario.Places),
            new JComboBox<>(Horario.Places)
    };

    private JTextField txtProfesor = new JTextField(20);
    private JTextField txtSemestre = new JTextField(5);
    private JCheckBox[] checkDays = new JCheckBox[Horario.Days.length];

    private DefaultTableModel dtm = new DefaultTableModel();
    private JTable table = new JTable(dtm);
    private JScrollPane scrollPane = new JScrollPane(table);

    public RegisterGroupView(User user) {
        this.context = (Teacher) user;
        setLayout(new GridBagLayout());

        txtProfesor.setEditable(false);
        txtProfesor.setText(context.getName());

        txtSemestre.setEditable(false);
        txtSemestre.setText(
                Integer.toString(((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).semestre)
        );

        for (int i = 0; i < checkDays.length; i++) {
            checkDays[i] = new JCheckBox(Horario.Days[i]);
            if (((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).credits >= 5) {
                checkDays[i].setSelected(true);
                checkDays[i].setEnabled(false);
            }
        }

        checkDays[5].setSelected(false);
        combosPlaces[5].setEnabled(false);

        updateGroups();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;

        add(new JLabel("Profesor"), gbc);
        gbc.gridx++;
        add(txtProfesor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Materia"), gbc);
        gbc.gridx++;
        add(comboMaterias, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Grupo"), gbc);
        gbc.gridx++;
        add(comboGroups, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Semestre"), gbc);
        gbc.gridx++;
        add(txtSemestre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Horario"), gbc);
        gbc.gridx++;
        add(comboHorarios, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Días"), gbc);

        for (JCheckBox checkDay : checkDays) {
            gbc.gridx++;
            add(checkDay, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Lugares"), gbc);

        for (JComboBox<String> stringJComboBox : combosPlaces) {
            gbc.gridx++;
            add(stringJComboBox, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JButton("Add") {
            {
                addActionListener(e -> addGroup());
            }
        }, gbc);
    }

    private void updateGroups() {
        groupsModel.removeAllElements();
        int semestre = ((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).semestre;

        for (int i = 0; i < Group.groupNames.length; i++)
            if (Group.groupNames[i].startsWith(Integer.toString(semestre)))
                groupsModel.addElement(Group.groupNames[i]);
    }

    private void addGroup() {
        Group.groups.add(new Group(Group.groups.size,
                (String) Objects.requireNonNull(comboGroups.getSelectedItem()),
                context,
                Integer.parseInt(txtSemestre.getText()),
                ((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())),
                new Horario() {
                    {
                        for (int i = 0; i < checkDays.length; i++) {
                            this.days[i] = checkDays[i].isSelected() ? Days[i] : "";
                            this.places[i] = checkDays[i].isSelected() ? (String) combosPlaces[i].getSelectedItem() : "";
                        }
                        this.horario = (String) comboHorarios.getSelectedItem();
                    }
                }));

        AllGroupsView.loadInfo();
    }
}
