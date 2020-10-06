package com.sdproject.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private JPanel panel;
	private JLabel user_label, pass_label;
	private JTextField user_text;
	private JPasswordField pass_text;
	private JButton submit;

	public LoginView() {
		panel = new JPanel(new GridLayout(3, 1));
		addUserLabel();
		addPassLabel();
		addSubmitButton();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Login to TaskApp");
		setSize(500, 300);
		setVisible(true);
	}

	private void addUserLabel() {
		user_label = new JLabel();
		user_label.setText("User Name: ");
		user_text = new JTextField();
		panel.add(user_label);
		panel.add(user_text);
	}

	private void addPassLabel() {
		pass_label = new JLabel();
                pass_label.setText("Password: ");
                pass_text = new JPasswordField();
                panel.add(pass_label);
                panel.add(pass_text);
	}

	private void addSubmitButton() {
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = user_text.getText();
				String password = pass_text.getText();

				//Tentative until we implement user/password system
				if (username.trim().equals("admin") && password.trim().equals("password")) {
					JOptionPane.showMessageDialog(null, "Successful login, display user view");
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username/password combination. Try again");
				}
			}
		});
		panel.add(submit);
	}

}
