package com.application.models.users;

import com.application.models.materia.Group;
import com.utils.CustomList;

import java.io.Serializable;

public class Teacher extends User {
    public CustomList<Group> groups = new CustomList<>();

    public Teacher(long id, String name, String password, Roles rol) {
        super(id, name, password, rol);
    }
}
