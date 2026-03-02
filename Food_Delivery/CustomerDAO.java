import java.sql.*;

public class CustomerDAO {
    public int loginCustomer(String phone) {

    String sql = "SELECT cust_id FROM Customer WHERE phone_number = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, phone);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("cust_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}

    public void addCustomer(String name, String phone, String location) {

        String sql = "INSERT INTO Customer (cust_name, phone_number, location) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, location);

            ps.executeUpdate();
            System.out.println("Customer Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCustomer(int custId) {

        String sql = "SELECT * FROM Customer WHERE cust_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, custId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("cust_id"));
                System.out.println("Name: " + rs.getString("cust_name"));
                System.out.println("Phone: " + rs.getString("phone_number"));
                System.out.println("Location: " + rs.getString("location"));
            } else {
                System.out.println(" Customer not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}