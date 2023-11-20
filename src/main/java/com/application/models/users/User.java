package com.application.models.users;

import com.utils.CustomList;

public abstract class User {
    public static CustomList<User> users = new CustomList<>();

    protected long id;
    protected String name;
    protected String password;
    protected Roles rol;

    public User(long id, String name, String password, Roles rol) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.rol = rol;
    }

    public enum Roles {
        Student,
        Teacher,
        Aspirante,
        Admin
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
