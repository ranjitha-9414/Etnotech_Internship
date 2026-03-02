import java.sql.*;

public class OrderDAO {

    // 🔹 Automatically get one delivery person
    public int getAvailableDeliveryPerson(Connection con) throws Exception {

        String sql = "SELECT dp_id FROM Delivery_person LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("dp_id");
            }
        }

        throw new Exception("No delivery person available!");
    }

    // 🔹 Place Order (Auto Assign Delivery Person)
    public void placeOrder(int custId, String address,
                           String paymentMethod, int[][] items) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Get delivery person automatically
            int dpId = getAvailableDeliveryPerson(con);

            double totalAmount = 0;

            // 🔹 Calculate total
            for (int[] item : items) {

                int foodId = item[0];
                int quantity = item[1];

                String priceQuery = "SELECT food_price FROM Food_Item WHERE food_id = ?";
                PreparedStatement priceStmt = con.prepareStatement(priceQuery);
                priceStmt.setInt(1, foodId);

                ResultSet rs = priceStmt.executeQuery();

                if (rs.next()) {
                    double price = rs.getDouble("food_price");
                    totalAmount += price * quantity;
                }
            }

            // 🔹 Insert into Ordered table
            String orderSQL = "INSERT INTO Ordered (order_date, cust_id, dp_id, status, delivery_address, total_amount, payment_method) VALUES (NOW(), ?, ?, 'Pending', ?, ?, ?)";

            PreparedStatement orderStmt =
                    con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);

            orderStmt.setInt(1, custId);
            orderStmt.setInt(2, dpId);
            orderStmt.setString(3, address);
            orderStmt.setDouble(4, totalAmount);
            orderStmt.setString(5, paymentMethod);

            orderStmt.executeUpdate();

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;

            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // 🔹 Insert into Order_Item
            String itemSQL = "INSERT INTO Order_Item (order_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";

            for (int[] item : items) {

                int foodId = item[0];
                int quantity = item[1];

                String priceQuery = "SELECT food_price FROM Food_Item WHERE food_id = ?";
                PreparedStatement priceStmt = con.prepareStatement(priceQuery);
                priceStmt.setInt(1, foodId);

                ResultSet rs = priceStmt.executeQuery();
                rs.next();
                double price = rs.getDouble("food_price");

                PreparedStatement itemStmt = con.prepareStatement(itemSQL);

                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, foodId);
                itemStmt.setInt(3, quantity);
                itemStmt.setDouble(4, price);

                itemStmt.executeUpdate();
            }

            con.commit();
            System.out.println(" Order Placed Successfully!");
            System.out.println("Assigned Delivery Person ID: " + dpId);
            System.out.println("Total Amount: ₹" + totalAmount);

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    // 🔹 View Orders
    public void viewAllOrders() {

        String sql = "SELECT o.order_id, c.cust_name, f.food_name, " +
                     "oi.quantity, o.total_amount, o.status " +
                     "FROM Ordered o " +
                     "JOIN Customer c ON o.cust_id = c.cust_id " +
                     "JOIN Order_Item oi ON o.order_id = oi.order_id " +
                     "JOIN Food_Item f ON oi.food_id = f.food_id " +
                     "ORDER BY o.order_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== ORDER DETAILS =====");

            while (rs.next()) {
                System.out.println(
                        "Order ID: " + rs.getInt("order_id") +
                        " | Customer: " + rs.getString("cust_name") +
                        " | Food: " + rs.getString("food_name") +
                        " | Qty: " + rs.getInt("quantity") +
                        " | Total: " + rs.getDouble("total_amount") +
                        " | Status: " + rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Update Status
    public void updateOrderStatus(int orderId, String newStatus) {

        String sql = "UPDATE Ordered SET status = ? WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            ps.executeUpdate();
            System.out.println("Status Updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Delete Order
    public void deleteOrder(int orderId) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps1 =
                    con.prepareStatement("DELETE FROM Order_Item WHERE order_id = ?");
            ps1.setInt(1, orderId);
            ps1.executeUpdate();

            PreparedStatement ps2 =
                    con.prepareStatement("DELETE FROM Ordered WHERE order_id = ?");
            ps2.setInt(1, orderId);
            ps2.executeUpdate();

            con.commit();
            System.out.println("Order Deleted Successfully!");

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}