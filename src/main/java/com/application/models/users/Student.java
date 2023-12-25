package com.application.models.users;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.AssignedMateria.State;
import com.application.models.materia.Group;
import com.application.models.materia.Materia;
import com.utils.CustomList;

import java.io.Serializable;

public class Student extends User {
    public int semestre;
    public CustomList<Group> groups = new CustomList<>();
    public CustomList<AssignedMateria> assigned = new CustomList<>();
    public CustomList<AssignedMateria> history = new CustomList<>();

    public Student(long id, String name, String password, Roles rol, int semestre) {
        super(id, name, password, rol);
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

            AssignedMateria assign = history.findFirstValue(m -> m.materia.name.equals(group.materia.name));
            assign.state = State.IN_COURSE;
            assign.grades = new Double[10];
            assign.teacher = group.teacher;
            assigned.add(assign);
        }
    }

    public boolean canTake(Materia materia) {
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
