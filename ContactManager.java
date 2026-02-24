import java.io.*;
import java.util.*;

class Contact implements Comparable<Contact> {

    String name;
    String phone;

    Contact(String name, String phone) {
        this.name = name.toLowerCase();
        this.phone = phone;
    }

    
    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.name);
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        return this.phone.equals(other.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}

public class ContactManager {

    static final String FILE_NAME = "contacts.txt";

    public static void main(String[] args) {

        TreeSet<Contact> contacts = new TreeSet<>();
        Scanner sc = new Scanner(System.in);

        loadFromFile(contacts);

        while (true) {

            System.out.println("\n1. Insert Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Display Contacts (Sorted)");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine().toLowerCase();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    Contact newContact = new Contact(name, phone);

                    
                    boolean phoneExists = contacts.stream()
                            .anyMatch(c -> c.phone.equals(phone));

                    if (phoneExists) {
                        System.out.println("Phone number already exists!");
                        break;
                    }

                    contacts.add(newContact);
                    saveToFile(contacts);
                    System.out.println(" Contact Saved");
                    break;

                case 2:
                    System.out.print("Enter Name to delete: ");
                    String delName = sc.nextLine().toLowerCase();

                    Contact toRemove = contacts.stream()
                            .filter(c -> c.name.equals(delName))
                            .findFirst()
                            .orElse(null);

                    if (toRemove != null) {
                        contacts.remove(toRemove);
                        saveToFile(contacts);
                        System.out.println(" Contact Deleted");
                    } else {
                        System.out.println(" Contact Not Found");
                    }
                    break;

                case 3:
                    System.out.print("Enter Name to search: ");
                    String searchName = sc.nextLine().toLowerCase();

                    Contact found = contacts.stream()
                            .filter(c -> c.name.equals(searchName))
                            .findFirst()
                            .orElse(null);

                    if (found != null)
                        System.out.println("Phone: " + found.phone);
                    else
                        System.out.println(" Contact Not Found");
                    break;

                case 4:
                    if (contacts.isEmpty()) {
                        System.out.println("Contact list is empty.");
                    } else {
                        System.out.println("\n--- Sorted Contact List ---");
                        for (Contact c : contacts) {
                            System.out.println("Name: " + c.name + " | Phone: " + c.phone);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    
    static void saveToFile(Set<Contact> contacts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                bw.write(c.name + "," + c.phone);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e);
        }
    }

    
    static void loadFromFile(Set<Contact> contacts) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.add(new Contact(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("No existing contacts found. Starting fresh.");
        }
    }
}