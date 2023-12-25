package com.application.models.materia;

import java.io.Serializable;

public class Horario implements Serializable {
    public static String[] Horarios = new String[] {
            "07:00 - 08:00",
            "08:00 - 09:00",
            "09:00 - 10:00",
            "10:00 - 11:00",
            "11:00 - 12:00",
            "12:00 - 13:00",
    };

    public static String[] Days = new String[] {
            "Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado"
    };

    public static String[] Places = new String[] {
            "SC1",
            "SC2",
            "SC3",
            "SC4",
            "SC5",
            "SC6",
            "SC7",
            "SC8",
            "SC9",
            "SC10",
            "SC11",
            "SC12",
            "LC1",
            "LC2",
            "LC3",
            "LC4",
    };

    public String[] days = new String[6];
    public String[] places = new String[6];
    public String horario = "";
}
