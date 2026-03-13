package File_reader;
import java.util.*;
import java.io.IOException;
import java.nio.file.*;
public class filereader {
    public static void main(String[] args) {
       Path file= Paths.get("File_reader/user.txt");
       try {
        Files.lines(file)
            .forEach(System.out::println);
    } catch (IOException e) {
        
        e.printStackTrace();
    }
   


    }
}
