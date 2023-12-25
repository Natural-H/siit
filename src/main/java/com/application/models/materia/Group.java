package com.application.models.materia;

import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.utils.CustomList;

import java.io.Serializable;

public class Group implements Serializable {
    public static CustomList<Group> groups = new CustomList<>();
    public CustomList<Student> students = new CustomList<>();
    public int id;
    public String name;
    public Teacher teacher;
    public int semestre;
    public Materia materia;
    public Horario horario;
    public int maxSize;

    public Group(int id, String name, Teacher teacher, int semestre, Materia materia, Horario horario) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.semestre = semestre;
        this.materia = materia;
        this.horario = horario;
        this.maxSize = 30;
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
}
