public class student {
    String name;
    int RollNo;
    String course;
    student(String name,int RollNo,String course){
        this.name=name;
        this.RollNo=RollNo;
        this.course=course;
    }
    public static void main(String[] args) {
        student s1=new student("Ranjitha",123,"CSE");
        student s2=new student("Virat",18,"ECE");
        System.out.println("Name:"+s1.name+" RollNo:"+s1.RollNo+" Course:"+ s1.course);
    }
}
