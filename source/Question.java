package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Question extends JFrame implements ActionListener {
	JButton btnA, btnB, btnC, btnD, btnBack;
	String[] s;

	Question(int type) {
		s = (SlangDictionary.getInstance()).quiz(type);
		// A Label
		JLabel label = new JLabel("Find out the correct answer");
		label.setForeground(Color.green);
		label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setAlignmentY(-100);
		JLabel questionLabel;
		if (type == 1)
			questionLabel = new JLabel("Slang " + "`" + s[0] + "`" + " means?");
		else
			questionLabel = new JLabel("`" + s[0] + "`" + " is the definition of slang word:");
		questionLabel.setForeground(Color.black);
		questionLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
		questionLabel.setAlignmentX(CENTER_ALIGNMENT);
		questionLabel.setAlignmentY(-100);

		// Add space
		// A Grid Answers
		btnA = new JButton("A." + s[1]);
		btnA.addActionListener(this);
		btnA.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnB = new JButton("B." + s[2]);
		btnB.addActionListener(this);
		btnB.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnC = new JButton("C." + s[3]);
		btnC.addActionListener(this);
		btnC.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnD = new JButton("D. " + s[4]);
		btnD.addActionListener(this);
		btnD.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 2, 10, 10));
		mainPanel.add(btnA);
		mainPanel.add(btnB);
		mainPanel.add(btnC);
		mainPanel.add(btnD);
		Dimension size2 = new Dimension(600, 200);
		mainPanel.setMaximumSize(size2);
		mainPanel.setPreferredSize(size2);
		mainPanel.setMinimumSize(size2);
		// Button back

		btnBack = new JButton("Back");
		btnBack.addActionListener(this);

		JPanel buttonPane = new JPanel();
		buttonPane.add(btnBack);

		Container con = this.getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 100)));
		con.add(label);
		con.add(Box.createRigidArea(new Dimension(0, 30)));
		con.add(questionLabel);
		con.add(Box.createRigidArea(new Dimension(0, 50)));
		con.add(mainPanel);
		con.add(Box.createRigidArea(new Dimension(0, 50)));
		con.add(buttonPane);
		this.setTitle("Question Quiz");
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnA) {
			this.answer(1);
		} else if (e.getSource() == btnB) {
			this.answer(2);
		} else if (e.getSource() == btnC) {
			this.answer(3);
		} else if (e.getSource() == btnD) {
			this.answer(4);
		} else if (e.getSource() == btnBack) {
			this.dispose();
			new Quiz();
		}
	}

	public void answer(int ans) {
		if (s[ans].equals(s[5])) {
			// default title and icon
			JOptionPane.showMessageDialog(this, "Correct Answer.");

		} else {
			JOptionPane.showMessageDialog(this, "Wrong Answer", "Inane error", JOptionPane.ERROR_MESSAGE);
			if (ans == 1)
				btnA.setBackground(Color.red);
			else if (ans == 2)
				btnB.setBackground(Color.red);
			else if (ans == 3)
				btnC.setBackground(Color.red);
			else if (ans == 4)
				btnD.setBackground(Color.red);
		}
		if (s[1].equals(s[5])) {
			btnA.setEnabled(false);
			btnA.setBackground(Color.green);
		} else {
			btnA.setEnabled(false);
		}
		if (s[2].equals(s[5])) {
			btnB.setEnabled(false);
			btnB.setBackground(Color.green);
		} else {
			btnB.setEnabled(false);
		}
		if (s[3].equals(s[5])) {
			btnC.setEnabled(false);
			btnC.setBackground(Color.green);
		} else {
			btnC.setEnabled(false);
		}
		if (s[4].equals(s[5])) {
			btnD.setEnabled(false);
			btnD.setBackground(Color.green);
		} else {
			btnD.setEnabled(false);
		}
	}
}