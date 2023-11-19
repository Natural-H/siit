package com.application.models.materia;

import com.application.models.users.Student;
import com.application.models.users.Teacher;
import com.utils.CustomList;

public class Group {
    public static CustomList<Group> groups = new CustomList<>();
    public CustomList<Student> students = new CustomList<>();
    public int id;
    public String name;
    public Teacher teacher;
    public int semestre;
    public Materia materia;
    public Horario horario;

    public Group(int id, String name, Teacher teacher, int semestre, Materia materia, Horario horario) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.semestre = semestre;
        this.materia = materia;
        this.horario = horario;
    }
}
