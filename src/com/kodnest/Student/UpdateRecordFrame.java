package com.kodnest.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateRecordFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderComboBox;
    private JTextField courseField;

    public UpdateRecordFrame() {
        setTitle("Update Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 502, 396);  // Adjust the window size to fit all fields
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblId.setBounds(50, 27, 100, 30);
        contentPane.add(lblId);

        idField = new JTextField();
        idField.setBounds(150, 29, 200, 30);
        contentPane.add(idField);
        idField.setColumns(10);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(50, 84, 100, 30);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(150, 86, 200, 30);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAge.setBounds(50, 137, 100, 30);
        contentPane.add(lblAge);

        ageField = new JTextField();
        ageField.setBounds(150, 139, 200, 30);
        contentPane.add(ageField);
        ageField.setColumns(10);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblGender.setBounds(50, 186, 100, 30);
        contentPane.add(lblGender);

        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setBounds(150, 188, 200, 30);
        contentPane.add(genderComboBox);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCourse.setBounds(50, 237, 100, 30);
        contentPane.add(lblCourse);

        courseField = new JTextField();
        courseField.setBounds(150, 239, 200, 30);
        contentPane.add(courseField);
        courseField.setColumns(10);

        // Fetch the record when the ID is entered
        JButton btnFetchRecord = new JButton("Fetch Record");
        btnFetchRecord.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnFetchRecord.setBounds(10, 294, 150, 30);
        contentPane.add(btnFetchRecord);
        
        btnFetchRecord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch the record based on the entered ID
                int id = Integer.parseInt(idField.getText());
                fetchRecord(id);
            }
        });

        // Update Button
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnUpdate.setBounds(181, 294, 150, 30);
        contentPane.add(btnUpdate);
        
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the updated information and update the database
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String gender = genderComboBox.getSelectedItem().toString();
                String course = courseField.getText();
                
                updateRecord(id, name, age, gender, course);
            }
        });

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBounds(358, 296, 100, 30); // Positioned below the Update button
        contentPane.add(btnBack);
        
        // ActionListener for Back Button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (UpdateRecordFrame)
                dispose();
                // Show the Home frame again
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });
    }

    private void fetchRecord(int id) {
        // Fetch record from the database based on ID
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");

            // SQL query to fetch the record by ID
            String query = "SELECT * FROM students WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                // Fill the fields with the current data
                nameField.setText(rs.getString("name"));
                ageField.setText(String.valueOf(rs.getInt("age")));
                genderComboBox.setSelectedItem(rs.getString("gender"));
                courseField.setText(rs.getString("course"));
            } else {
                JOptionPane.showMessageDialog(this, "Record not found.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateRecord(int id, String name, int age, String gender, String course) {
        // Update the record in the database
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");

            // SQL query to update the record
            String query = "UPDATE students SET name = ?, age = ?, gender = ?, course = ? WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.setString(4, course);
            pst.setInt(5, id);

            // Execute the update
            int result = pst.executeUpdate();

            // Check if the update was successful
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Record Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Update Record.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Show the frame
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdateRecordFrame frame = new UpdateRecordFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
