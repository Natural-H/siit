package com.application.views.student;

import com.application.models.materia.AssignedMateria;
import com.application.models.users.Student;
import com.application.models.users.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class PersonalInfoView extends JPanel {
    private final User context;

    public PersonalInfoView(User user) {
        context = user;
        setLayout(new GridBagLayout());

        JLabel lbImage;
        try {
            lbImage = new JLabel(new ImageIcon(
                    scaleImage(ImageIO.read(new File("images/4086679.png")), 150, 150, Color.WHITE)
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lbImage.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Fotografía"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        final JPanel[] infoPanel = new JPanel[1];
        if (context instanceof Student)
            infoPanel[0] = new StudentInfo((Student) context);
        else
            infoPanel[0] = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                PersonalInfoView.this.remove(infoPanel[0]);

                if (context instanceof Student)
                    infoPanel[0] = new StudentInfo((Student) context);
                else
                    infoPanel[0] = new JPanel();

                PersonalInfoView.this.add(infoPanel[0], gbc);
                super.componentShown(e);
            }
        });

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        add(lbImage, gbc);
        gbc.gridx++;

        gbc.gridheight = 1;
        add(new JLabel("Número de control: "), gbc);
        gbc.gridx++;
        add(new JTextField(Long.toString(context.getId()), 10) {
            {
                setEnabled(false);
            }
        }, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        add(new JLabel("Nombre: "), gbc);
        gbc.gridx++;
        add(new JTextField(context.getName(), 25) {
            {
                setEnabled(false);
            }
        }, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(infoPanel[0], gbc);
    }

    public BufferedImage scaleImage(BufferedImage img, int width, int height,
                                    Color background) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setBackground(background);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }
}

class StudentInfo extends JPanel {
    Student context;

    public StudentInfo(Student context) {
        this.context = context;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Student")
        ));

        AtomicReference<Double> calif = new AtomicReference<>((double) 0);

        context.history.filter(mat -> mat.state == AssignedMateria.State.APPROVED)
                .map(mat -> mat.grades)
                .forEach(g -> calif.updateAndGet(v -> v + Arrays.stream(g).mapToDouble(Double::doubleValue).sum()));

        double val = calif.get() / ((double)context.history.filter(mat -> mat.state == AssignedMateria.State.APPROVED).size * 10);
        val = Double.isNaN(val) || Double.isInfinite(val) ? 0 : val;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        add(new JLabel("Semestre: " + context.semestre), gbc);
        gbc.gridy++;
        add(new JLabel("Promedio: " + val), gbc);
    }
}
