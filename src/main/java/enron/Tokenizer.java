package enron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private HashMap<String, Boolean> map = new HashMap<String, Boolean>();

    public Tokenizer() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/stopwords.txt"));
            String data = reader.readLine();
            String[] words = data.split(",");
            for (String w: words) {
                map.put(w, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Set<String> extract(String data) {
        Pattern p = Pattern.compile("([a-zA-Z]+)(\\s+)*");
        Matcher m = p.matcher(data);

        Set<String> words = new HashSet<String>();

        while (m.find()) {
            String result = m.group(1);
            if (result == null) continue;

            result = result.toLowerCase();

            if (result.length() < 2) continue;
            if (map.containsKey(result)) continue;
            words.add(result);
        }

        return words;
    }


}
