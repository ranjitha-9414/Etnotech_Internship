//1st 100units=5 rupees per unit
//2nd 100units=7 ruppesper unit
//above 200units=10 rupees per unit

import java.util.Scanner;

public class Bill{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of units consumed: ");
        int units = sc.nextInt();
        double billAmount = calculateBill(units);
        System.out.println("Total bill amount: " + billAmount);
        sc.close();
    }
    public static double calculateBill(int units){
        double billAmount=0;
        //  if(units<=100){
        //     billAmount=units*5;
        // }else if(units<=200){
        //     billAmount=100*5+(units-100)*7;
        // }else{
        //     billAmount=100*5+100*7+(units-200)*10;
        // }
        billAmount=(units<=100)?units*5:(units<=200)?100*5+(units-100)*7:100*5+100*7+(units-200)*10;
        return billAmount;

    }
    
}
