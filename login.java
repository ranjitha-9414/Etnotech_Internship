//write a program to check login validation username=admin and password=1234
import java.util.Scanner;
 class login {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String username="";
        int password=0;
        for(int i=0;i<3 && !username.equals("admin") || password !=1234;i++){
            try{
                System.out.println("Enter the username:");
                username=sc.nextLine();
                System.out.println("enter the password:");
                password=sc.nextInt();
                sc.nextLine();
                if(username.equals("admin") && password ==1234){
                        System.out.println("Login successful");
                        return;
                    }else{
                        System.out.println("Login failed");
                        
                            System.out.println("Attempts left: " + (2 - i));
                       
                    }
                    
                }catch(Exception e){
                    System.out.println("Invalid input. Please enter the correct username and password.");
                    sc.nextLine(); 
                    System.out.println("Attempts left: " + (2 - i));
                }

            }
            System.out.println("Too many failed attempts. Access Denied ");
            
            sc.close();
    }
}
