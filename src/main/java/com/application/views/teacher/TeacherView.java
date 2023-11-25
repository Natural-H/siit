package com.application.views.teacher;

import com.application.models.users.User;
import com.application.views.AllGroupsView;

import javax.swing.*;
import java.awt.*;

public class TeacherView extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public TeacherView(User user) {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        tabbedPane.addTab("Grupos cargados", new AllGroupsView());
        tabbedPane.addTab("Registrar nuevo grupo", new RegisterGroupView(user));

        add(tabbedPane, gbc);
    }
}
