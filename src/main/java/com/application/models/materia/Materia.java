package com.application.models.materia;

import com.utils.CustomList;

public class Materia {
    public static CustomList<Materia> plainMateriaCustomList = new CustomList<>();
    public Materia[] dependencies;
    public String codeName;
    public String nombre;
    public int credits;
    public int semestre;

    public Materia(String codeName, String nombre, int credits, int semestre, Materia[] dependencies) {
        this.codeName = codeName;
        this.nombre = nombre;
        this.credits = credits;
        this.semestre = semestre;
        this.dependencies = dependencies;
        plainMateriaCustomList.add(this);
    }
}
