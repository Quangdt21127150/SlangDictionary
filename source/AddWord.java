package source;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddWord extends JFrame implements ActionListener {
	SlangDictionary slangWord;
	JButton btnBack, btnAdd;
	JTextField newMeaningField, newWordField;

	AddWord() {
		// Get container & slang word
		slangWord = SlangDictionary.getInstance();
		Container con = this.getContentPane();
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Add Slang Words");

		//Reference about setForeground: https://stackoverflow.com/questions/34262447/java-applet-setforeground-what-exactly-it-does-and-how-to-see-its-effect
		titleLabel.setForeground(Color.green);

		//Reference about setFont: https://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
		titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		titleLabel.setPreferredSize(new Dimension(300, 100));

		// Form
		JPanel form = new JPanel();

		JPanel slangPanel = new JPanel();
		form.setBackground(Color.CYAN);
		SpringLayout layout = new SpringLayout();
		slangPanel.setLayout(layout);
		JLabel slangWordLabel = new JLabel("Slang word: ");
		newWordField = new JTextField("", 20);
		slangWordLabel.setPreferredSize(new Dimension(100, 20));
		slangPanel.add(slangWordLabel);
		slangPanel.add(newWordField);
		
		//Reference about SpringLayout: https://viettuts.vn/java-swing/springlayout-trong-java-swing
		layout.putConstraint(SpringLayout.WEST, slangWordLabel, 6, SpringLayout.WEST, slangPanel);
		layout.putConstraint(SpringLayout.NORTH, slangWordLabel, 6, SpringLayout.NORTH, slangPanel);
		layout.putConstraint(SpringLayout.WEST, newWordField, 6, SpringLayout.EAST, slangWordLabel);
		layout.putConstraint(SpringLayout.NORTH, newWordField, 6, SpringLayout.NORTH, slangPanel);
		layout.putConstraint(SpringLayout.EAST, slangPanel, 6, SpringLayout.EAST, newWordField);
		layout.putConstraint(SpringLayout.SOUTH, slangPanel, 6, SpringLayout.SOUTH, newWordField);

		JPanel meaningPanel = new JPanel();
		SpringLayout layout1 = new SpringLayout();
		meaningPanel.setLayout(layout1);
		JLabel meaningLabel = new JLabel("Meaning: ");
		meaningLabel.setPreferredSize(new Dimension(100, 20));
		newMeaningField = new JTextField("", 20);
		meaningPanel.add(meaningLabel);
		meaningPanel.add(newMeaningField);
		layout1.putConstraint(SpringLayout.WEST, meaningLabel, 6, SpringLayout.WEST, meaningPanel);
		layout1.putConstraint(SpringLayout.NORTH, meaningLabel, 6, SpringLayout.NORTH, meaningPanel);
		layout1.putConstraint(SpringLayout.WEST, newMeaningField, 6, SpringLayout.EAST, meaningLabel);
		layout1.putConstraint(SpringLayout.NORTH, newMeaningField, 6, SpringLayout.NORTH, meaningPanel);
		layout1.putConstraint(SpringLayout.EAST, meaningPanel, 6, SpringLayout.EAST, newMeaningField);
		layout1.putConstraint(SpringLayout.SOUTH, meaningPanel, 6, SpringLayout.SOUTH, newMeaningField);

		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		form.add(slangPanel);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		form.add(meaningPanel);
		
		// Button Back and button Add
		JPanel bottomPanel = new JPanel();
		btnBack = new JButton("Back ");
		btnBack.setFocusable(false);
		btnBack.addActionListener(this);
		btnBack.setAlignmentX(CENTER_ALIGNMENT);
		btnAdd = new JButton("Add");
		btnAdd.setFocusable(false);
		btnAdd.addActionListener(this);
		btnAdd.setAlignmentX(CENTER_ALIGNMENT);
		bottomPanel.add(btnBack);
		bottomPanel.add(btnAdd);

		// Setting content
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(titleLabel);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(form);
		con.add(Box.createRigidArea(new Dimension(0, 10)));
		con.add(bottomPanel);
		
		// Setting Frame
		this.setTitle("Add Slang word");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	@Override
	//Reference about JOptionPane: https://viettuts.vn/java-swing/lop-joptionpane-trong-java-swing
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBack) {
			this.dispose();
			new Menu();
		} else if (e.getSource() == btnAdd) {
			String slang = newWordField.getText();
			String meaning = newMeaningField.getText();
			if (slang.isEmpty() || meaning.isEmpty()) {
				// custom title, error icon
				JOptionPane.showMessageDialog(this, "Slang Word and Meaning maybe empty", "Inane error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (slangWord.checkSlang(slang)) {
				// Duplicate or Overwrite
				Object[] options = { "Overwrite", "Duplicate" };
				int n = JOptionPane.showOptionDialog(this,
						"Slang `" + slang + "` have already exist on  SlangWord  List", "A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
				if (n == 0) {
					// Overwrite
					slangWord.addOverwrite(slang, meaning);
					JOptionPane.showMessageDialog(this, "Overwrite Slang Word Success.");
				} else if (n == 1) {
					// Duplicate
					slangWord.addDuplicate(slang, meaning);
					JOptionPane.showMessageDialog(this, "Dupilicate Slang Word Success.");
				}
			} else {
				// Add new slang
				slangWord.addNew(slang, meaning);
				JOptionPane.showMessageDialog(this, "Adding new Slang Word Success.");
			}
			newWordField.setText("");
			newMeaningField.setText("");
		}
	}

}
