package com.application.models.users;

import com.application.models.materia.Group;
import com.utils.CustomList;

public class Teacher extends User {
    public CustomList<Group> groups = new CustomList<>();

    public Teacher(long id, String name, String password, Roles rol) {
        super(id, name, password, rol);
        User.users.add(this);
    }
}
