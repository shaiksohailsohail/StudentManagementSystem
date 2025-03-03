package com.kodnest.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class CustomisedDisplayFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JCheckBox cbName, cbAge, cbGender, cbCourse;
    private Connection conn;

    /**
     * Create the frame.
     */
    public CustomisedDisplayFrame() {
        setTitle("Customised Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400); // Increased size for table display
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Checkboxes for customizing the display
        cbName = new JCheckBox("Name");
        cbName.setFont(new Font("Tahoma", Font.BOLD, 17));
        cbName.setBounds(34, 36, 100, 30);
        contentPane.add(cbName);

        cbAge = new JCheckBox("Age");
        cbAge.setFont(new Font("Tahoma", Font.BOLD, 17));
        cbAge.setBounds(167, 36, 100, 30);
        contentPane.add(cbAge);

        cbGender = new JCheckBox("Gender");
        cbGender.setFont(new Font("Tahoma", Font.BOLD, 17));
        cbGender.setBounds(356, 36, 100, 30);
        contentPane.add(cbGender);

        cbCourse = new JCheckBox("Course");
        cbCourse.setFont(new Font("Tahoma", Font.BOLD, 17));
        cbCourse.setBounds(530, 36, 100, 30);
        contentPane.add(cbCourse);

        // Button to apply custom display
        JButton btnShow = new JButton("Customized Display");
        btnShow.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnShow.setBounds(215, 77, 211, 40);
        contentPane.add(btnShow);

        // JTable to display the data
        table = new JTable();
        table.setBounds(69, 120, 519, 178);
        contentPane.add(table);

        // ActionListener for the Show Button
        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected columns
                ArrayList<String> selectedColumns = new ArrayList<>();
                if (cbName.isSelected()) selectedColumns.add("name");
                if (cbAge.isSelected()) selectedColumns.add("age");
                if (cbGender.isSelected()) selectedColumns.add("gender");
                if (cbCourse.isSelected()) selectedColumns.add("course");

                // Get the data based on selected columns and display it
                if (!selectedColumns.isEmpty()) {
                    displayData(selectedColumns);
                } else {
                    JOptionPane.showMessageDialog(CustomisedDisplayFrame.this, "Please select at least one column.");
                }
            }
        });

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnBack.setBounds(556, 310, 100, 40); // Positioned on the right side
        contentPane.add(btnBack);

        // ActionListener for Back Button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (CustomisedDisplayFrame)
                dispose();
                // Show the Home frame again
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });
    }

    private void displayData(ArrayList<String> selectedColumns) {
        DefaultTableModel model = new DefaultTableModel();
        // Add column names dynamically based on the selected columns
        for (String column : selectedColumns) {
            model.addColumn(column);
        }

        // Fetch data from the database based on selected columns
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");
            StringBuilder query = new StringBuilder("SELECT ");
            for (int i = 0; i < selectedColumns.size(); i++) {
                query.append(selectedColumns.get(i));
                if (i < selectedColumns.size() - 1) {
                    query.append(", ");
                }
            }
            query.append(" FROM students");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            // Add rows to the table model
            while (rs.next()) {
                Object[] row = new Object[selectedColumns.size()];
                for (int i = 0; i < selectedColumns.size(); i++) {
                    row[i] = rs.getObject(selectedColumns.get(i));
                }
                model.addRow(row);
            }

            // Set the model to the table
            table.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomisedDisplayFrame frame = new CustomisedDisplayFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
