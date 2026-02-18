public class SampleArray {
    public static void main(String[] args) {
        
    
        //Array Declaration
        int[] arr;
        //Memory Allocationor declare with size
        arr=new int[4];
        //intializing array
        arr[0]=5;
        arr[1]=8;
        arr[2]=9;
        arr[3]=7;
        //both memory and declaration
        int[] a=new int[6];
        //declartion + intialization
        String[] B={"Ranjitha","virat","siraj"};
        for(int i=0;i<B.length;i++)
            System.out.println(B[i]);
        //Acessing array elemnt using loop
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
        //2 D array
        int[][] matrix=new int[2][2];//int[][] matrix={{2,8},{9,10}};
        matrix[0][0]=2;
        matrix[0][1]=8;
        matrix[1][0]=9;
        matrix[1][1]=10;
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                    System.out.println(matrix[i][j]);
            }
        }
    }
}
