package com.application;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Advance;
import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.application.views.LoginView;
import com.formdev.flatlaf.IntelliJTheme;
import com.utils.CustomList;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        CustomList<Integer> integers = new CustomList<>();
        integers.add(1);
        integers.add(2);
        integers.add(2);
        integers.add(3);

        integers.forEach(System.out::println);
        System.out.println(integers.findFirstValue(val -> val == 3));
        System.out.println(Arrays.toString(integers.toArray()));
        System.out.println(integers.aggregate(5, (a, next) -> a * next));
        System.out.println(integers.aggregate((a, next) -> a * next));
        System.out.println(Arrays.toString(integers.findAllValues(value -> value == 2)));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).toArray()));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).filter(value -> value == 4).toArray()));

        integers.clear();

        System.out.println(Arrays.toString(integers.toArray()));

        loadData();
        IntelliJTheme.setup(App.class.getResourceAsStream(
                "/themes/nord.theme.json"));
        SwingUtilities.invokeLater(App::showUI);
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
//        loadAdvancesAndMaterias();
//        loadUsers();
//        loadGroups();

        File advanceFile = new File("advance.bin");
        File materias = new File("materias.bin");
        File users = new File("users.bin");
        File groups = new File("groups.bin");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(advanceFile.toPath())
        )) {
            outputStream.writeObject(Advance.advanceHashMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(materias.toPath())
        )) {
            outputStream.writeObject(Materia.materias);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(users.toPath())
        )) {
            outputStream.writeObject(User.users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(groups.toPath())
        )) {
            outputStream.writeObject(Group.groups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try (ObjectInputStream inputStream = new ObjectInputStream(
//                Files.newInputStream(advanceFile.toPath())
//        )) {
//            Advance.advanceHashMap = (HashMap<Advance.Semestre, Advance>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try (ObjectInputStream inputStream = new ObjectInputStream(
//                Files.newInputStream(users.toPath())
//        )) {
//            User.users = (CustomList<User>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try (ObjectInputStream inputStream = new ObjectInputStream(
//                Files.newInputStream(groups.toPath())
//        )) {
//            Group.groups = (CustomList<Group>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void showUI() {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new LoginView());
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    private static void loadUsers() {
        User.users.add(new Student(0, "a", "a", User.Roles.Student, 1));
        User.users.add(new Teacher(1, "Some teacher", "b", User.Roles.Teacher));
    }


    private static void loadGroups() {
        Group.groups.add(new Group(0, Group.groupNames[0],
                (Teacher) User.users
                        .findFirstValue(t -> t.getId() == 1), 1,
                Materia.materias
                        .findFirstValue(m -> m.codeName.equals("CodeName1")),
                new Horario() {
                    {
                        this.days = new String[]{Days[0], Days[1], Days[2], "", Days[4], ""};
                        this.horario = Horarios[0];
                        this.places = new String[]{"SC7", "SC7", "SC7", "SC7", "SC7", ""};
                    }
                }));

        Group.groups.add(new Group(1, Group.groupNames[8],
                (Teacher) User.users
                        .findFirstValue(t -> t.getId() == 1), 1,
                Materia.materias
                        .findFirstValue(m -> m.codeName.equals("CodeName7")),
                new Horario() {
                    {
                        this.days = new String[]{Days[0], Days[1], Days[2], "", Days[4], ""};
                        this.horario = Horarios[0];
                        this.places = new String[]{"SC7", "SC7", "SC7", "SC7", "SC7", ""};
                    }
                }));

        Group.groups.forEach(group -> User.users.filter(
                        user -> user.getRol().equals(User.Roles.Teacher))
                .forEach(teacher -> {
                    if (group.teacher.getId() == teacher.getId())
                        ((Teacher) teacher).groups.add(group);
                }));
    }

    private static void loadAdvancesAndMaterias() {
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE1, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName1", "Fundamentos de Programación", 5, 1, null),
                        new Materia("CodeName2", "Cálculo Diferencial", 5, 1, null),
                        new Materia("CodeName3", "Ética", 4, 1, null),
                };
            }
        });
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE2, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName4", "Programación Orientada a Objetos", 5, 2,
                                new Materia[]{
                                        Materia.materias.findFirstValue(
                                                value -> value.codeName.equals("CodeName1")
                                        )
                                }),
                        new Materia("CodeName5", "Cálculo Integral", 5, 2,
                                new Materia[]{
                                        Materia.materias.findFirstValue(
                                                value -> value.codeName.equals("CodeName2")
                                        )
                                }),
                        new Materia("CodeName6", "Química", 4, 2, null),
                };
            }
        });
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE3, new Advance() {
            {
                materias = new Materia[]{
                        new Materia("CodeName7", "Estructura de Datos", 5, 3,
                                new Materia[]{
                                        Materia.materias.findFirstValue(
                                                value -> value.codeName.equals("CodeName4")
                                        )
                                }),
                        new Materia("CodeName8", "Cálculo Vectorial", 5, 3,
                                new Materia[]{
                                        Materia.materias.findFirstValue(
                                                value -> value.codeName.equals("CodeName5")
                                        )
                                }),
                        new Materia("CodeName9", "Cultura Empresarial", 4, 3, null),
                };
            }
        });
    }

}
