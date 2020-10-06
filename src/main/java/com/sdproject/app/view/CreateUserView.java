package com.sdproject.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.sdproject.app.model.User;
import com.sdproject.app.model.User.UserType;

public class CreateUserView extends JFrame {

	JPanel panel;
	JLabel user_label, type_label;
	JTextField user_text;
	JComboBox<String> user_type;
	JButton submit;

	public CreateUserView() {
		panel = new JPanel(new GridLayout(4, 1));
		panel.add(new JLabel("New user creation"));
		panel.add(new JLabel(""));
		addUserLabel();
		addTypeLabel();
		addSubmitButton();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("New User creation");
		setSize(600, 600);
		setVisible(true);
	}

	public void addUserLabel() {
		user_label = new JLabel();
		user_label.setText("Username: ");
		user_text = new JTextField();
		panel.add(user_label);
		panel.add(user_text);
	}	

	public void addTypeLabel() {
		type_label = new JLabel();
		type_label.setText("Select user type: ");
		
		String[] typeList = new String[] {"NORMAL", "ADMIN"};
		user_type = new JComboBox<String>(typeList);
		panel.add(type_label);
		panel.add(user_type);
	}

	public void addSubmitButton() {
		submit = new JButton("SUBMIT");
		submit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
				
				//Tentative until we figure out how to handle storing user data

				String newUserName = user_text.getText();
				UserType newUserType = user_type.getSelectedItem().equals("ADMIN") ? UserType.ADMIN : UserType.NORMAL;
				User newUser = new User(newUserName, newUserType);
				String message = "Name: " + newUserName +", Type: " + newUserType.name();
				JOptionPane.showMessageDialog(null, message);
                        }
                });
                panel.add(submit);
	}

}
