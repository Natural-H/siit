package com.application;

import com.application.views.LoginView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.utils.CustomList;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        CustomList<Integer> integers = new CustomList<>();
        integers.add(1);
        integers.add(2);
        integers.add(2);
        integers.add(3);

        integers.forEach(System.out::println);
        System.out.println(integers.findFirstValue(val -> val == 3));
        System.out.println(Arrays.toString(integers.toArray()));
        System.out.println(Arrays.toString(integers.findAllValues(value -> value == 2)));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).toArray()));
        System.out.println(Arrays.toString(integers.map(value -> value * 2).filter(value -> value == 4).toArray()));

        integers.clear();

        System.out.println(Arrays.toString(integers.toArray()));

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
