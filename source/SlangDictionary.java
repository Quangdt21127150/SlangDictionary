package source;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SlangDictionary {
	//Reference about TreeMap: https://viettuts.vn/java-collection/treemap-trong-java
	private TreeMap<String, List<String>> data = new TreeMap<>(); 
	private static SlangDictionary dict = new SlangDictionary();
	private String FILE_SLANGWORD = "MyDictionary.data";
	private String FILE_ORIGINAL_SLANGWORD = "slang.data";
	private String FILE_HISTORY = "History.data";

	SlangDictionary() {
		try {
			readFile(FILE_SLANGWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Reference about synchronized in Java: https://viblo.asia/p/java-synchronized-blocks-jlA7GK04vKZQ
	public static SlangDictionary getInstance() {
		if (dict == null) {
			synchronized (SlangDictionary.class) {
				if (dict == null) {
					dict = new SlangDictionary();// instance will be created at request time
				}
			}
		}
		return dict;
	}

	void saveFile() {
		try(FileWriter fw = new FileWriter(new File(FILE_SLANGWORD))){
            Set<Map.Entry<String, List<String>>> dictionary = data.entrySet();
            for(Map.Entry<String, List<String>> item : dictionary){
                fw.write(item.getKey() + "`");
                List<String> definition = item.getValue();
                String lastDef = definition.stream().reduce((a, b) -> b).get();
                for (String def : definition){
                    if (def.equals(lastDef)){
                        fw.write(def + "\n");
                    }
                    else {
                        fw.write(def + "| ");
                    }
                }
            };

            fw.close();
        }
        catch (Exception e){
            System.out.println("Error message: " + e);
        }
	}

	public void readFile(String file) throws Exception {
		data.clear();
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
    
		String w;
		while((w = br.readLine()) != null){
			String[] wordAndDef = w.split("`");

			if (wordAndDef.length == 2){
				String[] definition = wordAndDef[1].split("\\|");
				List<String> def = new ArrayList<>(Arrays.stream(definition).collect(Collectors.toSet()));
				data.put(wordAndDef[0], def);
			}
		}  
		br.close();
	}

	public String[][] getData() {
		String[][] s = new String[data.size()][3];
		Set<String> slangListSet = data.keySet();
		Object[] slangList = slangListSet.toArray();
		int index = 0;
		for (int i = 0; i < data.size(); ++i) {
			s[i][0] = String.valueOf(i);
			s[i][1] = (String) slangList[index];
			List<String> meaning = data.get(slangList[index]);
			s[i][2] = meaning.get(0);
			for (int j = 1; j < meaning.size(); j++) {
				if (i < data.size())
					++i;
				s[i][0] = String.valueOf(i);
				s[i][1] = (String) slangList[index];
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
			s[i][0] = String.valueOf(i);
			s[i][1] = key;
			s[i][2] = listMeaning.get(i);
		}
		return s;
	}

	public void set(String slang, String oldValue, String newValue) {
		List<String> meaning = data.get(slang);
		int index = meaning.indexOf(oldValue);
		meaning.set(index, newValue);
		this.saveFile();
	}

	public void saveHistory(String slang, String meaning) throws Exception {
		File file1 = new File(FILE_HISTORY);
		FileWriter fr = new FileWriter(file1, true);
		fr.write(slang + "`" + meaning + "\n");
		fr.close();
	}

	public String[][] readHistory() {
		List<String> historySlang = new ArrayList<>();
		List<String> historyDefinition = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(FILE_HISTORY));
			scanner.useDelimiter("`");
			String temp = scanner.next();
			String[] part = scanner.next().split("\n");
			historySlang.add(temp);
			historyDefinition.add(part[0]);
			while (scanner.hasNext()) {
				temp = part[1];
				part = scanner.next().split("\n");
				historySlang.add(temp);
				historyDefinition.add(part[0]);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = historySlang.size();
		String[][] s = new String[size][3];
		for (int i = 0; i < size; ++i) {
			s[size - i - 1][0] = String.valueOf(size - i);
			s[size - i - 1][1] = historySlang.get(i);
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
			readFile(FILE_ORIGINAL_SLANGWORD);
			this.saveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String slang, String value) {
		List<String> meaningList = data.get(slang);
		int index = meaningList.indexOf(value);
		if (meaningList.size() == 1) {
			data.remove(slang);
		} else {
			meaningList.remove(index);
			data.put(slang, meaningList);
		}
		this.saveFile();
	}

	public void addNew(String slang, String meaning) {
		List<String> meaningList = new ArrayList<>();
		meaningList.add(meaning);
		data.put(slang, meaningList);
		this.saveFile();
	}

	public void addDuplicate(String slang, String meaning) {
		List<String> meaningList = data.get(slang);
		meaningList.add(meaning);
		data.put(slang, meaningList);
		this.saveFile();
	}

	public void addOverwrite(String slang, String meaning) {
		List<String> meaningList = data.get(slang);
		meaningList.set(0, meaning);
		data.put(slang, meaningList);
		this.saveFile();
	}

	public boolean checkSlang(String slang) {
		for (String keyIro : data.keySet()) {
			if (keyIro.equals(slang))
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
