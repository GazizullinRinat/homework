import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;

public class AllInfoYear {
    HashMap<Integer, HashMap<Integer, MonthlyReport>> allMonths;
    HashMap<Integer, YearlyReport> allYears;
    AllInfoYear() {
        allMonths = new HashMap<>();
        allYears = new HashMap<>();
    }
    void addYear(String preFilename) {
        YearlyReport newYear = new YearlyReport();
        if (newYear.addYear(preFilename)) {
            allYears.put(newYear.yearNum, newYear);
            System.out.println("Добавлен годовой отчет за " + newYear.yearNum + " год");
        } else {
            newYear = null;
        }
    }
    void addMonth(String year, String preFilename) {
        for (int i = 1; i <= 12; i++) {
            MonthlyReport newMonth = new MonthlyReport();
            String j;
            if (i < 10) {
                j = "0" + i;
            } else {
                j = i + "";
            }
            if (newMonth.addMonth(preFilename, year, j)) {;
                HashMap<Integer, MonthlyReport> preMonths = new HashMap<>();
                if (allMonths.containsKey(newMonth.yearNum)) {
                    allMonths.get(newMonth.yearNum).put(newMonth.monthNum, newMonth);
                } else {
                    preMonths.put(newMonth.monthNum, newMonth);
                    allMonths.put(newMonth.yearNum, preMonths);
                }
                System.out.println("Добавлен месячый отчет за " + newMonth.monthNum + " месяц и "
                        + newMonth.yearNum + " год");
            }
        }
    }
    void getInfoByYears() {
        for (int year: allYears.keySet()) {
            System.out.println("-*- " + year + " год -*-");
            boolean checker = true;
            int allIncome = 0;
            int allConsumption = 0;
            for (int num: allYears.get(year).year.keySet()) {
                if (!checker) {
                    checker = true;
                    continue;
                } else {
                    ArrayList<String>  line1 = allYears.get(year).year.get(num);
                    ArrayList<String> line2 = allYears.get(year).year.get(num + 1);
                    System.out.println(" · Месяц - " + Integer.parseInt(line1.get(0)) + " ·");
                    int money1 = Integer.parseInt(line1.get(1));
                    int money2 = Integer.parseInt(line2.get(1));
                    int income;
                    int consumption;
                    if (line1.get(2).toUpperCase().equals("TRUE")) {
                        consumption = money1;
                        income = money2;
                    } else {
                        consumption = money2;
                        income = money1;
                    }
                    System.out.println("Прибыль - " + (income - consumption));
                    allIncome += income;
                    allConsumption += consumption;
                    checker = false;
                }
            }
            System.out.println("Общая прибыль - " + (allIncome - allConsumption));
            int size = allYears.get(year).year.size() / 2;
            System.out.println("Средний доход - " + (allIncome / size));
            System.out.println("Средний расход - " + (allConsumption / size));
        }
    }

    void getInfoByMonths() {
        for (int year : allMonths.keySet()) {
            System.out.println("-*- " + year + " год -*-");
            System.out.println(allMonths.get(year).size());
            for (int month: allMonths.get(year).keySet()) {
                System.out.println(" · Месяц - " + month + " ·");
                double maxPerOne = 0;
                double max = 0;
                String maxName = "";
                double maxConsumption = 0;
                HashMap<Integer, ArrayList<String>> monthHash = allMonths.get(year).get(month).month;
                for (ArrayList<String> line: monthHash.values()) {
                    if (line.get(1).toUpperCase().equals("FALSE")) {
                        double cost = Double.parseDouble(line.get(3));
                        double count = Double.parseDouble(line.get(2));
                        if (cost > maxPerOne) {
                            maxPerOne = cost;
                            maxName = line.get(0);
                            max = cost * count;
                        }
                    } else {
                        double consumption = Double.parseDouble(line.get(3)) * Double.parseDouble(line.get(2));
                        if (maxConsumption < consumption) {
                            maxConsumption = consumption;
                        }
                    }
                }
                System.out.println("Самый прибыльный товар - " + maxName + " - и его прибыль - " + max);
                System.out.println("Самая большая трата - " + maxConsumption);
            }
        }
    }
    void getResult(int year) {
        if (allMonths.containsKey(year) && allYears.containsKey(year)) {
            if (allMonths.get(year).size() == allYears.get(year).year.size() / 2) {
                int allIncome;
                int allConsumption;
                for (int monthNum: allMonths.get(year).keySet()) {
                    allIncome = 0;
                    allConsumption = 0;
                    HashMap<Integer, ArrayList<String>> month = allMonths.get(year).get(monthNum).month;
                    for (ArrayList<String> line : month.values()) {
                        if (line.get(1).toUpperCase().equals("TRUE")) {
                            allConsumption += Integer.parseInt(line.get(2)) * Integer.parseInt(line.get(3));
                        } else {
                            allIncome += Integer.parseInt(line.get(2)) * Integer.parseInt(line.get(3));
                        }
                    }
                    if (monthNum == Integer.parseInt(allYears.get(year).year.get((monthNum - 1) * 2).get(0))) {
                        if (allYears.get(year).year.get((monthNum - 1) * 2).get(2).toUpperCase().equals("TRUE")) {
                            if (allIncome == Integer.parseInt(allYears.get(year).year.get((monthNum - 1) * 2 + 1).get(1))
                                    && allConsumption ==
                                    Integer.parseInt(allYears.get(year).year.get((monthNum - 1) * 2).get(1))) {
                                System.out.println("Месяц " + monthNum + " совпадает");
                            } else {
                                System.out.println("Месяц " + monthNum + " не совпадает");
                            }
                        } else {
                            if (allIncome == Integer.parseInt(allYears.get(year).year.get((monthNum - 1) * 2).get(1))
                                    && allConsumption ==
                                    Integer.parseInt(allYears.get(year).year.get((monthNum - 1) * 2 + 1).get(1))) {
                                System.out.println("Месяц " + monthNum + " совпадает");
                            } else {
                                System.out.println("Месяц " + monthNum + " не совпадает");
                            }
                        }
                    } else {
                        System.out.println("Не совпадают месяца");
                        return;
                    }
                }
            } else {
                System.out.println("Не совпадают месяца");
            }
        } else {
            System.out.println("Отсуствует нужный год");
        }
    }
}
