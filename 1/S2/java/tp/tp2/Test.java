public class Test{
    public static void main(String[] args){
        var s1 = "toto";
        var s2 = s1;
        var s3 = new String(s1);
    
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
    }
}