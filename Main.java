import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int seats = scanner.nextInt();

        String[][] hall = new String[rows + 1][seats + 1];
        hall = fillSeats(hall, rows, seats);
        int[] prevRowAndSeat = {0, 0};
        int[] curRowAndSeat = {0, 0};
        int quantity = 0;
        int currentIncome = 0;
        int numSeats = 0;
        int numRows = 0;
        int price = 0;
        int counter = 0;

        showMenu(hall, rows, seats, price, currentIncome, quantity, curRowAndSeat, prevRowAndSeat);
    }

    public static void showMenu(String[][] hall, int rows, int seats, int price, int currentIncome, int quantity, int[] curRowAndSeat, int[] prevRowAndSeat) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.println();

        int input = scanner.nextInt();

        switch (input) {
            case 1:

                showSeats(hall, rows, seats, curRowAndSeat);
                showMenu(hall, rows, seats, price, currentIncome, quantity, curRowAndSeat, prevRowAndSeat);
                break;
            case 2:
                curRowAndSeat = setPlace(rows, seats);
                while (checkSeat(prevRowAndSeat, curRowAndSeat)){
                    curRowAndSeat = setPlace(rows, seats);
                }
                quantity += 1;
                hall = fill(hall, rows, seats, curRowAndSeat);
                prevRowAndSeat = curRowAndSeat;
                price = buyTicket(hall, rows, seats, price, curRowAndSeat);
                currentIncome = getCurrentIncome(currentIncome, price);

                showMenu(hall, rows, seats, price, currentIncome, quantity, curRowAndSeat, prevRowAndSeat);
                break;
            case 3:
                currentIncome = showStatistics(rows, seats, price, currentIncome, quantity);
                showMenu(hall, rows, seats, price, currentIncome, quantity, curRowAndSeat, prevRowAndSeat);
                break;
            case 0:
                break;
        }
        System.out.println();
    }
    public static int[] checkInput(int rows, int seats, String rowMsg, String seatMsg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(rowMsg);
        int rowNum = 0;
        int seatNum = 0;
        rowNum = scanner.nextInt();
        System.out.println(seatMsg);
        seatNum = scanner.nextInt();
        if (rowNum > rows || seatNum > seats || seatNum < 0 || rowNum < 0) {
            System.out.println("Wrong input!");
            checkInput(rows, seats, rowMsg, seatMsg);
        }
        int[] rowAndSeat = {rowNum, seatNum};
        return rowAndSeat;
    }
    public static String[][] fillSeats(String[][] hall, int rows, int seats) {
        for (int i = 1; i <= rows; i++){
            for (int j = 1; j <= seats; j++) {
                hall[i][j] = "S";
            }
        }
        return hall;
    }
    public static String[][] showSeats(String[][] hall, int rows, int seats, int[] c) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= rows; i++){
            System.out.print(i + " ");
            for (int j = 1; j <= seats; j++) {
                if (c[0] == i && c[1] == j) {
                    hall[i][j] = "B";
                }
                System.out.print(hall[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        return hall;
    }
    public static String[][] fill(String[][] hall, int rows, int seats, int[] curRowAndSeat) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                if (curRowAndSeat[0] == i && curRowAndSeat[1] == j) {
                    hall[i][j] = "B";
                }
            }
        }
        return hall;
    }
    public static boolean checkSeat(int[] prevSeats, int[] curSeats) {
        if (prevSeats[0] == curSeats[0] && prevSeats[1] == curSeats[1] && curSeats[0] != 0 && curSeats[1] != 0) {
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            return true;
        } else {
            return false;
        }
    }
    public static int[] setPlace(int rows, int seats) {
        String rowMsg = "Enter a row number:";
        String seatMsg = "Enter a seat number in that row:";
        int[] rowAndSeat = checkInput(rows, seats, rowMsg, seatMsg);
        return rowAndSeat;
    }
    public static int getPrice(int rows, int seats, int[] rowAndSeat) {
        int price = 10;
        if (rows * seats > 60 && rowAndSeat[0] > rows / 2) {
            price = 8;
        }
        return price;
    }
    public static int buyTicket(String[][] hall, int rows, int seats, int quantity, int[] rowAndSeat) {
        int price = getPrice(rows, seats, rowAndSeat);

        System.out.println("Ticket price: $" + price);
        System.out.println();
        return price;

    }
    public static int showStatistics(int rows, int seats, int price, int currentIncome, int quantity) {
        int income = getIncome(rows, seats);

        double percentage = getPercentage(rows, seats, quantity);
        System.out.printf("Number of purchased tickets: %d", quantity);
        System.out.println();
        System.out.printf("Percentage: %.2f%%", percentage);
        System.out.println();
        System.out.printf("Current income: $%d", currentIncome);
        System.out.println();
        System.out.printf("Total income: $%d", income);
        System.out.println();
        return currentIncome;
    }
    public static int getIncome(int rows, int seats) {
        int income = 0;
        if (rows * seats > 60) {
            income = (rows / 2 * seats * 10) + (rows - rows / 2) * seats * 8;
        } else {
            income = rows * seats * 10;
        }
        return income;
    }
    public static int getCurrentIncome(int currentIncome, int price) {
        currentIncome += price;
        return currentIncome;
    }
    public static double getPercentage(int rows, int seats, int quantity) {
        double percentage = 0;
        double sets = rows * seats;
        percentage = (double)quantity * 100 / sets;
        return percentage;
    }
}
