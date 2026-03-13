package Student_Marks_Analyzer;
import java.util.*;
import java.io.IOException;
import java.nio.file.*;
public class Marks_Analyzer {
    public static void main(String[] args) {
        Path file=Paths.get("Student_Marks_Analyzer/marks.txt");
        try {
            int total=Files.lines(file)
                .mapToInt(line -> Integer.parseInt(line))
                .sum();
                System.out.println("Total Marks: " + total);
                
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            double avg=Files.lines(file)
            .mapToInt(line -> Integer.parseInt(line))
            .average()
            .orElse(0);
            System.out.println("Average Marks: " + avg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            int max=Files.lines(file)
            .mapToInt(line -> Integer.parseInt(line))
            .max()
            .orElse(0);
            System.out.println("Maximum Marks: " + max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
