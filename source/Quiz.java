package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Quiz extends JFrame implements ActionListener {
	JButton b1, b2, btnBack;

	Quiz() {
		// A Label
		JLabel label = new JLabel("Quizs");
		label.setForeground(Color.green);
		label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setAlignmentY(-100);
		// Add space
		// A Grid
		b1 = new JButton("1. Find Definition");
		b1.addActionListener(this);
		b1.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		JPanel panelCenter = new JPanel();

		panelCenter.setLayout(new GridLayout(1, 2, 10, 10));
		panelCenter.add(b1);
		b2 = new JButton("2. Find SlangWord");
		b2.addActionListener(this);
		b2.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		panelCenter.add(b2);
		Dimension size2 = new Dimension(500, 200);
		panelCenter.setMaximumSize(size2);
		panelCenter.setPreferredSize(size2);
		panelCenter.setMinimumSize(size2);

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
		con.add(panelCenter);
		con.add(Box.createRigidArea(new Dimension(0, 100)));
		con.add(buttonPane);

		this.setTitle("Quiz choose mode");
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			this.dispose();
			new Question(1);
		} else if (e.getSource() == b2) {
			this.dispose();
			new Question(2);
		} else if (e.getSource() == btnBack) {
			this.dispose();
			new Menu();
		}
	}
}