import java.sql.*;

public class FoodDAO {

    // 🔹 Add Food
    public void addFood(String foodName, double price, int restId) {

        String sql = "INSERT INTO Food_Item (food_name, food_price, rest_id) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, foodName);
            ps.setDouble(2, price);
            ps.setInt(3, restId);

            ps.executeUpdate();
            System.out.println("Food Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Show All Restaurants
    public void showAllRestaurants() {

        String sql = "SELECT * FROM Restaurant";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- RESTAURANTS -----");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("rest_id") + " | " +
                        rs.getString("rest_name") + " | " +
                        rs.getString("Location"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Show All Food (with Restaurant Name)
    public void showAllFood() {

        String sql = "SELECT f.food_id, f.food_name, f.food_price, r.rest_name " +
                     "FROM Food_Item f " +
                     "JOIN Restaurant r ON f.rest_id = r.rest_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- AVAILABLE FOOD -----");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("food_id") + " | " +
                        rs.getString("food_name") + " | Rs " +
                        rs.getDouble("food_price") + " | " +
                        rs.getString("rest_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Show Restaurant-Wise Food (Grouped View)
    public void showFoodByRestaurant() {

        String sql = "SELECT r.rest_name, f.food_id, f.food_name, f.food_price " +
                     "FROM Restaurant r " +
                     "JOIN Food_Item f ON r.rest_id = f.rest_id " +
                     "ORDER BY r.rest_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            String currentRestaurant = "";

            while (rs.next()) {

                String restName = rs.getString("rest_name");

                if (!restName.equals(currentRestaurant)) {
                    System.out.println("\nRestaurant: " + restName);
                    currentRestaurant = restName;
                }

                System.out.println("   " +
                        rs.getInt("food_id") + " | " +
                        rs.getString("food_name") + " | Rs " +
                        rs.getDouble("food_price"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Show Food By Specific Restaurant ID (Filter)
    public void showFoodByRestaurantId(int restId) {

        String sql = "SELECT f.food_id, f.food_name, f.food_price, r.rest_name " +
                     "FROM Food_Item f " +
                     "JOIN Restaurant r ON f.rest_id = r.rest_id " +
                     "WHERE f.rest_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, restId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Food for Selected Restaurant ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("food_id") + " | " +
                        rs.getString("food_name") + " | Rs " +
                        rs.getDouble("food_price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}