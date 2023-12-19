package com.application.views.student;

import com.application.models.users.Student;
import com.application.models.users.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

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

        gbc.gridy++;
        gbc.gridx = 1;
        add(new JLabel("Semestre: "), gbc);
        gbc.gridx++;
        add(new JTextField(Integer.toString(((Student)user).semestre), 5) {
            {
                setEnabled(false);
            }
        }, gbc);
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
