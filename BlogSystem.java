import java.io.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class User implements Serializable {
    String username;
    String passwordHash;
    String role; 
}

class Blog implements Serializable {
    private static int counter = 1;

    int id;
    String title;
    String content;
    String author;
    String category;
    String date;
    int likes = 0;
    Set<String> likedUsers = new HashSet<>();
    List<String> comments = new ArrayList<>();

    Blog(String title, String content, String author, String category) {
        this.id = counter++;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "\nID: " + id +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nDate: " + date +
                "\nLikes: " + likes +
                "\nContent: " + content +
                "\nComments: " + comments +
                "\n----------------------------";
    }
}

public class BlogSystem {

    static Scanner sc = new Scanner(System.in);
    static List<Blog> blogs = new ArrayList<>();
    static Map<String, User> users = new HashMap<>();

    static final String BLOG_FILE = "blogs.dat";
    static final String USER_FILE = "users.dat";

    public static void main(String[] args) {

        loadUsers();
        loadBlogs();

       
        if (!users.containsKey("admin")) {
            User admin = new User();
            admin.username = "admin";
            admin.passwordHash = hashPassword("admin123");
            admin.role = "ADMIN";
            users.put("admin", admin);
            saveUsers();
        }

        while (true) {
            System.out.println("\n===== BLOG SYSTEM LOGIN =====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    saveBlogs();
                    saveUsers();
                    System.exit(0);
            }
        }
    }

    

    static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void register() {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        if (users.containsKey(username)) {
            System.out.println(" Username already exists");
            return;
        }

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = new User();
        user.username = username;
        user.passwordHash = hashPassword(password);
        user.role = "USER";

        users.put(username, user);
        saveUsers();
        System.out.println(" Registered Successfully");
    }

    static void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = users.get(username);

        if (user != null && user.passwordHash.equals(hashPassword(password))) {
            if (user.role.equals("ADMIN"))
                adminMenu(username);
            else
                userMenu(username);
        } else {
            System.out.println(" Invalid Credentials");
            
        }
    }

    

    static void adminMenu(String username) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Blog");
            System.out.println("2. Edit Blog");
            System.out.println("3. Delete Blog");
            System.out.println("4. View All Blogs");
            System.out.println("5. Trending Blogs");
            System.out.println("6. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addBlog(username); break;
                case 2: editBlog(); break;
                case 3: deleteBlog(); break;
                case 4: viewAllBlogs(); break;
                case 5: showTrending(); break;
                case 6: return;
            }
        }
    }

    

    static void userMenu(String username) {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. View Blogs");
            System.out.println("2. Like Blog");
            System.out.println("3. Add Comment");
            System.out.println("4. Search by Category");
            System.out.println("5. Trending Blogs");
            System.out.println("6. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: viewAllBlogs(); break;
                case 2: likeBlog(username); break;
                case 3: addComment(username); break;
                case 4: searchCategory(); break;
                case 5: showTrending(); break;
                case 6: return;
            }
        }
    }

    
    static void addBlog(String author) {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Content: ");
        String content = sc.nextLine();
        System.out.print("Category: ");
        String category = sc.nextLine();

        blogs.add(new Blog(title, content, author, category));
        saveBlogs();
        System.out.println("Blog Added");
    }

    static void editBlog() {
        System.out.print("Enter Blog ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Blog b : blogs) {
            if (b.id == id) {
                System.out.print("New Title: ");
                b.title = sc.nextLine();
                System.out.print("New Content: ");
                b.content = sc.nextLine();
                System.out.print("New Category: ");
                b.category = sc.nextLine();
                saveBlogs();
                System.out.println(" Blog Updated");
                return;
            }
        }
        System.out.println(" Blog Not Found");
    }

    static void deleteBlog() {
        System.out.print("Enter Blog ID: ");
        int id = sc.nextInt();
        blogs.removeIf(b -> b.id == id);
        saveBlogs();
        System.out.println(" Deleted if existed");
    }

    static void viewAllBlogs() {
        if (blogs.isEmpty()) {
            System.out.println("No blogs available.");
            return;
        }

        int pageSize = 3;
        int totalPages = (int) Math.ceil((double) blogs.size() / pageSize);
        int currentPage = 1;

        while (true) {
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, blogs.size());

            System.out.println("\n--- Page " + currentPage + "/" + totalPages + " ---");

            for (int i = start; i < end; i++)
                System.out.println(blogs.get(i));

            System.out.println("n-next p-previous e-exit");
            String input = sc.nextLine();

            if (input.equals("n") && currentPage < totalPages) currentPage++;
            else if (input.equals("p") && currentPage > 1) currentPage--;
            else if (input.equals("e")) break;
        }
    }

    static void likeBlog(String username) {
        System.out.print("Enter Blog ID: ");
        int id = sc.nextInt();

        for (Blog b : blogs) {
            if (b.id == id) {
                if (b.likedUsers.contains(username)) {
                    System.out.println(" Already Liked");
                    return;
                }
                b.likes++;
                b.likedUsers.add(username);
                saveBlogs();
                System.out.println(" Liked");
                return;
            }
        }
        System.out.println(" Blog Not Found");
    }

    static void addComment(String username) {
        System.out.print("Enter Blog ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Blog b : blogs) {
            if (b.id == id) {
                System.out.print("Enter Comment: ");
                String comment = sc.nextLine();
                b.comments.add(username + ": " + comment);
                saveBlogs();
                System.out.println(" Comment Added");
                return;
            }
        }
        System.out.println(" Blog Not Found");
    }

    static void searchCategory() {
        System.out.print("Enter Category: ");
        String cat = sc.nextLine();

        blogs.stream()
                .filter(b -> b.category.equalsIgnoreCase(cat))
                .forEach(System.out::println);
    }

    static void showTrending() {
        blogs.stream()
                .sorted((b1, b2) -> b2.likes - b1.likes)
                .limit(5)
                .forEach(System.out::println);
    }

    
    static void saveBlogs() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(BLOG_FILE))) {
            oos.writeObject(blogs);
        } catch (Exception ignored) {}
    }

    static void loadBlogs() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(BLOG_FILE))) {
            blogs = (List<Blog>) ois.readObject();
        } catch (Exception ignored) {}
    }

    static void saveUsers() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (Exception ignored) {}
    }

    static void loadUsers() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception ignored) {}
    }
}