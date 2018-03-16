import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.UnaryOperator;

import static java.lang.StrictMath.sin;

public class Watek {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);
    static Semaphore semaphore = new Semaphore(0);

    static void initArray(int size) {
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * size / (i + 1);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        initArray(100000000);
        System.out.print(array[1]+"\n");
        parallelWatek(500000, t -> sin(t));
        System.out.print(array[1]);
    }

    static class Watekcalc extends Thread {
        private final int start;
        private final int end;
        private UnaryOperator<Double> operator;


        Watekcalc(int start, int end, UnaryOperator<Double> operator) {
            this.start = start;
            this.end = end;
            this.operator = operator;
        }

        public void run() {
            for (int i = start; i < end; i++) {
                try {
                    results.put(operator.apply(array[i]));
                    array[i]=results.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf(Locale.US, "%d-%d poszło\n", start, end);
            semaphore.release();
        }
    }

    static void parallelWatek(int cnt, UnaryOperator<Double> op) throws InterruptedException {
        double t1 = System.nanoTime() / 1e6;
        int len = array.length;
        int ile = len / cnt;
        for (int i = 0; i < cnt; i++) {
            new Watekcalc(i * ile, (i + 1) * ile, op).start();
        }


        semaphore.acquire(cnt);

        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "size = %d cnt=%d   t2-t1=%f \n",
                array.length,
                cnt,
                t2 - t1);
    }
}