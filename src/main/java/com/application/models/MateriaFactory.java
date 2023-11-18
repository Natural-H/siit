package com.application.models;

import java.util.HashMap;

public class MateriaFactory {
    public HashMap<Semestre, Materia[]> materias;

    public enum Semestre {
        semestre1,
        semestre2,
        semestre3,
    }
}
