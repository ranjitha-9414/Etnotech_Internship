//Check if given four points form a square 
//Input: p1 = { 20, 10 }, p2 = { 10, 20 }, p3 = { 20, 20 }, p4 = { 10, 10 }
//Output: Yes
import java.util.Arrays;

public class Square {

    // Method to calculate squared distance between two points
    static int distSq(int[] p1, int[] p2) {
        return (p1[0] - p2[0]) * (p1[0] - p2[0]) +
               (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }

    // Method to check if points form a square
    static boolean isSquare(int[] p1, int[] p2, int[] p3, int[] p4) {

        int[] d = new int[6];

        d[0] = distSq(p1, p2);
        d[1] = distSq(p1, p3);
        d[2] = distSq(p1, p4);
        d[3] = distSq(p2, p3);
        d[4] = distSq(p2, p4);
        d[5] = distSq(p3, p4);

        Arrays.sort(d);

        return d[0] > 0 &&
               d[0] == d[1] &&
               d[1] == d[2] &&
               d[2] == d[3] &&
               d[4] == d[5];
    }

    public static void main(String[] args) {

        int[] p1 = {20, 10};
        int[] p2 = {10, 20};
        int[] p3 = {20, 20};
        int[] p4 = {10, 10};

        if (isSquare(p1, p2, p3, p4))
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}
