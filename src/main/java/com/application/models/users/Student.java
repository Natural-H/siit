package com.application.models.users;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.AssignedMateria.State;
import com.application.models.materia.Group;
import com.application.models.materia.Materia;
import com.utils.CustomList;

import java.util.Arrays;

public class Student extends User {
    public int semestre;
    public CustomList<Group> groups = new CustomList<>();
    public CustomList<AssignedMateria> assigned = new CustomList<>();
    public CustomList<AssignedMateria> history = new CustomList<>();

    public Student(String name, String password, int semestre) {
        super(name, password, Roles.Student);
        this.semestre = semestre;

        for (int i = 0; i < Advance.Semestre.values().length; i++) {
            Advance advance = Advance.advanceHashMap.get(Advance.Semestre.values()[i]);

            for (int j = 0; j < advance.materias.length; j++) {
                int finalJ = j;
                history.add(new AssignedMateria() {
                    {
                        this.materia = advance.materias[finalJ];
                        this.state = State.NOT_COURSED;
                    }
                });
            }
        }
    }

    public void assignMaterias(Group[] assignedGroups) {
        groups.clear();
        assigned.clear();
        for (Group group : assignedGroups) {
            groups.add(group);

            AssignedMateria assign = history.findFirstValue(m -> m.materia.codeName.equals(group.materia.codeName));
            assign.state = State.IN_COURSE;
            assign.grades = new Double[10];
            Arrays.fill(assign.grades, 0.0);

//            assign.teacher = group.teacher;
            assigned.add(assign);
        }
    }

    public void injectMaterias(Materia[] materias) {
        assigned.clear();

        for (Materia materia : materias) {
            AssignedMateria assign = history.findFirstValue(m -> m.materia.codeName.equals(materia.codeName));
            assign.state = State.IN_COURSE;
            assign.grades = new Double[10];
            Arrays.fill(assign.grades, 0.0);

            assigned.add(assign);
        }
    }

    public void gradeMateria(Double[] grades, String code) {
        assigned.findFirstValue(m -> m.materia.codeName.equals(code)).grades = grades;
    }

    public void registerMaterias() {
        assigned.forEach(a -> {
            AssignedMateria materia = history.findFirstValue(h -> h.materia.codeName.equals(a.materia.codeName));
            materia.state = Arrays.stream(materia.grades).anyMatch(d -> d >= 70) ?
                    State.APPROVED :
                    State.FAILED;
            materia.grades = a.grades;
        });
        assigned.clear();
    }

    public boolean canTake(Materia materia) {
        if (materia.semestre > (semestre + 1))
            return false;

        if (materia.dependencies == null)
            return true;

        for (Materia dependence : materia.dependencies) {
            AssignedMateria taken = history.findFirstValue(mat -> mat.materia.codeName.equals(dependence.codeName));
            if (taken.state == State.NOT_COURSED || taken.state == State.FAILED)
                return false;
        }

        return true;
    }
}
