import java.util.Scanner;

public class SimpleIO {

    public static void main(String[] args) {
        // write your code here
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        int i = scan.nextInt();
        System.out.printf("Wczytano %s , %d",s,i);
    }
}