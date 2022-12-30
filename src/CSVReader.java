import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVReader {
    String filename;
    String[] head;
    BufferedReader fp;
    HashMap<Integer, ArrayList<String>> fileHashMap;
    boolean checker;

    CSVReader(String preFilename) {
        filename = preFilename;
        fileHashMap = new HashMap<>();
        checker = true;
    }
    void makeFile() {
        try {
            try {
                fp = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException ex) {
                checker = false;
                return;
            }
            try {
                head = fp.readLine().split(",");
            } catch (IOException ex) {
                checker = false;
            } finally {
                String[] cols;
                ArrayList<String> col;
                int num = 0;
                while(fp.ready()){
                    col = new ArrayList<>();
                    cols = fp.readLine().split(",");
                    for (int i = 0; i < cols.length; ++i) {
                        col.add(cols[i]);
                    }
                    fileHashMap.put(num, col);
                    num++;
                }
            }
            fp.close();
        } catch (Exception e){
            checker = false;
        }
    }
    String[] getHead() {
        return head;
    }
    HashMap<Integer, ArrayList<String>> returnfileHashMap() {
        return fileHashMap;
    }
}