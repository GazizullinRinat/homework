import java.util.HashMap;
import java.util.ArrayList;
public class YearlyReport {
    HashMap<Integer, ArrayList<String>> year;
    CSVReader csvReader;
    int yearNum;
    YearlyReport() {
        year = new HashMap<>();
    }
    boolean checkYearHashMap(HashMap<Integer, ArrayList<String>> preHashMap) {
        boolean checker = true;
        for (Integer key: preHashMap.keySet()) {
            ArrayList<String> toCheck = preHashMap.get(key);
            if (!(toCheck.size() == 3) || !(checkInt(toCheck.get(0)))
                    || !(checkInt(toCheck.get(1))) || !(toCheck.get(2).toUpperCase().equals("FALSE")
                    || toCheck.get(2).toUpperCase().equals("TRUE"))) {
                return false;
            } else {
                if (checker) {
                    if (!toCheck.get(0).equals(preHashMap.get(key + 1).get(0))) {
                        return false;
                    } else {
                        checker = false;
                    }
                } else {
                    checker = true;
                }
                }
            }
        return true;
        }
    boolean checkInt(String preDouble) {
        try {
            Integer.parseInt(preDouble);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    boolean checkHead(String[] head) {
        if (head.length == 3 && head[0].equals("month") && head[1].equals("amount") && head[2].equals("is_expense")) {
            return true;
        }
        return false;
    }
    boolean checkNameYear(String fileName) {
        String[] preChecker = fileName.split("/")[fileName.split("/").length - 1].split("\\.");
        if (preChecker.length == 3 && preChecker[2].equals("csv") && preChecker[0].equals("y")) {
            try {
                return checkInt(preChecker[1]);
            } catch (NumberFormatException ex) {
                return false;
            }
        } else {
            return false;
        }
    }
    int getYear(String fileName) {
        String[] preChecker = fileName.split("/")[fileName.split("/").length - 1].split("\\.");
        return Integer.parseInt(preChecker[1]);
    }
    boolean addYear(String filename) {
        csvReader = new CSVReader(filename);
        csvReader.makeFile();
        if (checkNameYear(filename) && csvReader.checker) {
            HashMap<Integer, ArrayList<String>> preYear = csvReader.returnfileHashMap();
            if (checkHead(csvReader.getHead()) && checkYearHashMap(preYear)) {
                year = preYear;
                yearNum = getYear(filename);
                System.out.println("Информация за " + yearNum + " успешно сохранена");
                return true;
            } else {
                System.out.println("Файл неправильно заполнен");
                return false;
            }
        } else {
            System.out.println("Файл неправильно назван или пуст");
            return false;
        }
    }
}
