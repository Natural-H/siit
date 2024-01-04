package com.application.models.materia;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public class Horario implements Serializable {
    public static String[] Hours = new String[] {
            "07:00 - 08:00",
            "08:00 - 09:00",
            "09:00 - 10:00",
            "10:00 - 11:00",
            "11:00 - 12:00",
            "12:00 - 13:00",
            "13:00 - 14:00",
            "14:00 - 15:00",
            "15:00 - 16:00",
            "16:00 - 17:00",
            "17:00 - 18:00",
            "18:00 - 19:00",
            "19:00 - 20:00",
            "20:00 - 21:00",
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
    public String time = "";

    public static boolean checkCollision(Horario horario) {
        AtomicReference<Boolean> found = new AtomicReference<>(Boolean.FALSE);

        Group.groups.forEach(g -> {
                    if (g.horario.time.equals(horario.time)) {
                        for (int i = 0; i < horario.days.length; i++) {
                            if ((!horario.days[i].isEmpty() && !horario.places[i].isEmpty()) && g.horario.days[i].equals(horario.days[i]) && g.horario.places[i].equals(horario.places[i])) {
                                found.set(Boolean.TRUE);
                                return;
                            }
                        }
                    }
                }
        );

        return found.get();
    }
}
