//write a program to check login validation username=admin and password=1234
import java.util.Scanner;
 class login {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the username:");
        String username=sc.nextLine();
        System.out.println("enter the password:");
        int password=sc.nextInt();
        
            
        
            if(username.equals("admin") && password ==1234){
                System.out.println("Login successful");
            }else{
                System.out.println("Login failed");
            }
            
        
        sc.close();

    }
}
