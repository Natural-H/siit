package com.application.views.student.reinscripciones;

import com.application.App;
import com.application.Environment;
import com.application.models.users.Student;
import com.application.models.users.User;
import com.application.views.AllGroupsView;
import com.application.views.student.SelectGroupsView;

import javax.swing.*;
import java.awt.*;

public class ReinscripcionesView extends JPanel {
    private Student context;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTabbedPane tabbedPane = new JTabbedPane();

    public ReinscripcionesView(User user) {
        context = ((Student) user);
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        tabbedPane.addTab("Grupos cargados", new AllGroupsView());
        tabbedPane.addTab("Selección de Materias", new SelectGroupsView(context));

        tabbedPane.setEnabledAt(1, App.environment.state.equals(Environment.State.Registering));
        add(tabbedPane, gbc);
    }
}
