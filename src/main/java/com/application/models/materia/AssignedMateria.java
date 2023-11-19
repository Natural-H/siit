package com.application.models.materia;

import com.application.models.users.Teacher;

public class AssignedMateria {
    public Materia materia;
    public Teacher teacher;
    public Double[] grades;
    public State state;

    public enum State {
        APPROVED,
        FAILED,
        IN_COURSE,
        NOT_COURSED
    }
}
