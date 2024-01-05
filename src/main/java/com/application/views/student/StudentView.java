package com.application.views.student;

import javax.swing.*;
import java.awt.*;

import com.application.models.users.User;
import com.application.views.student.reinscripciones.ReinscripcionesView;
import com.utils.swing.xtabbedpane.AbstractTabRenderer;
import com.utils.swing.xtabbedpane.JXTabbedPane;

public class StudentView extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JXTabbedPane tabbedPane = new JXTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);

    public StudentView(User user) {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        AbstractTabRenderer renderer = (AbstractTabRenderer)tabbedPane.getTabRenderer();
        renderer.setPrototypeText("This text is a prototype");
        renderer.setHorizontalTextAlignment(SwingConstants.LEADING);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        tabbedPane.addTab("Información Personal", new PersonalInfoView(user));
        tabbedPane.addTab("Horarios", new HorarioView(user));
        tabbedPane.addTab("Calificaciones Parciales", new JPanel());
        tabbedPane.addTab("Avance Reticular", new MateriasView(user));
        tabbedPane.addTab("Kárdex", new HistoryView(user));
        tabbedPane.addTab("Reinscripciones", new ReinscripcionesView(user));

        add(tabbedPane, gbc);
    }
}
