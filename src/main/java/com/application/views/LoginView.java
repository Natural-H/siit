package com.application.views;

import javax.swing.*;

import com.application.models.Horario;
import com.application.models.Materia;
import com.application.models.Student;
import com.application.models.Teacher;
import com.application.models.User;
import com.application.models.MateriaFactory.Semestre;
import com.application.models.User.Roles;

import java.awt.*;
import java.util.HashMap;

public class LoginView extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTextField txtUser = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);

    public LoginView() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Inicio de sesión")));

        loadUsers();
        loadMaterias();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        add(new JLabel("Tarjeta o Control"), gbc);
        gbc.gridx++;
        add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Contraseña"), gbc);
        gbc.gridx++;
        add(txtPassword, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JButton("Cerrar") {

        }, gbc);

        gbc.gridx++;
        add(new JButton("Iniciar Sesión") {
            {
                addActionListener(e -> login());
            }
        }, gbc);
    }

    private void login() {
        User user = User.users.findValue(usr -> usr.getName().equals(txtUser.getText()));
        if (user.getPassword().equals(txtPassword.getText())) {
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
            JFrame frame = new JFrame("App");
            frame.setContentPane(new StudentView(user));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    private void loadUsers() {
        User.users.add(new Student(0, "a", "a", Roles.Student, 1));
    }

    private void loadMaterias() {
        Materia.factory.materias = new HashMap<>();
        Materia.factory.materias.put(Semestre.semestre1, new Materia[] {
                new Materia(new Teacher[] {
                        new Teacher(0, "Maestro 1", "Some password", Roles.Teacher),
                        new Teacher(1, "Maestro 2", "Some password", Roles.Teacher),
                        new Teacher(2, "Maestro 3", "Some password", Roles.Teacher),
                },
                        new Horario[] {
                                new Horario() {
                                    {
                                        this.begin = "07:00";
                                        this.end = "08:00";
                                    }
                                },
                                new Horario() {
                                    {
                                        this.begin = "08:00";
                                        this.end = "09:00";
                                    }
                                },
                                new Horario() {
                                    {
                                        this.begin = "09:00";
                                        this.end = "10:00";
                                    }
                                }
                        },
                        "Fundamentos de Programación", 5, 1),
                new Materia(new Teacher[] {
                        new Teacher(0, "Maestro 1", "Some password", Roles.Teacher),
                        new Teacher(1, "Maestro 2", "Some password", Roles.Teacher),
                        new Teacher(2, "Maestro 3", "Some password", Roles.Teacher),
                },
                        new Horario[] {
                                new Horario() {
                                    {
                                        this.begin = "07:00";
                                        this.end = "08:00";
                                    }
                                },
                                new Horario() {
                                    {
                                        this.begin = "08:00";
                                        this.end = "09:00";
                                    }
                                },
                                new Horario() {
                                    {
                                        this.begin = "09:00";
                                        this.end = "10:00";
                                    }
                                }
                        },
                        "Cálculo Diferencial", 5, 1),
        });
    }
}
