package com.application.views.admin;

import com.application.App;
import com.application.Environment;
import com.application.models.materia.Group;
import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.application.models.users.User;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class AdminView extends JPanel {
    private final JComboBox<Environment.State> stateJComboBox = new JComboBox<>(Environment.State.values());
    private final JCheckBox useDefaultsCheck = new JCheckBox("Generar nuevos valores al iniciar", App.environment.loadDefaults);
    private final JButton btnChangeTime = new JButton("Cambiar tiempo");

    public AdminView(User user) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Controladores globales")
        ));

        stateJComboBox.setSelectedItem(App.environment.state);

        btnChangeTime.addActionListener(l -> {
            if (JOptionPane.showConfirmDialog(this,
                    "¿Seguro de cambiarlo?",
                    "Confirmación de cambio",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
                return;

            Environment.State selectedState = (Environment.State) Objects.requireNonNull(stateJComboBox.getSelectedItem());

            switch (selectedState) {
                case MidSemester:
                    if (App.environment.state.equals(Environment.State.MidSemester)) {
                        handleFromVacation();
                        handleFromRegistering();
                    } else if (App.environment.state.equals(Environment.State.Vacation)) {
                        handleFromRegistering();
                    }

                    handleFromMid();
                    break;
                case Vacation:
                    if (App.environment.state.equals(Environment.State.Vacation)) {
                        handleFromRegistering();
                        handleFromMid();
                    } else if (App.environment.state.equals(Environment.State.Registering)) {
                        handleFromMid();
                    }

                    handleFromVacation();
                    break;
                case Registering:
                    if (App.environment.state.equals(Environment.State.Registering)) {
                        handleFromMid();
                        handleFromVacation();
                    } else if (App.environment.state.equals(Environment.State.MidSemester)) {
                        handleFromVacation();
                    }

                    handleFromRegistering();
                    break;
            }

            App.environment.state = selectedState;
            App.writeEnvVars();
        });

        useDefaultsCheck.addActionListener(l -> {
            App.environment.loadDefaults = useDefaultsCheck.isSelected();
            App.writeEnvVars();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(new JLabel("Variables de estado") {
            {
                setFont(getFont().deriveFont(22f));
            }
        }, gbc);

        gbc.gridy++;
        add(new JLabel("Estado actual"), gbc);
        gbc.gridx++;
        add(stateJComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy++;
        add(useDefaultsCheck, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        add(btnChangeTime, gbc);
    }

    private static void handleFromRegistering() {
        User.users.filter(u -> u.getRol().equals(User.Roles.Student))
                .forEach(s -> ((Student) s).semestre++);

        App.loadGroups();
        App.saveFiles();
    }

    private void handleFromVacation() {
        User.users.filter(u -> u.getRol().equals(User.Roles.Teacher))
                .forEach(t -> ((Teacher) t).groups.clear());

        App.environment.lastGroupId = Group.fixedId;
        Group.groups.clear();
        RandomizeUnassignedGrades();
        App.saveFiles();
    }

    private void RandomizeUnassignedGrades() {
        Random random = new Random();

        User.users.filter(u -> u.getRol().equals(User.Roles.Student))
                .forEach(s -> {
                    Student student = ((Student) s);

                    student.assigned.forEach(m -> {
                        if (Arrays.stream(m.grades).anyMatch(g -> g != 0))
                            return;

                        student.gradeMateria(IntStream.range(0, 10)
                                .mapToDouble(in -> random.nextInt(32) + 69.0)
                                .boxed()
                                .toArray(Double[]::new), m.materia.codeName);
                    });

                    student.registerMaterias();
                });
    }

    private void handleFromMid() {
        App.loadNewUsers();
        App.assignGroupsToStudents();
        App.saveFiles();
    }
}
