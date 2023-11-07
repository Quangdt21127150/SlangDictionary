import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Slang_Dictionary{
    public static TreeMap<String, Set<String>> data = new TreeMap<String, Set<String>>();
    public static Vector<String> history = new Vector<String>();
    static Scanner scan = new Scanner(System.in);
    static ProcessBuilder process = new ProcessBuilder("cmd", "/c", "cls");

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

    public static void searchSlang() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter slang word that you want to search: ");
        String line = scan.nextLine().trim().toUpperCase();
        //addHistory(word);

        Set<String> def = data.get(line);
        if (def == null){
            Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
            for(Map.Entry<String,Set<String>> word: dictionary){
                String w = word.getKey();
                if (w.contains(line.toUpperCase())){
                    System.out.println("\t" + "+ " + w);
                }
            }

            System.out.println("The word " + line + " does not exist!");
        }
        else {
            System.out.println("Had found!");
            System.out.println("\t" + "+ " + line + " is " + def);
        }
    }

    public static void searchDefinition() throws IOException, InterruptedException{
        process.inheritIO().start().waitFor();
        System.out.print("Enter definition you want to search: ");
        String def = scan.nextLine().trim();

        Boolean flag = false;
        Set<Map.Entry<String, Set<String>>> dictionary = data.entrySet();
        for(Map.Entry<String,Set<String>> word: dictionary){
            Set<String> defList = word.getValue();
            for(String i : defList){
                if (i.contains(def) || i.contains(def.toUpperCase()) || i.contains(def.toLowerCase())){
                    flag = true;
                    System.out.println("\t" + "+ " + i);
                }
            }
        }

        if(!flag){
            System.out.println("Can not find Slang Word has this definition!");
        }
    }

    public static void main(String [] args) throws IOException, InterruptedException{
        inputFile("MyDictionary.txt");

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
            System.out.println("9. Slang word quiz");
            System.out.println("10. Slang word quiz follow definition");
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
            else 
                break;
        }
    }
}
