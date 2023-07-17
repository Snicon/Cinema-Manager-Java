package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    private static int ticketsBought = 0;
    private static int profit = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        String[][] cinemaSeats = generateCinemaModel(rows, seatsPerRow);

        System.out.println();
        runProgram(rows, seatsPerRow, scanner, cinemaSeats);
    }

    static void runProgram(int rows, int seatsPerRow, Scanner scanner, String[][] cinemaSeats) {
        System.out.println();
        System.out.println();
        printOptions();

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                printCinema(cinemaSeats, seatsPerRow);
                runProgram(rows, seatsPerRow, scanner, cinemaSeats);
            case 2:
                buyTicket(scanner, cinemaSeats, seatsPerRow, rows);
                runProgram(rows, seatsPerRow, scanner, cinemaSeats);
            case 3:
                printStatistics(seatsPerRow * rows, rows, seatsPerRow);
                runProgram(rows, seatsPerRow, scanner, cinemaSeats);
            case 0:
                return;
        }
    }

    static void buyTicket(Scanner scanner, String[][] cinemaSeats, int seatsPerRow, int rows) {
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();

        System.out.println("Enter a seat number in that row:");
        int seatNumber = scanner.nextInt();

        if(row > rows || seatNumber > seatsPerRow || row < 1 || seatNumber < 1) {
            System.out.println("Wrong input!");
            buyTicket(scanner, cinemaSeats, seatsPerRow, rows);
        } else {
            bookSeat(scanner, cinemaSeats, row, seatNumber, seatsPerRow, rows);

            System.out.println();
        }
    }

    static void printOptions() {
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """);
    }

    static void printStatistics(int maxSeatAmount, int rows, int seatsPerRow) {
            System.out.println("Number of purchased tickets: " + ticketsBought);
            float percentage = ((float) ticketsBought / maxSeatAmount * 100);
            System.out.println("Percentage: " + String.format("%.2f%%", percentage));
            System.out.println("Current income: $" + profit);
            System.out.println("Total income: " + calculateIncome(rows, seatsPerRow));
    }

    static String[][] generateCinemaModel(int rows, int seatsPerRow) {

        String[][] cinemaSeats = new String[rows][seatsPerRow];

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < seatsPerRow; j++) {
                cinemaSeats[i][j] = "S";
            }
        }

        return cinemaSeats;
    }

    static void printCinema(String[][] cinemaSeats, int seatsPerRow) {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.print(" " + i);
        }

        for(int i = 0; i < cinemaSeats.length; i++) {
            System.out.print("\n" + (i + 1));
            for (String row: cinemaSeats[i]
            ) {
                System.out.print(" " + row);
            }
        }
    }

    static void bookSeat(Scanner scanner, String[][] cinemaSeats, int row, int seatNumber, int seatsPerRow, int rows) {
        if(!Objects.equals(cinemaSeats[row - 1][seatNumber - 1], "B")) {
            cinemaSeats[row-1][seatNumber-1] = "B";
            ticketsBought++;
            int ticketPrice = calculateTicketPrice(row, seatsPerRow * rows > 60, rows);
            profit += ticketPrice;

            System.out.println();
            System.out.println("Ticket price: $" + ticketPrice);
        } else {
            System.out.println("That ticket has already been purchased!");
            buyTicket(scanner, cinemaSeats, seatsPerRow, rows);
        }
    }

    static int calculateTicketPrice(int row, boolean over60, int rows) {
        if(!over60) {
            return 10;
        } else {
            int frontRows = (int) (rows / 2);

            if(row <= frontRows) {
                return 10;
            } else {
                return 8;
            }
        }
    }

    static String calculateIncome(int rows, int seatsPerRow) {
        if(rows * seatsPerRow <= 60) {
            return "$" + rows * seatsPerRow * 10;
        } else {
            int frontRows = (int) (rows / 2);
            int backRows = rows - frontRows;

            return "$" + ((frontRows * seatsPerRow * 10) + (backRows * seatsPerRow * 8));
        }
    }
}
