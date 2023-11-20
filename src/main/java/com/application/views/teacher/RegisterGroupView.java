package com.application.views.teacher;

import com.application.models.users.Teacher;
import com.application.models.users.User;

import javax.swing.*;
import java.awt.*;

public class RegisterGroupView extends JPanel {
    private Teacher context;
    private GridBagConstraints gbc = new GridBagConstraints();
    public RegisterGroupView(User user) {
        this.context = (Teacher) user;
        setLayout(new GridBagLayout());


    }
}
