import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AllInfoYear years = new AllInfoYear();
        while (true) {
            menu();
            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println("Введите путь к нужной папки");
                String filename = scanner.next();
                System.out.println("За какой год вы хотите внести изменения?");
                String year = scanner.next();
                years.addMonth(year, filename);
            } else if (command == 2) {
                System.out.println("Введите путь к нужной папки");
                String fileName = scanner.next();
                System.out.println("За какой год вы хотите внести изменения?");
                String year = scanner.next();
                years.addYear(fileName + "/y." + year + ".csv");
            } else if (command == 3) {
                System.out.println("За какой год вы хотите получить сверку?");
                int year = scanner.nextInt();
                years.getResult(year);
            } else if (command == 4) {
                years.getInfoByMonths();
            } else if (command == 5) {
                years.getInfoByYears();
            } else if (command == 0) {
                System.out.println("Выход");
                return;
            } else {
                System.out.println("Такой команды не существует или не была найдена :(");
            }
        }
    }

    public static void menu() {
        System.out.println("1 - Считать месячный отчет");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Произвести сверку за год");
        System.out.println("4 - Получить месячный отчет");
        System.out.println("5 - Получить годовой отчет");
        System.out.println("0 - Выход");
    }
}