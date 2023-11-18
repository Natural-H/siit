package com.application;

import com.application.views.LoginView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.utils.CustomList;

public class App {
    public static void main(String[] args) {
        CustomList<Integer> integers = new CustomList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        integers.forEach(value -> System.out.println(value));

        System.out.println(integers.findValue(val -> val == 3));

        SwingUtilities.invokeLater(App::showUI);
    }

    private static void showUI() {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new LoginView());
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
