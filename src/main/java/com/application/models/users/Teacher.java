package com.application.models.users;

import com.application.models.materia.Group;
import com.utils.CustomList;

import java.io.Serializable;

public class Teacher extends User {
    public CustomList<Group> groups = new CustomList<>();

    public Teacher(String name, String password) {
        super(name, password, Roles.Teacher);
    }

    public Teacher(long id, String name, String password) {
        super(id, name, password, Roles.Teacher);
    }

    public static int count() {
        return User.users.filter(u -> u.getRol().equals(Roles.Teacher)).size;
    }
}
