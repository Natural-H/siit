package com.application.models.materia;

import com.utils.CustomList;

import java.io.Serializable;

public class Materia implements Serializable {
    public static CustomList<Materia> materias = new CustomList<>();
    public Materia[] dependencies;
    public String codeName;
    public String name;
    public int credits;
    public int semestre;

    public Materia(String codeName, String name, int credits, int semestre, Materia[] dependencies) {
        this.codeName = codeName;
        this.name = name;
        this.credits = credits;
        this.semestre = semestre;
        this.dependencies = dependencies;
        materias.add(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
