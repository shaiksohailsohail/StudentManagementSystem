package com.kodnest.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRecordFrame extends JFrame {

    private JPanel contentPane;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField courseField;
    private JComboBox<String> genderComboBox;
    private JTextField idField;  // Add a JTextField for the ID

    public InsertRecordFrame() {
        setTitle("Insert Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);  // Adjust the window size to accommodate the new field
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblId.setBounds(50, 50, 100, 30);
        contentPane.add(lblId);

        idField = new JTextField();  // Initialize the JTextField for ID
        idField.setBounds(150, 50, 200, 30);  // Position the ID field
        contentPane.add(idField);
        idField.setColumns(10);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(50, 100, 100, 30);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(150, 100, 200, 30);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAge.setBounds(50, 150, 100, 30);
        contentPane.add(lblAge);

        ageField = new JTextField();
        ageField.setBounds(150, 150, 200, 30);
        contentPane.add(ageField);
        ageField.setColumns(10);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblGender.setBounds(50, 200, 100, 30);
        contentPane.add(lblGender);

        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setBounds(150, 200, 200, 30);
        contentPane.add(genderComboBox);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCourse.setBounds(50, 250, 100, 30);
        contentPane.add(lblCourse);

        courseField = new JTextField();
        courseField.setBounds(150, 250, 200, 30);
        contentPane.add(courseField);
        courseField.setColumns(10);

        JButton btnInsert = new JButton("Insert");
        btnInsert.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve input data and parse age and id as integers
                    int id = Integer.parseInt(idField.getText());  // Parse ID as an integer
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());  // Parse Age as an integer
                    String gender = genderComboBox.getSelectedItem().toString();
                    String course = courseField.getText();

                    // Insert data into the database
                    insertData(id, name, age, gender, course);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InsertRecordFrame.this, "Error: ID and Age must be valid integers.");
                }
            }
        });
        btnInsert.setBounds(150, 300, 100, 30);
        contentPane.add(btnInsert);

        // Add Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBounds(270, 300, 100, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (InsertRecordFrame) and show Home page
                dispose();  // Close the current frame
                Home homePage = new Home();  // Create the Home page frame
                homePage.setVisible(true);  // Show the Home page
            }
        });
        contentPane.add(btnBack);
    }

    private void insertData(int id, String name, int age, String gender, String course) {
        // Database connection and insertion logic
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");
            System.out.println("Connection Established successfully");

            // SQL query to insert data into the students table
            String query = "INSERT INTO students (id, name, age, gender, course) VALUES (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);  // Set the ID as entered by the user
            pst.setString(2, name);
            pst.setInt(3, age);  // Set the Age as entered by the user
            pst.setString(4, gender);
            pst.setString(5, course);

            // Execute the query
            int result = pst.executeUpdate();

            // Check if the insertion was successful
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Record Inserted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Insert Record.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                // Close the resources
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
                    InsertRecordFrame frame = new InsertRecordFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
