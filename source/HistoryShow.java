package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class HistoryShow extends JFrame implements ActionListener {
	JButton btnReturn, btnExit;
	SlangDictionary slangWord = SlangDictionary.getInstance();

	HistoryShow() {
		Container con = this.getContentPane();

		// History
		JLabel historyLabel = new JLabel();
		historyLabel.setText("History Slang Wordword Found");
		historyLabel.setForeground(Color.green);
		historyLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		historyLabel.setAlignmentX(CENTER_ALIGNMENT);
		// Table
		JPanel panelTable = new JPanel();
		panelTable.setBackground(Color.black);

		String[][] data = slangWord.readHistory();
		String[] column = { "No.", "Slang Word", "Definition" };
		JTable jt = new JTable(data, column);
		jt.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		jt.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		jt.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		jt.setEnabled(false);
		JScrollPane sp = new JScrollPane(jt);
		panelTable.setLayout(new GridLayout(1, 1));
		panelTable.add(sp);

		// 2 Button
		JPanel bottomPanel = new JPanel();
		btnReturn = new JButton("Return");
		btnExit = new JButton("Exit");
		btnReturn.addActionListener(this);
		btnExit.addActionListener(this);
		Dimension size2 = new Dimension(700, 50);
		bottomPanel.setMaximumSize(size2);
		bottomPanel.setPreferredSize(size2);
		bottomPanel.setMinimumSize(size2);
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.add(btnReturn);
		bottomPanel.add(btnExit);

		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(historyLabel);
		con.add(Box.createRigidArea(new Dimension(0, 50)));
		con.add(panelTable);
		con.add(Box.createRigidArea(new Dimension(0, 50)));
		con.add(bottomPanel);

		// Setting JFrame
		this.setTitle("History Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExit) {
			System.exit(0);
		} else if (e.getSource() == btnReturn) {
			this.dispose();
			new Menu();
		}
	}

}
