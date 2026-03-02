import java.sql.*;

public class RestaurantDAO {

    public void addRestaurant(String name, String phone, String location) {

        String sql = "INSERT INTO Restaurant (rest_name, rest_phone_number, Location) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, location);

            ps.executeUpdate();
            System.out.println("Restaurant Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}