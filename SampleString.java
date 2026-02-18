public class SampleString {
    public static void main(String[] args) {
        String name="ranjitha";
        String fullname=name+" C";
        System.out.println(fullname);
        System.out.println("string length:"+name.length());
        System.out.println("character at 7: "+name.charAt(7));
        System.out.println("Name in uppercase:"+name.toUpperCase());
        System.out.println("Name in lowercase:"+name.toLowerCase());
        System.out.println("Is it equals to virat: "+name.equals("virat"));
        System.out.println("ranj is present in string: "+name.contains("ranj"));
        System.out.println("character from 0 to 4 in name: "+name.substring(0,4));
    }
}
