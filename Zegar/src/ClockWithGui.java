import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    ClockWithGui() {
        new ClockThread().start();
    }


    LocalTime time = LocalTime.now();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);

        FontMetrics fm = g.getFontMetrics();


        for (int i = 1; i < 13; i++) {
            AffineTransform at = new AffineTransform();
            at.rotate(2 * Math.PI / 12 * i);
            Point2D src = new Point2D.Float(0, -120);
            Point2D trg = new Point2D.Float();
            at.transform(src, trg);
            g2d.drawString(Integer.toString(i), (int) trg.getX(), (int) trg.getY());

        }

        for (int i = 1; i < 61; i++) {
            AffineTransform li = g2d.getTransform();
            g2d.rotate(i % 60 * 2 * Math.PI / 60);
            g2d.drawLine(0, -120, 0, -110);
            g2d.setTransform(li);
        }

        AffineTransform saveAT = g2d.getTransform();
        g2d.rotate(time.getHour() % 60 * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -50);
        g2d.setTransform(saveAT);

        AffineTransform saveAT1 = g2d.getTransform();
        g2d.rotate(time.getMinute() % 60 * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -100);
        g2d.setTransform(saveAT1);

        AffineTransform saveAT2 = g2d.getTransform();
        g2d.rotate(time.getSecond() % 60 * 2 * Math.PI / 60);
        g2d.drawLine(0, 0, 0, -120);
        g2d.setTransform(saveAT2);


    }

    class ClockThread extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n", time.getHour(), time.getMinute(), time.getSecond());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }
}