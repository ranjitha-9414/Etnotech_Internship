import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of taxis: ");
        int n = scanner.nextInt();

        
        TaxiManager service = new TaxiManager(n);

        while (true) {

            System.out.println("\n1. Book Taxi");
            System.out.println("2. View Bookings");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:

                        try {

                            System.out.print("Enter Pickup Point: ");
                            char from = scanner.next().charAt(0);

                            System.out.print("Enter Drop Point: ");
                            char to = scanner.next().charAt(0);

                            service.bookTaxi(from, to);

                        }
                        catch(TaxiNotAvailableException e){
                            System.out.println(e.getMessage());
                        }

                        break;

                case 2:
                    service.viewBookings();
                    break;

                case 3:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
