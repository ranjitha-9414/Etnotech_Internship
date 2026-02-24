

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertStudent {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "root");

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            String query = "INSERT INTO student(name, age, course) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);

            ps.executeUpdate();

            System.out.println(" Student Stored Successfully!");

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}