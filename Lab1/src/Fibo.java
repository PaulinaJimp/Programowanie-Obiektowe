import java.util.Scanner;

public class Fibo {

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        System.out.printf("Wczytano %d \n",n);

        if(n > 1 && n < 45){

            int [] tab = new int[n];
            for(int i =0; i < n; i++){
                if(i == 1 || i == 0)
                    tab[i] = 1;
                else{
                    tab[i] = tab[i-1]+tab[i-2];
                }
                System.out.println(tab[i]);
            }
        }
    }
}