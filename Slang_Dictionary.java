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
                Set<String> def = new HashSet<>(Arrays.stream(definition).collect(Collectors.toSet()));
                data.put(str[0], def);
            }
            line = in.readLine();
        }
        in.close();
    }

    public static void saveDictionary() throws IOException{
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

    public static void searchSlang() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to search: ");
        String input = scan.nextLine().trim().toUpperCase();
        addHistory(input);

        Set<String> def = data.get(input);
        if (def == null){
            Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
            for(Map.Entry<String,Set<String>> word: dictionary){
                String w = word.getKey();
                if (w.contains(input.toUpperCase())){
                    System.out.println("\t" + "+ " + w);
                }
            }

            System.out.println("The word " + input + " does not exist!");
        }
        else {
            System.out.println("Had found!");
            System.out.println("\t" + "+ " + input + " is " + def);
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
                    System.out.println("\t" + "+ " + word.getKey());
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

        BufferedReader line = new BufferedReader(new FileReader(new File("History.txt")));
        String word;
        while((word = line.readLine()) != null){
            history.add(word);
        }  
        line.close();  
    }

    private static void saveHistory() throws IOException{
        FileWriter save = new FileWriter(new File("History.txt"));
        for (String word : history){
            save.write(word + "\n");
        }
        save.close();
    }

    public static void addHistory(String word) throws IOException{
        history.add(word);
        saveHistory();
    }

    public static void printHistory(){
        if (history.isEmpty())
            System.out.println("History is empty");
        else 
            for(String word : history)
                System.out.println("\t" + "+ " + word);
    }

    public static void addSlangWord() throws IOException, InterruptedException{
        Set<String> definition = new HashSet<String>();
        process.inheritIO().start().waitFor();
        System.out.print("Enter a slang word that you want to add: ");
        String word = scan.nextLine().trim().toUpperCase();

        String choice = "0";
        if (data.containsKey(word)){
            System.out.println();
            System.out.println("Word exist in dictionary!");
            System.out.println("1. Overwrite slang word");
            System.out.println("2. Duplicate to new slang word");
            System.out.print("Enter your choice: ");
            choice = scan.nextLine();
        }
        
        System.out.println();
        String numOfDef = "";
        while(!numOfDef.matches("^\\d+$")){
            System.out.print("Enter number of definition of slang word you want to add (it must be an integer): ");
            scan.nextLine();
        }
        int c = Integer.parseInt(numOfDef);

        for (int i = 0; i < c; ++i){
            int k = i + 1;
            System.out.print("Enter " + k + "st definition: ");
            definition.add(scan.nextLine());
        }

        if (choice.equals("0") || choice.equals("1"))
            data.put(word, definition);
        else if (choice.equals("2"))
            data.put(word.toLowerCase(), definition);   
        else
            return;

        System.out.println("Add new slang word success!");
        saveDictionary();
    }

    public static void main(String [] args) throws IOException, InterruptedException{
        inputFile("MyDictionary.txt");
        inputHistory();
        String choice;

        while(true){
            process.inheritIO().start().waitFor();
			System.out.println();
			System.out.println("1. Search by slang word");
			System.out.println("2. Search slang word follow definition");
			System.out.println("3. Show Search History");
			System.out.println("4. Add new slang word");
			System.out.println("5. Edit slang word");
			System.out.println("6. Delete slang word");
			System.out.println("7. Reset slang word Dictionary");
            System.out.println("8. On this day slang word");
            System.out.println("9. slang word quiz");
            System.out.println("10. slang word quiz follow definition");
			System.out.println("Choose difference key to exit");
			System.out.print("Choose your action: ");
			choice = scan.nextLine();

            if (choice.equals("1")){
                searchSlang();
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
            else 
                break;
        }
    }
}
