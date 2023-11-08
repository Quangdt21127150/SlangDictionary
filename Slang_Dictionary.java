import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Slang_Dictionary{
    public static TreeMap<String, Set<String>> data = new TreeMap<String, Set<String>>();
    public static Vector<String> history;
    static Scanner scan = new Scanner(System.in);
    static ProcessBuilder process = new ProcessBuilder("cmd", "/c", "cls");

    public static void inputFile(String file) throws IOException{
        File f = new File(file);
        if(!f.exists())
            return;
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = in.readLine();
        while(line != null){
            String[] str = line.split("`");
            if(str.length == 2){
                String[] definition = str[1].split("\\|");
                for(String i : definition)
                    i.trim();
                Set<String> def = new HashSet<>(Arrays.stream(definition).collect(Collectors.toSet()));
                data.put(str[0].trim(), def);
            }
            line = in.readLine();
        }
        in.close();
    }

    public static void saveDictionary() throws IOException{
        FileWriter save = new FileWriter(new File("MyDictionary.data"));
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

    public static void searchSlangWord() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to search: ");
        String input = scan.nextLine().trim();
        addHistory(input);

        Set<String> def = data.get(input);
        if (def == null){
            Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
            for(Map.Entry<String,Set<String>> word: dictionary){
                String w = word.getKey();
                if (w.contains(input)){
                    System.out.println(" + " + w);
                }
            }

            System.out.println("The word '" + input + "' does not exist!");
        }
        else {
            System.out.println("Had found!");
            System.out.println(" + " + input + " is " + def);
        }
    }

    public static void searchDefinition() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter a definition that you want to search: ");
        String def = scan.nextLine().trim();

        Boolean flag = false;
        Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
        for(Map.Entry<String,Set<String>> word: dictionary){
            Set<String> defList = word.getValue();
            for(String i : defList){
                if (i.contains(def) || i.contains(def.toUpperCase()) || i.contains(def.toLowerCase())){
                    flag = true;
                    System.out.println(" + " + word.getKey());
                    break;
                }
            }
        }

        if(!flag){
            System.out.println("Can not find slang word has this definition!");
        }
    }

    private static void inputHistory() throws IOException{
        history = new Vector<String>();

        BufferedReader line = new BufferedReader(new FileReader(new File("History.data")));
        String word;
        while((word = line.readLine()) != null){
            history.add(word);
        }  
        line.close();  
    }

    private static void saveHistory() throws IOException{
        FileWriter save = new FileWriter(new File("History.data"));
        for (String word : history){
            save.write(word + "\n");
        }
        save.close();
    }

    public static void addHistory(String word) throws IOException{
        history.add(word);
        saveHistory();
    }

    public static void printHistory() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        if (history.isEmpty())
            System.out.println("History is empty");
        else 
            for(String word : history)
                System.out.println("\t" + "+ " + word);
    }

    public static void addSlangWord() throws IOException, InterruptedException{
        HashSet<String> definition = new HashSet<String>();
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to add: ");
        String word = scan.nextLine().trim();

        String choice = "0";
        if (data.containsKey(word)){
            System.out.println();
            System.out.println("Word exist in dictionary!");
            System.out.println("1. Overwrite slang word");
            System.out.println("2. Duplicate to new slang word");
            System.out.print("Enter your choice: ");
            choice = scan.nextLine().trim();
        }
        
        System.out.println();
        String numOfDef = "";
        while(!numOfDef.matches("[0-9]{1,}")){
            System.out.print("Enter the number of definition of slang word you want to add (it must be an integer): ");
            numOfDef = scan.nextLine().trim();
        }
        int c = Integer.parseInt(numOfDef);

        for (int i = 1; i <= c; ++i){
            System.out.print("Enter definition " + i + ": ");
            definition.add(scan.nextLine().trim());
        }

        if (choice.equals("0") || choice.equals("1"))
            data.put(word, definition);
        else if (choice.equals("2"))
            data.put(word.toLowerCase(), definition);   
        else
            return;

        System.out.println("Adding the new slang word is success!");
        saveDictionary();
    }

    public static void editSlangWord() throws IOException, InterruptedException{
        HashSet<String> definition = new HashSet<String>();
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to edit: ");
        String word = scan.nextLine().trim();

        if (!data.containsKey(word)){
            System.out.println("Word does not exist!");
            return;
        }
        else 
            System.out.println(word + " is " + data.get(word));

        String choice;
        System.out.println("1. Add more definition for slang word");
        System.out.println("2. Create new definition for slang word");
        System.out.print("Enter your choice: ");
        choice = scan.nextLine().trim();

        System.out.println();
        String numOfDef = "";
        while(!numOfDef.matches("[0-9]{1,}")){
            System.out.print("Enter the number of definition of slang word you want to add (it must be an integer): ");
            numOfDef = scan.nextLine().trim();
        }
        int c = Integer.parseInt(numOfDef);

        for (int i = 1; i <= c; ++i){
            System.out.print("Enter definition " + i + ": ");
            definition.add(scan.nextLine().trim());
        }
        
        if (choice.equals("1"))
            definition.addAll(data.get(word));
        else if (choice.equals("2"))
            data.put(word.toLowerCase(), definition);
        else 
            return;

        data.put(word, definition);
        System.out.println("Editing the slang word is success!");
        saveDictionary();
    }

    public static void deleteSlangWord() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to delete: ");
        String word = scan.nextLine().trim();

        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Are you sure to delete word?: ");

        String choice = scan.nextLine().trim();
        if (choice.equals("1")){
            if (data.remove(word) == null)
                System.out.println("Word does not exist");
            else 
                System.out.println("Deleting is success!");
        }
        else if (choice.equals("2"))
            return;
        else {
            System.out.println("Invalid choice!");
            return;
        }
        saveDictionary();
    }

    public static void resetDictionary() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        inputFile("slang.data");
        saveDictionary();
        System.out.println();
        System.out.println("Reset The Dictionary Success!");
    }

    public static String onThisDaySlang(Boolean quiz) throws IOException, InterruptedException{
        ArrayList<String> keysAsArray = new ArrayList<String>(data.keySet());
        Random random = new Random();
        String word = keysAsArray.get(random.nextInt(keysAsArray.size()));
        process.inheritIO().start().waitFor();
        if (!quiz){
            System.out.println("On this day slang word!");
            System.out.println(word + " is " + data.get(word));
        }
        return word;
    }

    public static void slangWordQuiz() throws IOException, InterruptedException{
        TreeMap<String, Set<String>> quiz = new TreeMap<String, Set<String>>();
        for(int i = 0; i < 4; i++){
            String word = onThisDaySlang(true);
            quiz.put(word, data.get(word));
        }
        int randomAnswers = new Random().nextInt(4);

        ArrayList<String> keysArray = new ArrayList<String>(quiz.keySet());
        String question = keysArray.get(randomAnswers);
        Set<Map.Entry<String, Set<String>>> q = quiz.entrySet();

        String str = "";
        while (!str.matches("[ABCD]{1}")) {
            process.inheritIO().start().waitFor();
            System.out.println("Welcome to slang word quiz");
            System.out.println("Choose definition of this slang word: " + question);
            int i = 0;
            for(Map.Entry<String, Set<String>> item: q){
                System.out.println((char)(i + 65) + ". " + item.getValue().iterator().next());
                ++i;
            }
            System.out.print("Choose correct answer (A, B, C or D): ");
            str = scan.nextLine().trim();
        }
        int choice = (int)str.toCharArray()[0] - 65;

        if (choice == randomAnswers)
            System.out.println("Correct Answer! Congratulation!!!");
        else {
            char answers = (char)(randomAnswers + 65);
            System.out.println("Incorrect! Good luck for next time!");
            System.out.println("Correct answer is: " + answers + ". " + quiz.get(question));
        }
    }

    public static void definitionQuiz() throws IOException, InterruptedException{
        TreeMap<String, Set<String>> quiz = new TreeMap<String, Set<String>>();
        for(int i = 0; i < 4; i++){
            String word = onThisDaySlang(true);
            quiz.put(word, data.get(word));
        }
        int randomAnswers = new Random().nextInt(4);

        ArrayList<String> keysArray = new ArrayList<String>(quiz.keySet());
        String question = keysArray.get(randomAnswers);
        Set<Map.Entry<String, Set<String>>> q = quiz.entrySet();

        String str = "";
        while (!str.matches("[ABCD]{1}")) {
            process.inheritIO().start().waitFor();
            System.out.println("Welcome to slang word quiz follow definition");
            System.out.println("Choose slang word has this definition: " + quiz.get(question).iterator().next());
            int i = 0;
            for(Map.Entry<String,Set<String>> item: q){
                System.out.println((char)(i + 65) + ". " + item.getKey());
                ++i;
            }
            System.out.print("Choose correct answer (A, B, C or D): ");
            str = scan.nextLine().trim();
        }
        int choice = (int)str.toCharArray()[0] - 65;

        if (choice == randomAnswers)
            System.out.println("Correct Answer! Congratulation!!!");
        else {
            char answers = (char)(randomAnswers + 65);
            System.out.println("Incorrect! Good luck for next time!");
            System.out.println("Correct answer is: " + answers + ". " + question);
        }
    }

    public static void main(String [] args) throws IOException, InterruptedException{
        inputFile("MyDictionary.data");
        inputHistory();
        String choice;

        while(true){
            process.inheritIO().start().waitFor();
			System.out.println();
			System.out.println("1. Search by slang word");
			System.out.println("2. Search slang word follow definition");
			System.out.println("3. Show Search History");
			System.out.println("4. Add a new slang word");
			System.out.println("5. Edit a slang word");
			System.out.println("6. Delete a slang word");
			System.out.println("7. Reset the slang word dictionary");
            System.out.println("8. Random a slang word (On this day slang word)");
            System.out.println("9. Slang word quiz");
            System.out.println("10. Slang word quiz follow definition");
			System.out.println("11. Exit");
			System.out.print("Choose your action: ");
			choice = scan.nextLine();

            if (choice.equals("1")){
                searchSlangWord();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("2")){
                searchDefinition();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("3")){
                printHistory();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("4")){
                addSlangWord();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("5")){
                editSlangWord();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("6")){
                deleteSlangWord();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("7")){
                resetDictionary();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("8")){
                onThisDaySlang(false);
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("9")){
                slangWordQuiz();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("10")){
                definitionQuiz();
                System.out.print("Choose any key to back to menu: ");
                choice = scan.nextLine();
            }
            else if (choice.equals("11"))
                break;
        }
    }
}
