import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SlangDictionary{
    public static TreeMap<String, List<String>> data = new TreeMap<>();
    private static SlangDictionary dict = new SlangDictionary();
	private static String FILE_SLANGWORD = "file data/MyDictionary.data";
	private String FILE_ORIGINAL_SLANGWORD = "file data/slang.data";
	private String FILE_HISTORY = "file data/History.data";

    private SlangDictionary() {
		try {
			inputFile(FILE_SLANGWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SlangDictionary crateDictionary() {
		if (dict == null) {
			synchronized (SlangDictionary.class) {
				if (dict == null) {
					dict = new SlangDictionary();// instance will be created at request time
				}
			}
		}
		return dict;
	}

    public static void inputFile(String file) throws IOException{
        data.clear();
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
                List<String> def = new ArrayList<>(Arrays.stream(definition).collect(Collectors.toSet()));
                data.put(str[0].trim(), def);
            }
            line = in.readLine();
        }
        in.close();
    }

    public static void saveDictionary(){
        try {
            FileWriter save = new FileWriter(new File(FILE_SLANGWORD));
            Set<Map.Entry<String, List<String>>> dictionary = data.entrySet();
            for(Map.Entry<String, List<String>> word : dictionary){
                save.write(word.getKey() + "`");
                List<String> definition = word.getValue();
                String lastDef = definition.stream().reduce((a, b) -> b).get();
                for (String def : definition){
                    if (def.equals(lastDef))
                        save.write(def + "\n");
                    else 
                        save.write(def + "| ");
                }
            };

            save.close();
        } catch (Exception e) {}
    }

    public String[][] getData() {
		String[][] s = new String[data.size()][3];
		Set<String> slagListSet = data.keySet();
		Object[] slagList = slagListSet.toArray();
		int index = 0;
		for (int i = 0; i < data.size(); ++i) {
			s[i][0] = String.valueOf(i + 1);
			s[i][1] = (String) slagList[index];
			List<String> meaning = data.get(slagList[index]);
			s[i][2] = meaning.get(0);
			for (int j = 1; j < meaning.size(); j++) {
				if (i < data.size())
					++i;
				s[i][0] = String.valueOf(i);
				s[i][1] = (String) slagList[index];
				s[i][2] = meaning.get(j);
			}
			++index;
		}
		return s;
	}

	public String[][] getMeaning(String key) {
		List<String> listMeaning = data.get(key);
		if (listMeaning == null)
			return null;
		int size = listMeaning.size();
		String s[][] = new String[size][3];
		for (int i = 0; i < size; ++i) {
			s[i][0] = String.valueOf(i + 1);
			s[i][1] = key;
			s[i][2] = listMeaning.get(i);
		}
		return s;
	}

	public void set(String slag, String oldValue, String newValue) {
		List<String> meaning = data.get(slag);
		int index = meaning.indexOf(oldValue);
		meaning.set(index, newValue);
		saveDictionary();
	}

	public void saveHistory(String slag, String meaning) throws Exception {
		// String file = "history.txt";
		File file1 = new File(FILE_HISTORY);
		FileWriter fr = new FileWriter(file1, true);
		fr.write(slag + "`" + meaning + "\n");
		fr.close();
	}

	public String[][] readHistory() {
		List<String> historySlag = new ArrayList<>();
		List<String> historyDefinition = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(FILE_HISTORY));
			scanner.useDelimiter("`");
			String temp = scanner.next();
			String[] part = scanner.next().split("\n");
			historySlag.add(temp);
			historyDefinition.add(part[0]);
			while (scanner.hasNext()) {
				temp = part[1];
				part = scanner.next().split("\n");
				historySlag.add(temp);
				historyDefinition.add(part[0]);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = historySlag.size();
		String[][] s = new String[size][3];
		for (int i = 0; i < size; ++i) {
			s[size - i - 1][0] = String.valueOf(size - i);
			s[size - i - 1][1] = historySlag.get(i);
			s[size - i - 1][2] = historyDefinition.get(i);
		}
		return s;
	}

	public String[][] findDefinition(String query) {
		// Get all slang contain key
		List<String> keyList = new ArrayList<>();
		List<String> meaningList = new ArrayList<>();
		for (Entry<String, List<String>> entry : data.entrySet()) {
			List<String> meaning = entry.getValue();
			for (int i = 0; i < meaning.size(); ++i) {
				if (meaning.get(i).toLowerCase().contains(query.toLowerCase())) {
					keyList.add(entry.getKey());
					meaningList.add(meaning.get(i));
				}
			}
		}
		int size = keyList.size();
		String[][] s = new String[size][3];

		for (int i = 0; i < size; ++i) {
			s[i][0] = String.valueOf(i);
			s[i][1] = keyList.get(i);
			s[i][2] = meaningList.get(i);
		}
		return s;
	}

	public void reset() {
		try {
			inputFile(FILE_ORIGINAL_SLANGWORD);
			inputFile(FILE_HISTORY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String slag, String value) {
		List<String> meaningList = data.get(slag);
		int index = meaningList.indexOf(value);
		if (meaningList.size() == 1) {
			data.remove(slag);
		} else {
			meaningList.remove(index);
			data.put(slag, meaningList);
		}
	    saveDictionary();
	}

	public void addNew(String slag, String meaning) {
		List<String> meaningList = new ArrayList<>();
		meaningList.add(meaning);
		data.put(slag, meaningList);
		saveDictionary();
	}

	public void addDuplicate(String slag, String meaning) {
		List<String> meaningList = data.get(slag);
		meaningList.add(meaning);
		data.put(slag, meaningList);
		saveDictionary();
	}

	public void addOverwrite(String slag, String meaning) {
		List<String> meaningList = data.get(slag);
		meaningList.set(0, meaning);
		data.put(slag, meaningList);
		saveDictionary();
	}

	public boolean checkSlang(String slag) {
		for (String keyIro : data.keySet()) {
			if (keyIro.equals(slag))
				return true;
		}
		return false;
	}

	public String[] random() {
		// Random a number
		int minimun = 0;
		int maximun = data.size() - 1;
		int rand = randInt(minimun, maximun);
		// Get slang meaning
		String[] s = new String[2];
		int index = 0;
		for (String key : data.keySet()) {
			if (index == rand) {
				s[0] = key;
				s[1] = data.get(key).get(0);
				break;
			}
			index++;
		}
		return s;
	}

	public static int randInt(int minimum, int maximum) {
		return (minimum + (int)(Math.random() * maximum));
	}

	public String[] quiz(int type) {
		String s[] = new String[6];
		if (type == 1) {
			// Random a number
			String[] slangRandom = this.random();
			s[0] = slangRandom[0];
			int rand = randInt(1, 4);
			s[rand] = slangRandom[1];
			s[5] = slangRandom[1];
			for (int i = 1; i <= 4; ++i) {
				if (rand == i)
					continue;
				else {
					String[] slangRand = this.random();
					while (slangRand[0] == s[0]) {
						slangRand = this.random();
					}
					s[i] = slangRand[1];
				}
			}
		} else {
			// Random a number
			String[] slangRandom = this.random();
			s[0] = slangRandom[1];
			int rand = randInt(1, 4);
			s[rand] = slangRandom[0];
			s[5] = slangRandom[0];
			for (int i = 1; i <= 4; ++i) {
				if (rand == i)
					continue;
				else {
					String[] slangRand = this.random();
					while (slangRand[0] == s[0]) {
						slangRand = this.random();
					}
					s[i] = slangRand[0];
				}
			}
		}

		return s;
	}
}
