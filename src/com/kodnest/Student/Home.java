package com.kodnest.Student;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home frame = new Home();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 548, 308);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("STUDENT MANAGEMENT SYSTEM");
        lblNewLabel.setBounds(108, 11, 366, 50);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblNewLabel);

        // Insert Record Button
        JButton btnNewButton_1 = new JButton("Insert Record");
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Insert Record Frame
                InsertRecordFrame insertFrame = new InsertRecordFrame();
                insertFrame.setVisible(true);
                // Hide Home frame when Insert Record Frame opens
                setVisible(false);
            }
        });
        btnNewButton_1.setBounds(31, 86, 150, 40);  // Adjusted to fit within the space
        contentPane.add(btnNewButton_1);

        // Customised Display Button
        JButton btnNewButton_2 = new JButton("Customised Display");
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Customised Display Frame (You can create this frame as needed)
                CustomisedDisplayFrame displayFrame = new CustomisedDisplayFrame();
                displayFrame.setVisible(true);
            }
        });
        btnNewButton_2.setBounds(336, 86, 174, 40);  // Adjusted to fit within the space
        contentPane.add(btnNewButton_2);

        // Delete Record Button
        JButton btnNewButton_3 = new JButton("Delete Record");
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Delete Record Frame (You can create this frame as needed)
                DeleteRecordFrame deleteFrame = new DeleteRecordFrame();
                deleteFrame.setVisible(true);
            }
        });
        btnNewButton_3.setBounds(31, 158, 150, 40);  // Adjusted to fit within the space
        contentPane.add(btnNewButton_3);

        // Display Record Button
        JButton btnNewButton_5 = new JButton("Display Record");
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Display Record Frame (You can create this frame as needed)
                DisplayRecordFrame displayFrame = new DisplayRecordFrame();
                displayFrame.setVisible(true);
            }
        });
        btnNewButton_5.setBounds(336, 158, 165, 40);  // Adjusted to fit within the space
        contentPane.add(btnNewButton_5);

        // Update Record Button
        JButton btnNewButton_6 = new JButton("Update Record");
        btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Update Record Frame (You can create this frame as needed)
                UpdateRecordFrame updateFrame = new UpdateRecordFrame();
                updateFrame.setVisible(true);
            }
        });
        btnNewButton_6.setBounds(179, 209, 165, 40);  // Adjusted to fit within the space
        contentPane.add(btnNewButton_6);
    }
}
