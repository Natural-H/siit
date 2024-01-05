package com.application.models.materia;

import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.utils.CustomList;

import java.io.Serializable;

public class Group implements Serializable {
    public static CustomList<Group> groups = new CustomList<>();
    public CustomList<Student> students = new CustomList<>();

    public static int fixedId = 0;

    public int id;
    public String name;
    public Teacher teacher;
    public int semestre;
    public Materia materia;
    public Horario horario;
    public int maxSize;

    public Group(String name, Teacher teacher, Materia materia, Horario horario) {
        this.id = ++fixedId;
        this.name = name;
        this.teacher = teacher;
        this.semestre = materia.semestre;
        this.materia = materia;
        this.horario = horario;
        this.maxSize = 30;
    }

    public static boolean checkCollision(Group toCheck) {
        return Group.groups.anyMatch(group -> {
                    if (groups.anyMatch(g -> g.name.equals(toCheck.name) &&
                            g.horario.time.equals(toCheck.horario.time))) {
                        return true;
                    }

                    return toCheck.teacher.groups.anyMatch(g -> g.horario.time.equals(toCheck.horario.time));
                }
        );
    }

    public static String[] groupNames = {
            "1Y1",
            "1Y2",
            "1Y3",
            "1Y4",
            "2Y1",
            "2Y2",
            "2Y3",
            "2Y4",
            "3Y1",
            "3Y2",
            "3Y3",
            "3Y4",
    };

    @Override
    public String toString() {
        return String.format("%s - %s", name, materia.name);
    }
}
