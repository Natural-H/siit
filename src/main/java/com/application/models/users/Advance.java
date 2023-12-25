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
        SEMESTRE3
    }
}
