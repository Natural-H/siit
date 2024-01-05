package com.application;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.materia.Materia.CodeNames;
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
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {
    public static Environment environment = new Environment();
    public static void main(String[] args) {
        CustomList<Integer> integers = new CustomList<>();
        integers.add(1);
        integers.add(2);
        integers.add(2);
        integers.add(3);

        integers.forEach(System.out::println);
        System.out.println(integers.find(val -> val == 3));
        integers.remove(2);
        System.out.println(Arrays.toString(integers.toArray(new Integer[0])));
        System.out.println(integers.aggregate(5, (a, next) -> a * next));
        System.out.println(integers.aggregate((a, next) -> a * next));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).toArray(new Integer[0])));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).filter(value -> value == 4).toArray(new Integer[0])));

        integers.clear();
        System.out.println(Arrays.toString(integers.toArray(new Integer[0])));

        environment.state = Environment.State.Registering;
        environment.loadDefaults = true;
        loadData();

        IntelliJTheme.setup(App.class.getResourceAsStream(
                "/themes/nord.theme.json"));
        SwingUtilities.invokeLater(App::showUI);
        System.out.println("Total Groups: " + Group.fixedId);
    }

    static File advanceFile = new File("advance.bin");
    static File materiasFile = new File("materias.bin");
    static File usersFile = new File("users.bin");
    static File groupsFile = new File("groups.bin");


    private static void loadData() {
        if (Stream.of(advanceFile, materiasFile, usersFile, groupsFile)
                .anyMatch(file -> !file.exists()) || environment.loadDefaults) {
            loadAdvancesAndMaterias();
            loadUsers();
            loadGroups();
            assignGroupsToStudents();

            saveFiles();
        } else {
            readFiles();
        }
    }

    @SuppressWarnings("unchecked")
    private static void readFiles() {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                Files.newInputStream(advanceFile.toPath())
        )) {
            Advance.advanceHashMap = (HashMap<Advance.Semestre, Advance>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(
                Files.newInputStream(materiasFile.toPath())
        )) {
            Materia.materias = (CustomList<Materia>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(
                Files.newInputStream(usersFile.toPath())
        )) {
            User.users = (CustomList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(
                Files.newInputStream(groupsFile.toPath())
        )) {
            Group.groups = (CustomList<Group>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        User.fixedId = User.users.last.value.getId();
    }

    public static void saveFiles() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(advanceFile.toPath())
        )) {
            outputStream.writeObject(Advance.advanceHashMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(materiasFile.toPath())
        )) {
            outputStream.writeObject(Materia.materias);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(usersFile.toPath())
        )) {
            outputStream.writeObject(User.users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                Files.newOutputStream(groupsFile.toPath())
        )) {
            outputStream.writeObject(Group.groups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        Random random = new Random();

        for (int i = 0; i < 80; i++)
            User.users.add(new Student("Some poor soul " + i, "a", random.nextInt(Advance.Semestre.values().length) + 1));

        User.users.add(new Teacher("Some teacher 1", "b"));
        User.users.add(new Teacher("Some teacher 2", "b"));
        User.users.add(new Teacher("Some teacher 3", "b"));
        User.users.add(new Teacher("Some teacher 4", "b"));
        User.users.add(new Teacher("Some teacher 5", "b"));
        User.users.add(new Teacher("Some teacher 6", "b"));
    }

    private static void loadGroups() {
        Random rand = new Random();
        Materia[] shuffledMaterias = Materia.materias.toArray(new Materia[0]);

        for (String name : Group.groupNames) {
            for (int i = 0; i < shuffledMaterias.length; i++) {
                int swapI = rand.nextInt(shuffledMaterias.length);

                Materia temp = shuffledMaterias[swapI];
                shuffledMaterias[swapI] = shuffledMaterias[i];
                shuffledMaterias[i] = temp;
            }

            for (Materia materia : shuffledMaterias) {
                if (!name.startsWith(Integer.toString(materia.semestre)))
                    continue;

                Group g = null;
                Teacher t = null;

                do {
                    t = (Teacher) User.users
                            .filter(u -> u.getRol().equals(User.Roles.Teacher))
                            .get(rand.nextInt(Teacher.count()));

                    g = new Group(name,
                            t,
                            materia,
                            new Horario() {
                                {
                                    do {
                                        this.time = Horario.Hours[rand.nextInt(Horario.Hours.length)];

                                        if (materia.credits == 5) {
                                            this.days = new String[]{Days[0], Days[1], Days[2], Days[3], Days[4], ""};
                                        } else {
                                            this.days = new String[]{Days[0], Days[1], Days[2], Days[3], "", ""};
                                        }

                                        Arrays.fill(this.places, Places[rand.nextInt(Places.length)]);
                                        this.places[5] = "";

                                        if (materia.credits == 4)
                                            this.places[4] = "";
                                    } while (Horario.checkCollision(this));
                                }
                            });
                } while (Group.checkCollision(g));

                Group.groups.add(g);
                t.groups.add(g);
            }
        }
    }

    private static void assignGroupsToStudents() {
        Random random = new Random();

        User.users.filter(u -> u.getRol().equals(User.Roles.Student)).forEach(s -> {
            Student student = (Student) s;

            CustomList<Group> groupCustomList = Group.groups.filter(g -> g.name.startsWith(Integer.toString(student.semestre)));
            String group = groupCustomList.get(random.nextInt(groupCustomList.size)).name;

            for (int i = 1; i < student.semestre; i++) {
                Materia[] materias = Advance.advanceHashMap.get(Advance.Semestre.parseInt(i)).materias;
                student.injectMaterias(materias);

                for (Materia materia : materias) {
                    student.gradeMateria(IntStream.range(0, 10).mapToDouble(in ->
                            random.nextInt(32) + 69.0).boxed().toArray(Double[]::new), materia.codeName);
                }

                student.registerMaterias();
            }

            Group.groups.filter(g -> g.name.equals(group) && student.canTake(g.materia)).forEach(student::assignMateria);
            student.history.filter(materia -> materia.state.equals(AssignedMateria.State.FAILED))
                            .forEach(m -> Group.groups.filter(g -> g.materia.codeName.equals(m.materia.codeName))
                                    .forEach(available -> {
                                        if (!student.isGroupColliding(available) && !student.isHorarioColliding(available))
                                            student.assignMateria(available);
                                    }));
        });
    }

    private static void loadAdvancesAndMaterias() {
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE1, new Advance() {
            {
                materias = new Materia[]{
                        new Materia(CodeNames.FundProg.codeName, "Fundamentos de Programación", 5, 1, null),
                        new Materia(CodeNames.CalcDiff.codeName, "Cálculo Diferencial", 5, 1, null),
                        new Materia(CodeNames.Etic.codeName, "Ética", 4, 1, null),
                };
            }
        });
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE2, new Advance() {
            {
                materias = new Materia[]{
                        new Materia(CodeNames.POO.codeName, "Programación Orientada a Objetos", 5, 2,
                                new Materia[]{
                                        Materia.materias.find(
                                                value -> value.codeName.equals(CodeNames.FundProg.codeName)
                                        )
                                }),
                        new Materia(CodeNames.CalcInt.codeName, "Cálculo Integral", 5, 2,
                                new Materia[]{
                                        Materia.materias.find(
                                                value -> value.codeName.equals(CodeNames.CalcDiff.codeName)
                                        )
                                }),
                        new Materia(CodeNames.Chemistry.codeName, "Química", 4, 2, null),
                };
            }
        });
        Advance.advanceHashMap.put(Advance.Semestre.SEMESTRE3, new Advance() {
            {
                materias = new Materia[]{
                        new Materia(CodeNames.DataStruct.codeName, "Estructura de Datos", 5, 3,
                                new Materia[]{
                                        Materia.materias.find(
                                                value -> value.codeName.equals(CodeNames.POO.codeName)
                                        )
                                }),
                        new Materia(CodeNames.CalcVect.codeName, "Cálculo Vectorial", 5, 3,
                                new Materia[]{
                                        Materia.materias.find(
                                                value -> value.codeName.equals(CodeNames.CalcInt.codeName)
                                        )
                                }),
                        new Materia(CodeNames.CultEmpr.codeName, "Cultura Empresarial", 4, 3, null),
                };
            }
        });
    }

}
