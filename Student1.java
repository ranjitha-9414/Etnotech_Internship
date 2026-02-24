import java.util.Scanner;
public class Student1 {
        public static void main(String[] args) {
        
            Scanner sc=new Scanner(System.in);
            String[] students=new String[5];
            int[] marks=new int[5];
            int i,total_marks=0;
            for( i=0;i<students.length;i++){
                System.out.println("Enter the student name "+(i+1)+":");
                students[i]=sc.nextLine();
                System.out.println("Marks:");
                marks[i]=sc.nextInt();
                sc.nextLine();
                 total_marks +=marks[i];
            }

            System.out.println(total_marks);
            for( i=0;i<students.length;i++){
                if(marks[i]<=100 &&marks[i]>=90){
                    System.out.println(students[i]+" Grade A+");
                }else if(marks[i]<90 && marks[i]>=80){
                        System.out.println(students[i]+" Grade A");
                }else if(marks[i]<80 && marks[i]>=70){
                        System.out.println(students[i]+" Grade B");
                }else if(marks[i]<70 && marks[i]>=60){
                        System.out.println(students[i]+" Grade C");
                }else if(marks[i]<60 ){
                        System.out.println(students[i]+" Failed");
                }else{
                        System.out.println(students[i]+" Invalid marks");
                }
            }
        }
}
       
