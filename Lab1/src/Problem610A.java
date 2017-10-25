import java.util.Scanner;

public class Problem610A {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        int l = scan.nextInt();
        System.out.printf("Wczytano %d \n",l);

        int pom = 0;
        if(l % 2 == 0){
            for (int i = 1; i <= l / 4; i++) {
                pom++;
            }
        }
        if(l % 4 == 0)
            pom--;
        System.out.println(pom);
    }
}