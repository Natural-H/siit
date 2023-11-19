package com.application.views;

import javax.swing.*;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Advance;
import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.application.models.users.User.Roles;
import com.application.views.student.StudentView;
import com.application.views.teacher.SelectGroupsView;

import java.awt.*;
import java.util.Arrays;

import static com.application.models.users.Advance.advanceHashMap;

public class LoginView extends JPanel {
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);

    public LoginView() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Inicio de sesión")));

        loadAdvancesAndMaterias();
        loadUsers();
        loadGroups();

        GridBagConstraints gbc = new GridBagConstraints();
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
        User user = User.users.findFirstValue(usr -> usr.getName().equals(txtUser.getText()));
        if (Arrays.equals(user.getPassword().toCharArray(), txtPassword.getPassword())) {
            SwingUtilities.getWindowAncestor(this).dispose();
            JFrame frame = new JFrame("App");

            switch (user.getRol()) {
                case Student:
                    frame.setContentPane(new StudentView(user));
                    break;
                case Teacher:
                    frame.setContentPane(new SelectGroupsView(user));
                    break;
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    private void loadUsers() {
        User.users.add(new Student(0, "a", "a", Roles.Student, 1));
        User.users.add(new Teacher(1, "b", "b", Roles.Teacher));
    }


    private void loadGroups() {
        Group.groups.add(new Group(0, "1YY",
                (Teacher) User.users
                        .findFirstValue(t -> t.getId() == 1), 1,
                Materia.plainMateriaCustomList
                        .findFirstValue(m -> m.codeName.equals("CodeName1")),
                new Horario() {
                    {
                        this.days = new String[]{Days[0], Days[1], Days[2], Days[3], Days[4]};
                        this.horario = Horarios[0];
                        this.places = new String[]{"SC7", "SC7", "SC7", "SC7", "SC7"};
                    }
                }));

        Group.groups.forEach(group -> {
            User.users.filter(user -> user.getRol().equals(Roles.Teacher)).forEach(teacher -> {
                if (group.teacher.getId() == teacher.getId())
                    ((Teacher) teacher).groups.add(group);
            });
        });
    }

    private void loadAdvancesAndMaterias() {
        advanceHashMap.put(Advance.Semestre.SEMESTRE1, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName1", "Fundamentos de Programación", 5, 1, null),
                        new Materia("CodeName2", "Cálculo Diferencial", 5, 1, null),
                        new Materia("CodeName3", "Ética", 4, 1, null),
                };
            }
        });
        advanceHashMap.put(Advance.Semestre.SEMESTRE2, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName4", "Programación Orientada a Objetos", 5, 2,
                                new Materia[]{
                                        Materia.plainMateriaCustomList.findFirstValue(
                                                value -> value.codeName.equals("CodeName1")
                                        )
                                }),
                        new Materia("CodeName5", "Cálculo Integral", 5, 2,
                                new Materia[]{
                                        Materia.plainMateriaCustomList.findFirstValue(
                                                value -> value.codeName.equals("CodeName2")
                                        )
                                }),
                        new Materia("CodeName6", "Química", 4, 2, null),
                };
            }
        });
        advanceHashMap.put(Advance.Semestre.SEMESTRE3, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName7", "Estructura de Datos", 5, 3,
                                new Materia[]{
                                        Materia.plainMateriaCustomList.findFirstValue(
                                                value -> value.codeName.equals("CodeName4")
                                        )
                                }),
                        new Materia("CodeName8", "Cálculo Vectorial", 5, 3,
                                new Materia[]{
                                        Materia.plainMateriaCustomList.findFirstValue(
                                                value -> value.codeName.equals("CodeName5")
                                        )
                                }),
                        new Materia("CodeName9", "Cultura Empresarial", 4, 3, null),
                };
            }
        });
    }
}
