package com.application.views.student;

import javax.swing.*;
import java.awt.*;

import com.application.models.users.User;
import com.application.views.student.reinscripciones.ReinscripcionesView;

public class StudentView extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTabbedPane tabbedPane = new JTabbedPane();

    public StudentView(User user) {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        tabbedPane.addTab("Información Personal", new JPanel());
        tabbedPane.addTab("Horarios", new HorarioView(user));
        tabbedPane.addTab("Calificaciones Parciales", new JPanel());
        tabbedPane.addTab("Avance Reticular", new MateriasView(user));
        tabbedPane.addTab("Kárdex", new JPanel());
        tabbedPane.addTab("Reinscripciones", new ReinscripcionesView(user));

        add(tabbedPane, gbc);
    }
}
