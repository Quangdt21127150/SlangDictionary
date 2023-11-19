package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
	JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
	SlangDictionary slangWord;

	Menu() {
		slangWord = SlangDictionary.getInstance();
		// A Label
		JLabel label = new JLabel("Slang Words");
		label.setForeground(Color.green);
		label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		label.setAlignmentX(CENTER_ALIGNMENT);

		// A Grid
		btn1 = new JButton("1. List Slang Words");
		btn1.addActionListener(this);
		btn1.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn1.setFocusable(false);

		btn2 = new JButton("2. Find Slang word");
		btn2.addActionListener(this);
		btn2.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn2.setFocusable(false);

		btn3 = new JButton("3. Add slang word");
		btn3.addActionListener(this);
		btn3.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn3.setFocusable(false);

		btn4 = new JButton("4. Random Slang Words");
		btn4.addActionListener(this);
		btn4.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn4.setFocusable(false);

		btn5 = new JButton("5. History");
		btn5.addActionListener(this);
		btn5.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn5.setFocusable(false);

		btn6 = new JButton("6. Delete Slang Word");
		btn6.addActionListener(this);
		btn6.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn6.setFocusable(false);

		btn7 = new JButton("7. Reset Slang Word");
		btn7.addActionListener(this);
		btn7.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn7.setFocusable(false);

		btn8 = new JButton("8. Quiz");
		btn8.addActionListener(this);
		btn8.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
		btn8.setFocusable(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 3, 10, 10));
		mainPanel.add(btn1);
		mainPanel.add(btn2);
		mainPanel.add(btn3);
		mainPanel.add(btn4);
		mainPanel.add(btn5);
		mainPanel.add(btn6);
		mainPanel.add(btn7);
		mainPanel.add(btn8);

		Dimension size2 = new Dimension(600, 500);
		mainPanel.setMaximumSize(size2);
		mainPanel.setPreferredSize(size2);
		mainPanel.setMinimumSize(size2);
		Container con = this.getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(label);
		con.add(Box.createRigidArea(new Dimension(0, 30)));
		con.add(mainPanel);

		// Setting Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Menu Window");
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			this.dispose();
			try {
				new ListWord();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btn2) {
			this.dispose();
			try {
				new FindWord();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == btn3) {
			// Add a slang word
			this.dispose();
			new AddWord();

		} else if (e.getSource() == btn4) {
			this.dispose();
			new RandomWord();

		} else if (e.getSource() == btn5) {
			this.dispose();
			new HistoryShow();

		} else if (e.getSource() == btn6) {
			this.dispose();
			try {
				new DeleteWord();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btn7) {
			// default icon, custom title
			int n = JOptionPane.showConfirmDialog(this, "Do you really want to reset Slang Word?", "An Inane Question",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				slangWord.reset();
				JOptionPane.showMessageDialog(this, "Reset success.");
			}
		} else if (e.getSource() == btn8) {
			this.dispose();
			new Quiz();
		}
	}

}
