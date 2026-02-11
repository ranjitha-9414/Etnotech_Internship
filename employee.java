public class employee extends Encapsulation {
    
    employee(double salary){
        super(salary);
    }
    public static void main(String[] args) {
        employee e1=new employee(50000);//Inheretence
       System.out.println("Initial Salary: " + e1.getsalary());

        e1.setsalary(60000);

        System.out.println("Updated Salary: " + e1.getsalary());
    }
    }
    

