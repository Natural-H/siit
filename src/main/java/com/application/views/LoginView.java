package com.application.views;

import com.application.models.users.User;
import com.application.views.admin.AdminView;
import com.application.views.student.StudentView;
import com.application.views.teacher.TeacherView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class LoginView extends JPanel {
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);

    public LoginView() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Inicio de sesión")));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        add(new JLabel("Tarjeta o Control"), gbc);
        gbc.gridx++;
        add(txtUser, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Contraseña"), gbc);
        gbc.gridx++;
        add(txtPassword, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JButton("Cerrar") {
            {
                addActionListener(l -> SwingUtilities.getWindowAncestor(LoginView.this).dispose());
            }
        }, gbc);

        gbc.gridx++;
        add(new JButton("Iniciar Sesión") {
            {
                addActionListener(e -> login());
            }
        }, gbc);
    }

    private void login() {
        User user = User.users.find(usr -> usr.getId() == Integer.parseInt(txtUser.getText()));
        if (Arrays.equals(user.getPassword().toCharArray(), txtPassword.getPassword())) {
            SwingUtilities.getWindowAncestor(this).dispose();
            JFrame frame = buildFrame();

            switch (user.getRol()) {
                case Student:
                    frame.setContentPane(new StudentView(user));
                    break;
                case Teacher:
                    frame.setContentPane(new TeacherView(user));
                    break;
                case Admin:
                    frame.setContentPane(new AdminView(user));
                    break;
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    private static JFrame buildFrame() {
        JFrame frame = new JFrame("App");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                JFrame frame = new JFrame("app");
                frame.setContentPane(new LoginView());
                frame.setSize(400, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        return frame;
    }
}
