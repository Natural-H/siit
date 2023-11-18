package com.application.models;

public class AssignedMateria {
    public String name;
    public int credits;
    public int semestre;
    public Teacher teacher;
    public Horario horario;
    public Boolean passed;

    public AssignedMateria() {
    }

    public AssignedMateria(String name, int credits, int semestre) {
        this.name = name;
        this.credits = credits;
        this.semestre = semestre;
        this.passed = false;
    }

    public AssignedMateria(String name, int credits, int semestre, Teacher teacher, Horario horario) {
        this.name = name;
        this.credits = credits;
        this.semestre = semestre;
        this.teacher = teacher;
        this.horario = horario;
        this.passed = false;
    }
}
