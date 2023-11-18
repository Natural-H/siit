package com.application.models;

public class Teacher extends User {
    public AssignedMateria[] materias = new AssignedMateria[5];

    public Teacher(long id, String name, String password, Roles rol) {
        super(id, name, password, rol);
    }
}
