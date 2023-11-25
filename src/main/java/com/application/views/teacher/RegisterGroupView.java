package com.application.views.teacher;

import com.application.models.materia.Group;
import com.application.models.materia.Horario;
import com.application.models.materia.Materia;
import com.application.models.users.Teacher;
import com.application.models.users.User;
import com.utils.swing.MultiLineTableCellRenderer;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class RegisterGroupView extends JPanel {
    private final Teacher context;
    private final DefaultComboBoxModel<String> groupsModel = new DefaultComboBoxModel<>(Group.groupNames);
    private final JComboBox<String> comboGroups = new JComboBox<>(groupsModel);
    private final JXComboBox comboMaterias = new JXComboBox(Materia.materias.toArray());
    private final JComboBox<String> comboHorarios = new JComboBox<>(Horario.Horarios);
    private final JXComboBox[] combosPlaces = new JXComboBox[6];

    private final JTextField txtSemestre = new JTextField(5);
    private final JCheckBox[] checkDays = new JCheckBox[Horario.Days.length];

    private final DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table = new JTable(dtm);

    public RegisterGroupView(User user) {
        this.context = (Teacher) user;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Some data")));

        for (int i = 0; i < combosPlaces.length; i++) combosPlaces[i] = new JXComboBox(Horario.Places);

        dtm.setColumnIdentifiers(new String[]{
                "Grupo", "Materia", "Semestre", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        });

        JTextField txtProfesor = new JTextField(20);
        txtProfesor.setEnabled(false);
        txtProfesor.setText(context.getName());

        txtSemestre.setEnabled(false);
        txtSemestre.setText(
                Integer.toString(((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).semestre)
        );

        for (int i = 0; i < checkDays.length; i++) {
            checkDays[i] = new JCheckBox(Horario.Days[i]);
            if (((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).credits >= 5) {
                checkDays[i].setSelected(true);
                checkDays[i].setEnabled(false);
            }
        }

        checkDays[5].setSelected(false);
        combosPlaces[5].setEnabled(false);

        updateGroups();

        comboMaterias.addItemListener(e -> {
            Materia materia = (Materia) e.getItem();
            txtSemestre.setText(String.valueOf(materia.semestre));
            updateGroups();
        });

        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        for (int i = 3; i < 8; i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        table.setRowHeight(50);
        loadMyGroups();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridwidth = 1;
        add(new JLabel("Profesor"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 6;
        add(txtProfesor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Materia"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 6;
        add(comboMaterias, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Grupo"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 6;
        add(comboGroups, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Semestre"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 6;
        add(txtSemestre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Horario"), gbc);
        gbc.gridwidth = 6;
        gbc.gridx++;
        add(comboHorarios, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(new JLabel("Días"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        for (JCheckBox checkDay : checkDays) {
            gbc.gridx++;
            add(checkDay, gbc);
        }

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Lugares"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        for (JXComboBox comboBox : combosPlaces) {
            gbc.gridx++;
            AutoCompleteDecorator.decorate(comboBox);
            add(comboBox, gbc);
        }

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JButton("Add") {
            {
                addActionListener(e -> addGroup());
            }
        }, gbc);

        gbc.gridy++;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Mis grupos registrados:") {
            {
                this.setFont(this.getFont().deriveFont(Font.BOLD, 14f));
            }
        }, gbc);

        gbc.gridy++;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, gbc);
    }

    private void updateGroups() {
        groupsModel.removeAllElements();
        int semestre = ((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())).semestre;

        for (int i = 0; i < Group.groupNames.length; i++)
            if (Group.groupNames[i].startsWith(Integer.toString(semestre)))
                groupsModel.addElement(Group.groupNames[i]);
    }

    private void addGroup() {
        Group newG = new Group(Group.groups.size,
                (String) Objects.requireNonNull(comboGroups.getSelectedItem()),
                context,
                Integer.parseInt(txtSemestre.getText()),
                ((Materia) Objects.requireNonNull(comboMaterias.getSelectedItem())),
                new Horario() {
                    {
                        for (int i = 0; i < checkDays.length; i++) {
                            this.days[i] = checkDays[i].isSelected() ? Days[i] : "";
                            this.places[i] = checkDays[i].isSelected() ? (String) combosPlaces[i].getSelectedItem() : "";
                        }
                        this.horario = (String) comboHorarios.getSelectedItem();
                    }
                });

        Group.groups.add(newG);
        context.groups.add(newG);

        loadMyGroups();
    }

    private void loadMyGroups() {
        dtm.setRowCount(0);
        Object[][] data = new Object[context.groups.size][9];

        for (int i = 0; i < context.groups.size; i++) {
            Group group = context.groups.get(i);

            data[i][0] = group.name;
            data[i][1] = group.materia.name;
            data[i][2] = Integer.toString(group.semestre);

            for (int j = 3; j < 8; j++)
                data[i][j] = group.horario.days[j - 3].equals(Horario.Days[j - 3]) ?
                        new String[]{group.horario.horario, group.horario.places[j - 3]} : new String[]{"", ""};

            dtm.addRow(data[i]);
        }
    }
}
