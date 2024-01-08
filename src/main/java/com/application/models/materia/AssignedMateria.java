package com.application.models.materia;

import java.io.Serializable;

public class AssignedMateria implements Serializable {
    public Materia materia;
    public Double[] grades;
    public State state;

    public enum State {
        APPROVED,
        FAILED,
        IN_COURSE,
        NOT_COURSED;

        @Override
        public String toString() {
            switch (this) {
                case APPROVED:
                    return "Aprobado";
                case FAILED:
                    return "Reprobado";
                case IN_COURSE:
                    return "Cursando";
                case NOT_COURSED:
                    return "Sin Cursar";
                default:
                    return "";
            }
        }
    }
}
