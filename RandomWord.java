import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class RandomWord extends JFrame implements ActionListener {
	JButton btnBack, btnReset;
	SlangDictionary slangWord = SlangDictionary.createDictionary();
	JLabel lb2, lb4;

	RandomWord() {
		// Get Content
		Container con = this.getContentPane();

		// Title
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("SLANG WORD RANDOM TO DAY IS ");
		titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		titlePanel.add(titleLabel);
		titlePanel.setBackground(Color.blue);
		titleLabel.setForeground(Color.white);
		titlePanel.setMaximumSize(new Dimension(700, 300));

		// Slang word
		String s[] = slangWord.random();
		JPanel slangPanel = new JPanel();
		JLabel lb1 = new JLabel("Slang: ");
		lb2 = new JLabel(s[0]);
		JLabel lb3 = new JLabel("Meaning: ");
		lb4 = new JLabel(s[1]);
		lb2.setForeground(Color.green);
		lb4.setForeground(Color.red);
		lb1.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		lb2.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		lb3.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		lb4.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));

		slangPanel.add(lb1);
		slangPanel.add(lb2);
		slangPanel.add(lb3);
		slangPanel.add(lb4);

		// Bottom btnback btnRenew
		btnReset = new JButton("Reset");
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnReset.addActionListener(this);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnReset);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(btnBack);

		// Setting con
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(titlePanel);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(slangPanel);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(buttonPane);

		// Setting JFrame
		this.setTitle("Ramdom Slangwords");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBack) {
			this.dispose();
			new Menu();
		} else if (e.getSource() == btnReset) {
			String s[] = slangWord.random();
			lb2.setText(s[0]);
			lb4.setText(s[1]);
		}
	}

}
