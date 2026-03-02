import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AdminDAO adminDAO = new AdminDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        FoodDAO foodDAO = new FoodDAO();
        OrderDAO orderDAO = new OrderDAO();

        while (true) {

            System.out.println("\n===== WELCOME =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Username: ");
                    String user = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    if (adminDAO.login(user, pass)) {
                        adminMenu(sc, restaurantDAO, foodDAO, orderDAO, customerDAO);
                    } else {
                        System.out.println("Invalid Admin Credentials!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Phone Number: ");
                    String phone = sc.nextLine();

                    int custId = customerDAO.loginCustomer(phone);

                    if (custId != -1) {
                        customerMenu(sc, custId, foodDAO, orderDAO);
                    } else {
                        System.out.println("Customer not found!");
                    }
                    break;

                case 3:
                    System.exit(0);
            }
        }
    }

    // 🔹 Admin Menu
    public static void adminMenu(Scanner sc,
                                 RestaurantDAO restaurantDAO,
                                 FoodDAO foodDAO,
                                 OrderDAO orderDAO,
                                 CustomerDAO customerDAO) {

        while (true) {

            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Add Restaurant");
            System.out.println("2. Add Food");
            System.out.println("3. View Orders");
            System.out.println("4. Update Order Status");
            System.out.println("5. Delete Order");
            System.out.println("6. Back");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    System.out.print("Restaurant Name: ");
                    String rname = sc.nextLine();
                    System.out.print("Phone: ");
                    String rphone = sc.nextLine();
                    System.out.print("Location: ");
                    String rloc = sc.nextLine();
                    restaurantDAO.addRestaurant(rname, rphone, rloc);
                    break;

                case 2:
                    System.out.print("Food Name: ");
                    String fname = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    foodDAO.showAllRestaurants();
                    System.out.print("Restaurant ID: ");
                    int restId = sc.nextInt();
                    foodDAO.addFood(fname, price, restId);
                    break;

                case 3:
                    orderDAO.viewAllOrders();
                    break;

                case 4:
                    System.out.print("Order ID: ");
                    int oid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Status: ");
                    String status = sc.nextLine();
                    orderDAO.updateOrderStatus(oid, status);
                    break;

                case 5:
                    System.out.print("Order ID: ");
                    int del = sc.nextInt();
                    orderDAO.deleteOrder(del);
                    break;

                case 6:
                    return;
            }
        }
    }

    // 🔹 Customer Menu
    public static void customerMenu(Scanner sc,
                                    int custId,
                                    FoodDAO foodDAO,
                                    OrderDAO orderDAO) {

        while (true) {

            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. View Restaurants & Food");
            System.out.println("2. Place Order");
            System.out.println("3. Back");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    foodDAO.showFoodByRestaurant();
                    break;

                case 2:

                    System.out.print("Delivery Address: ");
                    String address = sc.nextLine();

                    System.out.print("Payment Method: ");
                    String payment = sc.nextLine();

                    foodDAO.showFoodByRestaurant();

                    System.out.print("Number of Items: ");
                    int n = sc.nextInt();

                    int[][] items = new int[n][2];

                    for (int i = 0; i < n; i++) {
                        System.out.print("Food ID: ");
                        items[i][0] = sc.nextInt();
                        System.out.print("Quantity: ");
                        items[i][1] = sc.nextInt();
                    }

                    orderDAO.placeOrder(custId, address, payment, items);
                    break;

                case 3:
                    return;
            }
        }
    }
}