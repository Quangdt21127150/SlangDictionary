import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;
import java.awt.*;

public class Slang_Dictionary extends JPanel{
    public static TreeMap<String, Set<String>> data;
    public static Vector<String> history;

    static void inputFile(String file) throws IOException{
        File f = new File(file);
        if(!f.exists())
            return;
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        while(line != null){
            String[] str = line.split("`");
            if(str.length == 2){
                String[] definition = str[1].split("\\|");
                Set<String> def = new HashSet<>(Arrays.stream(definition).collect(Collectors.toSet()));
                data.put(str[0], def);
            }
            line = in.readLine();
        }
        in.close();
    }

    static void saveDictionary() throws IOException{
        FileWriter save = new FileWriter(new File("MyDictionary.txt"));
        Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
        for(Map.Entry<String, Set<String>> word : dictionary){
            save.write(word.getKey() + "`");
            Set<String> definition = word.getValue();
            String lastDef = definition.stream().reduce((a, b) -> b).get();
            for (String def : definition){
                if (def.equals(lastDef))
                    save.write(def + "\n");
                else 
                    save.write(def + "| ");
            }
        };

        save.close();
    }

    public Slang_Dictionary(){
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Search");
        JTextField searchField = new JTextField("Search a slang word or a definition");
        searchField.setPreferredSize(searchField.getPreferredSize());
        
        topPanel.add(label);
        topPanel.add(searchField);

        add(topPanel, BorderLayout.WEST);

        String[] all_words = new String[words.size()];
        JList<String> wordList = new JList<>(words.toArray(all_words));
        JScrollPane wordPane = new JScrollPane(wordList);

        add(wordPane, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setLayout(new BoxLayout(bottomPanel1, BoxLayout.LINE_AXIS));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        bottomPanel1.add(ok);
        bottomPanel1.add(Box.createRigidArea(new Dimension(30, 0)));
        bottomPanel1.add(cancel);

        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.setLayout(new BoxLayout(bottomPanel2, BoxLayout.PAGE_AXIS));
        bottomPanel2.add(Box.createRigidArea(new Dimension(0, 5)));
        bottomPanel2.add(bottomPanel1);
        bottomPanel2.add(Box.createRigidArea(new Dimension(0, 5)));

        add(bottomPanel2, BorderLayout.PAGE_END);
    }

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Slang_Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Slang_Dictionary();
        
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    
    public static void main(String [] args) throws IOException{
        inputFile("slang.txt");
        createAndShowGUI();
    }
}
