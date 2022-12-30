import java.util.HashMap;
import java.util.ArrayList;
public class MonthlyReport {
    HashMap<Integer, ArrayList<String>> month;
    CSVReader csvReader;
    int monthNum;
    int yearNum;
    MonthlyReport() {
        month = new HashMap<>();
    }
    boolean checkMonthHashMap(HashMap<Integer, ArrayList<String>> preHashMap) {
        for (Integer key: preHashMap.keySet()) {
            ArrayList<String> toCheck = preHashMap.get(key);
            if (!(toCheck.size() == 4) || !(checkInt(toCheck.get(2)))
                    || !(checkInt(toCheck.get(3))) || !(toCheck.get(1).toUpperCase().equals("FALSE")
                    || toCheck.get(1).toUpperCase().equals("TRUE"))) {
                return false;
            }
        }
        return true;
    }
    boolean checkHead(String[] head) {
        if (head.length == 4 && head[0].equals("item_name") && head[1].equals("is_expense")
                && head[2].equals("quantity") && head[3].equals("sum_of_one")) {
            return true;
        }
        return false;
    }
    boolean checkInt(String preDouble) {
        try {
            Integer.parseInt(preDouble);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    boolean checkNameMonth(String fileName) {
        String[] preChecker = fileName.split("/")[fileName.split("/").length - 1].split("\\.");
        if (preChecker.length == 3 && preChecker[2].equals("csv") && preChecker[0].equals("m")) {
            try {
                return checkInt(preChecker[1]);
            } catch (NumberFormatException ex) {
                return false;
            }
        } else {
            return false;
        }
    }
    int getInt(String preNum) {
        return Integer.parseInt(preNum);
    }
    boolean addMonth(String filename, String yearStr, String monthStr) {
        csvReader = new CSVReader(filename + "/m." + yearStr + monthStr + ".csv");
        filename = filename + "/m." + yearStr + monthStr + ".csv";
        csvReader.makeFile();
        if (csvReader.checker) {
            if (checkNameMonth(filename) && checkInt(yearStr)) {
                HashMap<Integer, ArrayList<String>> preYear = csvReader.returnfileHashMap();
                if (checkHead(csvReader.getHead()) && checkMonthHashMap(preYear)) {
                    yearNum = getInt(yearStr);
                    monthNum = getInt(monthStr);
                    month = csvReader.returnfileHashMap();
                    System.out.println("Информация за " + monthNum + " месяц и " + yearNum + " год успешно сохранена");
                    return true;
                } else {
                    System.out.println("Файл неправильно заполнен");
                    return false;
                }
            } else {
                System.out.println("Файл неправильно назван");
                return false;
            }
        } else {
            return false;
        }
    }
}
