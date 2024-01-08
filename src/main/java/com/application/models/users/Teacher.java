package com.application.models.users;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.views.student.HorarioView;
import com.utils.CustomList;

import java.io.Serializable;

public class Teacher extends User {
    public CustomList<Group> groups = new CustomList<>();

    public Teacher(String name, String password) {
        super(name, password, Roles.Teacher);
    }

    public Teacher(long id, String name, String password) {
        super(id, name, password, Roles.Teacher);
    }

    public static int count() {
        return User.users.filter(u -> u.getRol().equals(Roles.Teacher)).size;
    }

    public boolean isHorarioColliding(Group toCheck) {
//        return groups.anyMatch(g -> g.horario.time.equals(toCheck.horario.time)) ||
//                Horario.checkCollision(toCheck.horario);
        return Horario.checkCollision(toCheck.horario);
    }

    public boolean isGroupColliding(Group toCheck) {
//        return groups.anyMatch(g ->
//                g.name.equals(toCheck.name) ||
//                        g.materia.codeName.equals(toCheck.materia.codeName)
//        ) || Group.checkCollision(toCheck);
        return Group.checkCollision(toCheck);
    }

    public void gradeStudent(Student student, Materia materia, Double[] grades) {
        AssignedMateria context = student.assigned.find(a -> a.materia.codeName.equals(materia.codeName));
        System.arraycopy(grades, 0, context.grades, 0, grades.length);
    }
}
