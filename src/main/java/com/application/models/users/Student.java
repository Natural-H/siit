package com.application.models.users;

import com.application.models.materia.AssignedMateria;
import com.application.models.materia.Group;
import com.utils.CustomList;

public class Student extends User {
    public int semestre;
    public CustomList<Group> groups = new CustomList<>();
    public CustomList<AssignedMateria> assigned = new CustomList<>();
    public CustomList<AssignedMateria> assignableMaterias = new CustomList<>();

    public Student(long id, String name, String password, Roles rol, int semestre) {
        super(id, name, password, rol);
        this.semestre = semestre;

        for (int i = 0; i < Advance.Semestre.values().length; i++) {
            Advance advance = Advance.advanceHashMap.get(Advance.Semestre.values()[i]);
            for (int j = 0; j < advance.materias.length; j++) {
                int finalJ = j;
                assignableMaterias.add(new AssignedMateria() {
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
        for (Group group : assignedGroups) {
            groups.add(group);
            assigned.add(new AssignedMateria() {
                {
                    this.materia = group.materia;
                    this.state = State.IN_COURSE;
                    this.grades = new Double[10];
                    this.teacher = group.teacher;
                }
            });
        }
    }
}
