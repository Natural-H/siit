package com.application.models.users;

import com.application.models.materia.Materia;

import java.io.Serializable;
import java.util.HashMap;

public class Advance implements Serializable {
    public static HashMap<Semestre, Advance> advanceHashMap = new HashMap<>();
    public Materia[] materias;

    public enum Semestre {
        SEMESTRE1,
        SEMESTRE2,
        SEMESTRE3;

        public int toInt() {
            switch (this) {
                case SEMESTRE1:
                    return 1;
                case SEMESTRE2:
                    return 2;
                case SEMESTRE3:
                    return 3;
                default:
                    return -1;
            }
        }

        public static Semestre parseInt(int i) {
            switch (i) {
                case 1:
                    return SEMESTRE1;
                case 2:
                    return SEMESTRE2;
                case 3:
                    return SEMESTRE3;
                default:
                    return null;
            }
        }
    }
}
