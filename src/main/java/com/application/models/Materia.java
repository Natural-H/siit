package com.application.models;

public class Materia {
    public static MateriaFactory factory = new MateriaFactory();

    public Teacher[] profesores;
    public Horario[] horarios;
    public String name;
    public int credits;
    public int semestre;

    public Materia(Teacher[] profesores, Horario[] horarios, String name, int credits, int semestre) {
        this.profesores = profesores;
        this.horarios = horarios;
        this.name = name;
        this.credits = credits;
        this.semestre = semestre;
    }
}
