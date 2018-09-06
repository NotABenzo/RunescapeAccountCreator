package org.medusa;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NotificationGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotificationGUI frame = new NotificationGUI(type);
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
	public NotificationGUI(String type) {
		setTitle("Done");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 294, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAmount = new JLabel("");
		lblAmount.setBounds(10, 11, 258, 14);
		contentPane.add(lblAmount);

		JButton btnAwesome = new JButton("Done");
		
		if (type == "complete") {
		lblAmount.setText(Main.currentNumber + "/" + Main.accountsWanted + " accounts created");
		
		btnAwesome.setText("Awesome!");
		btnAwesome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		} else if (type == "error") {
		lblAmount.setText("An error ocurred!");
		btnAwesome.setText(";-;");
		btnAwesome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		}
		btnAwesome.setBounds(77, 102, 123, 49);
		contentPane.add(btnAwesome);
	}
}
