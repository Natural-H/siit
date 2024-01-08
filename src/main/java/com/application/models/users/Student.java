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
                        this.grades = new Double[10];
                        Arrays.fill(grades, 0.0);
                    }
                });
            }
        }
    }

    public void assignGroup(Group group) {
//        groups.clear();
//        assigned.clear();
//        for (Group group : assignedGroups) {
            if (!group.tryAdd(this)) return;
            groups.add(group);

            AssignedMateria assign = history.find(m -> m.materia.codeName.equals(group.materia.codeName));
            assign.state = State.IN_COURSE;
            assign.grades = new Double[10];
            Arrays.fill(assign.grades, 0.0);

//            assign.teacher = group.teacher;
            assigned.add(assign);
//        }
    }

    public void injectMaterias(Materia[] materias) {
        for (Materia materia : materias) {
            if (!canTake(materia))
                continue;

            AssignedMateria assign = history.find(m -> m.materia.codeName.equals(materia.codeName));
            assign.state = State.IN_COURSE;
            assign.grades = new Double[10];
            Arrays.fill(assign.grades, 0.0);

            assigned.add(assign);
        }
    }

    public void gradeMateria(Double[] grades, String code) {
        AssignedMateria found = assigned.find(m -> m.materia.codeName.equals(code));
        if (found == null)
            return;

        System.arraycopy(grades, 0, found.grades, 0, grades.length);
    }

    public void registerMaterias() {
        assigned.forEach(a -> {
            AssignedMateria materia = history.find(h -> h.materia.codeName.equals(a.materia.codeName));
            materia.state = Arrays.stream(materia.grades).allMatch(d -> d >= 70) ?
                    State.APPROVED :
                    State.FAILED;
            materia.grades = a.grades;
        });
        assigned.clear();
        groups.clear();
    }

    public boolean canTake(Materia materia) {
        if (materia.semestre > (semestre + 1))
            return false;

        if (materia.dependencies != null) {
            for (Materia dependence : materia.dependencies) {
                AssignedMateria taken = history.find(m -> m.materia.codeName.equals(dependence.codeName));
                if (taken.state != State.APPROVED)
                    return false;
            }
        }

        AssignedMateria.State actualState = history.find(m -> m.materia.codeName.equals(materia.codeName)).state;
        return actualState.equals(State.FAILED) || actualState.equals(State.NOT_COURSED);
    }

    public boolean isHorarioColliding(Group toCheck) {
        return groups.anyMatch(g -> g.horario.time.equals(toCheck.horario.time));
    }

    public boolean isGroupColliding(Group toCheck) {
        return groups.anyMatch(g ->
                g.name.equals(toCheck.name) ||
                g.materia.codeName.equals(toCheck.materia.codeName)
        );
    }

    @Override
    public String toString() {
        return getName();
    }
}
