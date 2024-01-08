package com.application.models.users;

public class Admin extends User {
    public Admin(String name, String password) {
        super(name, password, Roles.Admin);
    }

    public Admin(long id, String name, String password) {
        super(id, name, password, Roles.Admin);
    }
}
