//Encapsulation:Making the variable private and it can access using methods(getter and setter)or 
//Binding data with methods so that we can protect the data
 class student{
    private String name;//Instance variables
    private int rollno;
    private String Branch="CSE";
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public int getrollno(){
        return rollno;
    }

    public void setRollno(int a){
        rollno=a;
    }

    public String getBranch(){
        return Branch;
    }
    
}
public class Encapsulation1 {
    public static void main(String[] args) {
        student s1=new student();
        s1.setName("Ranjitha");
        s1.setRollno(123);
        System.out.println("Name = "+ s1.getName() + "RollNo"+ s1.getrollno() + "Branch="+s1.getBranch());
    }
}
