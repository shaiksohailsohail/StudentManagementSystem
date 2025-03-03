package com.kodnest.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecordFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField;

    /**
     * Create the frame.
     */
    public DeleteRecordFrame() {
        setTitle("Delete Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Label for ID
        JLabel lblId = new JLabel("Enter ID to Delete:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblId.setBounds(50, 50, 150, 30);
        contentPane.add(lblId);

        // JTextField for ID
        idField = new JTextField();
        idField.setBounds(200, 50, 150, 30);
        contentPane.add(idField);
        idField.setColumns(10);

        // Delete Button
        JButton btnDelete = new JButton("Delete Record");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(120, 120, 150, 30);
        contentPane.add(btnDelete);

        // ActionListener for Delete Button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve the ID entered by the user
                    int id = Integer.parseInt(idField.getText());

                    // Call the deleteData method to delete the record
                    deleteData(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DeleteRecordFrame.this, "Error: ID must be a valid integer.");
                }
            }
        });

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(150, 180, 100, 30);
        contentPane.add(btnBack);

        // ActionListener for Back Button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (DeleteRecordFrame)
                dispose();
                // Show the Home frame again
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });
    }

    // Method to delete data from the database
    private void deleteData(int id) {
        // Database connection and deletion logic
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");

            // SQL query to delete data from the students table
            String query = "DELETE FROM students WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);  // Set the ID to the one entered by the user

            // Execute the query
            int result = pst.executeUpdate();

            // Check if the deletion was successful
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Record Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the given ID.");
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
                    DeleteRecordFrame frame = new DeleteRecordFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
