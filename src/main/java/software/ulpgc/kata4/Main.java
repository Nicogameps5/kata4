package software.ulpgc.kata4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("D:/Descargas/title.basics.tsv");
        List<Title> titles = new FileTitleLoader(file, new TsvTitleDeserializer()).load();
        Map<Integer, Integer> stats = new HashMap<>();
        for (Title title : titles) {
            stats.put(title.year(), stats.getOrDefault(title.year(), 0) + 1);
        }

        for (int year : stats.keySet()) {
            System.out.println(year + "\t" + stats.get(year));
        }

    }
}