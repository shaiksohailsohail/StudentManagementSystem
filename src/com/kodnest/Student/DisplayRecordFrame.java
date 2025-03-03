package com.kodnest.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Vector;

public class DisplayRecordFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * Create the frame.
     */
    public DisplayRecordFrame() {
        setTitle("Display Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);  // Adjusted the size for better visibility
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Create a JScrollPane to hold the table
        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 20, 530, 250); // Adjusted size
        contentPane.add(scrollPane);

        // Create a JTable to display the records
        table = new JTable();
        scrollPane.setViewportView(table);

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(250, 300, 100, 30); // Centered button
        contentPane.add(btnBack);

        // ActionListener for Back Button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (DisplayRecordFrame)
                dispose();
                // Show the Home frame again
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });

        // Load data into the table
        loadData();
    }

    private void loadData() {
        // Database connection and query logic
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingers", "root", "sohail");

            // SQL query to fetch all records
            String query = "SELECT * FROM students";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            // Prepare the table model
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();

            // Get column names from the database
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Prepare the table data
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            // Create the table model and set it to the table
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            table.setModel(tableModel);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
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
                    DisplayRecordFrame frame = new DisplayRecordFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
