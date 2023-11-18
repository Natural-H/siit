package com.application.models;

import java.util.HashMap;
import java.util.Map;

import com.application.models.MateriaFactory.Semestre;
import com.utils.CustomList;

public class Student extends User {
    public CustomList<AssignedMateria> materias;
    public AssignedMateria[] doing;
    public int carga;
    public int semestre;

    public Map<String, Double[]> grades = new HashMap<>();

    public Student(long id, String name, String password, Roles rol, int semestre) {
        super(id, name, password, rol);
        this.semestre = semestre;
        assignMaterias();
    }

    public void assignMaterias() {
        for (Semestre semestre : MateriaFactory.Semestre.values()) {
            for (Materia materia : Materia.factory.materias.get(semestre)) {
                materias.add(new AssignedMateria(materia.name, materia.credits, materia.semestre));
            }
        }
    }
}
