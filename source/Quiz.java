package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Quiz extends JFrame implements ActionListener {
	JButton btnA, btnB, btnBack;

	Quiz() {
		// A Label
		JLabel label = new JLabel("Quizs");
		label.setForeground(Color.green);
		label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setAlignmentY(-100);
		// Add space
		// A Grid
		btnA = new JButton("1. Find Definition");
		btnA.addActionListener(this);
		btnA.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(1, 2, 10, 10));
		mainPanel.add(btnA);
		btnB = new JButton("2. Find SlangWord");
		btnB.addActionListener(this);
		btnB.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		mainPanel.add(btnB);
		Dimension size2 = new Dimension(500, 200);
		mainPanel.setMaximumSize(size2);
		mainPanel.setPreferredSize(size2);
		mainPanel.setMinimumSize(size2);

		// Button back

		btnBack = new JButton("Back");
		btnBack.addActionListener(this);

		JPanel buttonPane = new JPanel();
		buttonPane.add(btnBack);

		// Setting content
		Container con = this.getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 100)));
		con.add(label);
		con.add(Box.createRigidArea(new Dimension(0, 100)));
		con.add(mainPanel);
		con.add(Box.createRigidArea(new Dimension(0, 100)));
		con.add(buttonPane);

		//Setting Frame
		this.setTitle("Quiz choose mode");
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnA) {
			this.dispose();
			new Question(1);
		} else if (e.getSource() == btnB) {
			this.dispose();
			new Question(2);
		} else if (e.getSource() == btnBack) {
			this.dispose();
			new Menu();
		}
	}
}