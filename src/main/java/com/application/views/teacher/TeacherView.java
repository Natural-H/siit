package com.application.views.teacher;

import com.application.models.users.User;
import com.application.views.AllGroupsView;
import com.utils.swing.xtabbedpane.AbstractTabRenderer;
import com.utils.swing.xtabbedpane.JXTabbedPane;

import javax.swing.*;
import java.awt.*;

public class TeacherView extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JXTabbedPane tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);

    public TeacherView(User user) {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        AbstractTabRenderer renderer = (AbstractTabRenderer)tabbedPane.getTabRenderer();
        renderer.setPrototypeText("This text is a prototype");
        renderer.setHorizontalTextAlignment(SwingConstants.LEADING);

        tabbedPane.addTab("Grupos cargados", new AllGroupsView());
        tabbedPane.addTab("Registrar nuevo grupo", new RegisterGroupView(user));
        tabbedPane.addTab("Mis grupos", new MyGroupsView(user));

        add(tabbedPane, gbc);
    }
}
